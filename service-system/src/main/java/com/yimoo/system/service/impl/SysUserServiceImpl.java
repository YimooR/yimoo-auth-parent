package com.yimoo.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimoo.model.system.SysUser;
import com.yimoo.model.vo.RouterVo;
import com.yimoo.model.vo.SysUserQueryVo;
import com.yimoo.system.mapper.SysMenuMapper;
import com.yimoo.system.service.SysMenuService;
import com.yimoo.system.service.SysUserService;
import com.yimoo.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author haoyang
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2022-11-10 20:06:37
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

    @Autowired
    SysMenuService sysMenuService;

    @Override
    public IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo sysUserQueryVo) {
        return baseMapper.selectPage(pageParam, sysUserQueryVo);
    }

    @Override
    public void updateStatus(String id, Integer status) {
        //根据用户id查询
         SysUser user=baseMapper.selectById(id);
        //设置修改状态
        user.setStatus(status);
        //调用方法修改
        baseMapper.updateById(user);
    }

    @Override
    public SysUser getUserInfoByUsername(String username) {
        QueryWrapper<SysUser> sysUserQueryWrapper=new QueryWrapper<>();
        sysUserQueryWrapper.eq("username",username);
        SysUser sysUser=baseMapper.selectOne(sysUserQueryWrapper);
        return sysUser;
    }

    @Override
    public Map<String, Object> getUserInfo(String username) {
        //根据username查询用户基本信息
        SysUser sysUser=this.getUserInfoByUsername(username);
        //根据userId查询菜单权限值
        List<RouterVo> routerVoList=sysMenuService.getUserMenuList(sysUser.getId());
        //根据userId查询按钮权限值
        List<String> permsList=sysMenuService.getUserButtonList(sysUser.getId());
        Map<String,Object> result=new HashMap<>();
        result.put("name",username);
        result.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        result.put("roles","[\"admin\"]");
        //菜单权限数据
        result.put("routers",routerVoList);
        //按钮权限数据
        result.put("buttons",permsList);
        return result;
    }
}




