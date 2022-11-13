package com.yimoo.system.controller;

import com.yimoo.common.result.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    //http://localhost:9528/dev-api/vue-admin-template/user/login
    //{"code":20000,"data":{"token":"admin-token"}}
    @PostMapping("login")
    public Result login(){
      Map<String,Object> map=new HashMap<>();
      map.put("token","admin-token Yimoo");
        return Result.ok(map);
    }

    //{"code":20000,"data":{"roles":["admin"],"introduction":"I am a super administrator","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif","name":"Super Admin"}}
    @GetMapping("info")
    public Result info(){
        Map<String,Object> map=new HashMap<>();
        map.put("roles","[admin]");
        map.put("introduction","I am YimooRua");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name","Yimoo");
        return Result.ok(map);
    }
}
