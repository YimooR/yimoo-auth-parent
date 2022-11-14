package com.yimoo.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimoo.model.system.SysUser;
import com.yimoo.model.vo.SysUserQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author haoyang
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2022-11-10 20:06:37
* @Entity com.yimoo.system.pojo.SysUser
*/
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    IPage<SysUser> selectPage(Page<SysUser> pageParam, @Param("vo") SysUserQueryVo sysUserQueryVo);
}




