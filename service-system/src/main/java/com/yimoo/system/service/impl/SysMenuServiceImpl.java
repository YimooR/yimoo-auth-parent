package com.yimoo.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yimoo.model.system.SysMenu;
import com.yimoo.system.exception.YimooException;
import com.yimoo.system.service.SysMenuService;
import com.yimoo.system.mapper.SysMenuMapper;
import com.yimoo.system.utils.MenuHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author haoyang
* @description 针对表【sys_menu(菜单表)】的数据库操作Service实现
* @createDate 2022-11-14 22:10:22
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService{

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
    public Boolean removeMenuById(String id) {
        //查询当前删除的菜单下是否有子菜单
        //根据id=parentId
        QueryWrapper<SysMenu> sysMenuQueryWrapper=new QueryWrapper<>();
        sysMenuQueryWrapper.eq("parent_id",id);
        Integer count=baseMapper.selectCount(sysMenuQueryWrapper);
        if(count>0){
            throw new YimooException(201,"请先删除子菜单");
        }
        //调用删除
        baseMapper.deleteById(id);
        return false;
    }
}




