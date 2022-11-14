package com.yimoo.system.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimoo.model.system.SysUser;
import com.yimoo.model.vo.SysUserQueryVo;
import com.yimoo.system.service.SysUserService;
import com.yimoo.system.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

/**
* @author haoyang
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2022-11-10 20:06:37
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{
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
}




