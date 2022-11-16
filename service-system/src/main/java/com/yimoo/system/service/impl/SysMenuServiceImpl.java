package com.yimoo.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yimoo.common.utils.RouterHelper;
import com.yimoo.model.system.SysMenu;
import com.yimoo.model.system.SysRoleMenu;
import com.yimoo.model.vo.AssginMenuVo;
import com.yimoo.model.vo.RouterVo;
import com.yimoo.system.exception.YimooException;
import com.yimoo.system.mapper.SysRoleMenuMapper;
import com.yimoo.system.service.SysMenuService;
import com.yimoo.system.mapper.SysMenuMapper;
import com.yimoo.system.utils.MenuHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author haoyang
 * @description 针对表【sys_menu(菜单表)】的数据库操作Service实现
 * @createDate 2022-11-14 22:10:22
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
        implements SysMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    //菜单列表（树形）
    @Override
    public List<SysMenu> findNodes() {
        //全部权限列表
        List<SysMenu> sysMenuList = this.list();
        if (CollectionUtils.isEmpty(sysMenuList)) return null;
        //构建树形数据
        List<SysMenu> result = MenuHelper.buildTree(sysMenuList);
        return result;
    }

    @Override
    public void removeMenuById(String id) {
        //查询当前删除的菜单下是否有子菜单
        //根据id=parentId
        QueryWrapper<SysMenu> sysMenuQueryWrapper = new QueryWrapper<>();
        sysMenuQueryWrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(sysMenuQueryWrapper);
        if (count > 0) { //有子菜单
            throw new YimooException(201, "请先删除子菜单！");
        }
        //调用删除
        baseMapper.deleteById(id);
    }

    //根据角色来分配菜单
    @Override
    public List<SysMenu> findMenuByRoleId(String roleId) {
        //获取所有的菜单，status为1的菜单
        QueryWrapper<SysMenu> sysMenuQueryWrapper = new QueryWrapper<>();
        sysMenuQueryWrapper.eq("status", 1);
        List<SysMenu> menuList = baseMapper.selectList(sysMenuQueryWrapper);
        //根据角色ID查询，获取角色分配过的菜单列表,a
        QueryWrapper<SysRoleMenu> sysRoleMenuQueryWrapper = new QueryWrapper<>();
        sysMenuQueryWrapper.eq("role_id", roleId);
        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(sysRoleMenuQueryWrapper);
        //从上面查询的菜单列表中查询角色分配所有菜单id,b
        List<String> roleMenuIds = new ArrayList<>();
        for (SysRoleMenu sysRoleMenu : roleMenus) {
            roleMenuIds.add(sysRoleMenu.getMenuId());
        }
        //数据处理：isSelect如果菜单被选中为true,否则为false
        //拿着a比较b，如果相同，让isSelect值为true，意思为被选中
        for (SysMenu sysMenu : menuList) {
            if (roleMenuIds.contains(sysMenu.getId())) {
                sysMenu.setSelect(true);
            } else {
                sysMenu.setSelect(false);
            }
        }
        //转换成树形结构，为了最终显示
        List<SysMenu> sysMenuList=MenuHelper.buildTree(menuList);
        return sysMenuList;
    }

    //分配权限
    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        //根据角色ID删除菜单权限
        QueryWrapper<SysRoleMenu> sysRoleMenuQueryWrapper=new QueryWrapper<>();
        sysRoleMenuQueryWrapper.eq("role_id",assginMenuVo.getRoleId());
        sysRoleMenuMapper.delete(sysRoleMenuQueryWrapper);
        //遍历菜单ID列表，一个一个添加
        List<String> menuIdList=assginMenuVo.getMenuIdList();
        for(String menuId:menuIdList){
            SysRoleMenu sysRoleMenu=new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
            sysRoleMenuMapper.insert(sysRoleMenu);
        }
    }

    //根据userId查询菜单权限值
    @Override
    public List<RouterVo> getUserMenuList(String userId) {
        //admin唯一的超级管理员，操作所有内容
        List<SysMenu> sysMenuList=null;
        //判断userId值是1代表超级管理员，查询所有权限数据
        if("1".equals(userId)){
            QueryWrapper<SysMenu> sysMenuQueryWrapper=new QueryWrapper<>();
            sysMenuQueryWrapper.eq("status",1);
            sysMenuQueryWrapper.orderByAsc("sort_value");
            sysMenuList=baseMapper.selectList(sysMenuQueryWrapper);
        }else {
            //如果userid不是1，是其他类型用户，查询这个用户权限
            sysMenuList=baseMapper.findMenuListUserId(userId);
        }
        //构建成一个树形结构
        List<SysMenu> sysMenuTreeList=MenuHelper.buildTree(sysMenuList);
        //转换成前端路由要求的数据格式
        List<RouterVo> routerVoList=RouterHelper.buildRouters(sysMenuTreeList);
        return routerVoList;
    }

    //根据userId查询按钮权限值
    @Override
    public List<String> getUserButtonList(String userId) {
        List<SysMenu> sysMenuList=null;
        //判断是否是管理员
        if("1".equals(userId)){
           sysMenuList=baseMapper.selectList(new QueryWrapper<SysMenu>().eq("status",1));
        }else {
            sysMenuList=baseMapper.findMenuListUserId(userId);
        }
        //sysMenuList遍历
        List<String> permissionList=new ArrayList<>();
        for(SysMenu sysMenu:sysMenuList){
            if(sysMenu.getType()==2) {
                String perms = sysMenu.getPerms();
                permissionList.add(perms);
            }
        }
        return permissionList;
    }
}




