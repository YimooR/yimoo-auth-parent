package com.yimoo.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimoo.common.result.Result;
import com.yimoo.model.system.SysUser;
import com.yimoo.model.vo.SysRoleQueryVo;
import com.yimoo.model.vo.SysUserQueryVo;
import com.yimoo.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName SysUserController
 * @Description TODO
 * @date 2022/11/10 20:09
 * @Version 1.0
 * @Author hao yang
 */

@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("角色列表")
    @GetMapping("{page}/{limit}")
    public Result findPageQueryUser(
            @PathVariable int limit,
            @PathVariable int page,
            SysUserQueryVo sysUserQueryVo){
        //创建一个page对象
        Page<SysUser> pageParam=new Page<>(page,limit);
        //调用selectPage方法
        IPage<SysUser> userIPage =sysUserService.selectPage(pageParam,sysUserQueryVo);
        return Result.ok(userIPage);
    }

    @ApiOperation("添加用户")
    @PostMapping("save")
    public Result save(@RequestBody SysUser sysUser){
        boolean isSuccess=sysUserService.save(sysUser);
        if(isSuccess){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    @ApiOperation("查询用户id")
    @GetMapping("findUserById/{id}")
    public Result findUserById(@PathVariable String id){
        SysUser sysUser=sysUserService.getById(id);
        return Result.ok(sysUser);
    }

    @ApiOperation("修改用户")
    @PostMapping("update")
    public Result update(@RequestBody SysUser sysUser){
        boolean isSuccess=sysUserService.saveOrUpdate(sysUser);
        if(isSuccess){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    @ApiOperation("删除用户")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id){
        boolean isSuccess=sysUserService.removeById(id);
        if(isSuccess){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    @ApiOperation("批量删除")
    @DeleteMapping("batchDelete")
    public Result batchDelete(@RequestBody List<Long> ids){
        boolean isSuccess=sysUserService.removeByIds(ids);
        if(isSuccess){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    @ApiOperation("用户状态")
    @GetMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable String id, @PathVariable Integer status){
        sysUserService.updateStatus(id,status);
        return Result.ok();
    }
}
