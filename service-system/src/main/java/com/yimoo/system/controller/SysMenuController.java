package com.yimoo.system.controller;

import com.yimoo.common.result.Result;
import com.yimoo.model.system.SysMenu;
import com.yimoo.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName SysMenuController
 * @Description TODO
 * @date 2022/11/14 22:12
 * @Version 1.0
 * @Author hao yang
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    //菜单列表（树形）
    @ApiOperation("菜单列表")
    @GetMapping("findNodes")
    public Result findNodes(){
        List<SysMenu> sysMenuList=sysMenuService.findNodes();
        return Result.ok(sysMenuList);
    }

    //添加菜单
    @ApiOperation("添加菜单")
    @PostMapping("save")
    public Result save(@RequestBody SysMenu sysMenu){
        Boolean isSuccess=sysMenuService.save(sysMenu);
        if (isSuccess){
            return  Result.ok();
        }else {
            return  Result.fail();
        }
    }

    //根据id查询
    @ApiOperation("根据id查询菜单")
    @GetMapping("findNode/{id}")
    public Result findNode(@PathVariable String id){
        SysMenu sysMenu=sysMenuService.getById(id);
        return Result.ok(sysMenu);
    }

    //修改菜单
    @ApiOperation("修改菜单")
    @PostMapping("update")
    public Result update(@RequestBody SysMenu sysMenu){
        Boolean isSuccess=sysMenuService.updateById(sysMenu);
        if (isSuccess){
            return  Result.ok();
        }else {
            return  Result.fail();
        }

    }

    //删除菜单
    @ApiOperation("删除菜单")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id){
        Boolean isSuccess=sysMenuService.removeMenuById(id);
        if (isSuccess){
            return  Result.ok();
        }else {
            return  Result.fail();
        }
    }
}
