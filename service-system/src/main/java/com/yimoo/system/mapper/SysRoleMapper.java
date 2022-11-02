package com.yimoo.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimoo.model.system.SysRole;
import com.yimoo.model.vo.SysRoleQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author haoyang
* @description 针对表【sys_role(角色)】的数据库操作Mapper
* @createDate 2022-11-02 17:58:06
* @Entity com.yimoo.system.pojo.SysRole
*/
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    //条件分页查询
    IPage<SysRole> selectPage(Page<SysRole> pageParam,@Param("vo") SysRoleQueryVo sysRoleQueryVo);
}




