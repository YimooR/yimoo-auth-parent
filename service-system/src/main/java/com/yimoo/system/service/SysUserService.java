package com.yimoo.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yimoo.model.system.SysUser;
import com.yimoo.model.vo.SysUserQueryVo;
import io.swagger.models.auth.In;

import java.util.Map;

/**
* @author haoyang
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2022-11-10 20:06:37
*/
public interface SysUserService extends IService<SysUser> {

    IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo sysUserQueryVo);

    void updateStatus(String id, Integer status);

    SysUser getUserInfoByUsername(String username);

    Map<String, Object> getUserInfo(String username);
}
