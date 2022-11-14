package com.yimoo.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yimoo.model.system.SysRole;
import com.yimoo.model.vo.AssginRoleVo;
import com.yimoo.model.vo.SysRoleQueryVo;

import java.util.Map;

/**
* @author haoyang
* @description 针对表【sys_role(角色)】的数据库操作Service
* @createDate 2022-11-02 17:58:06
*/
public interface SysRoleService extends IService<SysRole> {

    /**
     * 条件分页查询
     * @param pageParam
     * @param sysRoleQueryVo
     * @return
     */
    IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo);

    Map<String, Object> getRoleByUserId(String userId);

    void doAssign(AssginRoleVo assginRoleVo);
}
