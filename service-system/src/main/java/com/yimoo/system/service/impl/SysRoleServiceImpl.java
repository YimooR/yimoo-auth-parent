package com.yimoo.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimoo.model.system.SysRole;
import com.yimoo.model.vo.SysRoleQueryVo;
import com.yimoo.system.service.SysRoleService;
import com.yimoo.system.mapper.SysRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author haoyang
* @description 针对表【sys_role(角色)】的数据库操作Service实现
* @createDate 2022-11-02 17:58:06
*/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService{

    //条件分页查询
    @Override
    public IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo) {
        IPage<SysRole> page=baseMapper.selectPage(pageParam,sysRoleQueryVo);
        return page;
    }
}




