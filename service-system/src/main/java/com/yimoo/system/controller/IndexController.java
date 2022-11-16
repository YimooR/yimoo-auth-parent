package com.yimoo.system.controller;

import com.yimoo.common.result.Result;
import com.yimoo.common.utils.JwtHelper;
import com.yimoo.common.utils.MD5;
import com.yimoo.model.system.SysUser;
import com.yimoo.model.vo.LoginVo;
import com.yimoo.system.exception.YimooException;
import com.yimoo.system.service.SysUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName IndexController
 * @Description TODO
 * @date 2022/11/8 19:50
 * @Version 1.0
 * @Author hao yang
 */
@Api(tags = "用户登录接口")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    //http://localhost:9528/dev-api/vue-admin-template/user/login
    //{"code":20000,"data":{"token":"admin-token"}}
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo){
      //根据username查询数据
       SysUser sysUser =sysUserService.getUserInfoByUsername(loginVo.getUsername());
        //如果查询为空
        if(sysUser==null){
            throw new YimooException(20001,"用户不存在！");
        }
        //判断密码是否一致
        String md5Password=MD5.encrypt(loginVo.getPassword());
        if(!sysUser.getPassword().equals(md5Password)){
            throw new YimooException(20001,"密码不存在！");
        }
        //判断当前用户是否可用
        if(sysUser.getStatus().intValue()==0){
            throw new YimooException(20001,"用户已被禁用！");
        }
        //根据用户的id和名称生产token返回
        String token=JwtHelper.createToken(sysUser.getId(),sysUser.getUsername());
        Map<String,Object> map=new HashMap<>();
        map.put("token",token);
        return Result.ok(map);
    }

    //{"code":20000,"data":{"roles":["admin"],"introduction":"I am a super administrator","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif","name":"Super Admin"}}
    @GetMapping("info")
    public Result info(HttpServletRequest request){
        //获取请求头token字符串
        String token=request.getHeader("token");
        //从token字符串获取用户名称（id）
        String username=JwtHelper.getUsername(token);
        //根据用户名称获取用户信息（基本信息和菜单权限和按钮权限数据）
        Map<String,Object> map=sysUserService.getUserInfo(username);
        return Result.ok(map);
    }
}
