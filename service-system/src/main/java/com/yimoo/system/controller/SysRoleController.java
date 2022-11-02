package com.yimoo.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimoo.common.result.Result;
import com.yimoo.model.system.SysRole;
import com.yimoo.model.vo.SysRoleQueryVo;
import com.yimoo.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName SysRoleController
 * @Description TODO
 * @date 2022/11/2 18:42
 * @Version 1.0
 * @Author hao yang
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 查询所有角色
     */
    @ApiOperation(value = "查询所有角色")
    @GetMapping("findAll")
    public Result<List<SysRole>> findAllRole(){
        List<SysRole> sysRoleList=sysRoleService.list();
        return Result.ok(sysRoleList);
    }

    /**
     * 逻辑删除
     */
    @ApiOperation(value = "删除指定角色")
    @DeleteMapping("remove/{id}")
    public Result<SysRole> removeRole(@PathVariable Long id){
         //调用方法删除
        boolean isSuccess=sysRoleService.removeById(id);
        if(isSuccess){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    /**
     * 条件分页查询
     * @param limit
     * @param page
     * @param sysRoleQueryVo
     * @return
     */
    @ApiOperation(value = "条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result findPageQueryRole(
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "page", value = "当前页码", required = true)
                                             @PathVariable Long page,
            @ApiParam(name = "roleQueryVo", value = "查询对象", required = false)
                                             SysRoleQueryVo sysRoleQueryVo){
        Page<SysRole> pageParam=new Page<>(page,limit);
        IPage<SysRole> iPage =sysRoleService.selectPage(pageParam,sysRoleQueryVo);
        return Result.ok(iPage);
    }
}