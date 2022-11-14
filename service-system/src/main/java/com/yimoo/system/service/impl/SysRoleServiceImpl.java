package com.yimoo.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimoo.model.system.SysRole;
import com.yimoo.model.system.SysUserRole;
import com.yimoo.model.vo.AssginRoleVo;
import com.yimoo.model.vo.SysRoleQueryVo;
import com.yimoo.system.mapper.SysUserRoleMapper;
import com.yimoo.system.service.SysRoleService;
import com.yimoo.system.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author haoyang
* @description 针对表【sys_role(角色)】的数据库操作Service实现
* @createDate 2022-11-02 17:58:06
*/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService{

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    //条件分页查询
    @Override
    public IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo) {
        IPage<SysRole> page=baseMapper.selectPage(pageParam,sysRoleQueryVo);
        return page;
    }

    //获取用户的角色数据
    @Override
    public Map<String, Object> getRoleByUserId(String userId) {
        //获取所有的角色
        List<SysRole> roles=baseMapper.selectList(null);
        //根据用户id查询，已经分配的角色数据
        QueryWrapper<SysUserRole> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<SysUserRole> userRoles=sysUserRoleMapper.selectList(wrapper);
        //从userRoles获取所有角色ID
        List<String> userRoleIds=new ArrayList<>();
        for(SysUserRole userRole:userRoles){
            String roleId= userRole.getRoleId();
            userRoleIds.add(roleId);
        }
        //创建返回的map
        Map<String,Object> map=new HashMap<>();
        map.put("allRoles",roles);
        map.put("userRoleIds",userRoleIds);
        return map;
    }

    //用户分配角色
    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {
        //根据用户id删除之前分配角色
        QueryWrapper<SysUserRole> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id",assginRoleVo.getUserId());
        sysUserRoleMapper.delete(wrapper);
        //获取所有角色id,添加角色用户关系表
        //角色id列表
        List<String> roleIdList=assginRoleVo.getRoleIdList();
        for(String roleId:roleIdList){
            SysUserRole sysUserRole=new SysUserRole();
            sysUserRole.setUserId(assginRoleVo.getUserId());
            sysUserRole.setRoleId(roleId);
               sysUserRoleMapper.insert(sysUserRole);
        }
    }
}




