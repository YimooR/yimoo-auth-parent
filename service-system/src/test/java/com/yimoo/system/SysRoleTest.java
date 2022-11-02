package com.yimoo.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yimoo.model.system.SysRole;
import com.yimoo.system.mapper.SysRoleMapper;
import com.yimoo.system.service.SysRoleService;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SysRoleTest {

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Test
    void demo1(){
        List<SysRole> sysRoleList=sysRoleService.list();
        sysRoleList.forEach(System.out::println);
    }

    @Test
    void demo2(){
        QueryWrapper<SysRole> sysRoleQueryWrapper=new QueryWrapper<>();
        sysRoleQueryWrapper.eq("role_name","普通管理员");
        List<SysRole> sysRoleList=sysRoleMapper.selectList(sysRoleQueryWrapper);
        sysRoleList.forEach(System.out::println);
    }


    @Test
    void demo3(){
        SysRole sysRole=new SysRole();
        sysRole.setRoleName("测试用户2");
        sysRole.setRoleCode("testManger2");
        sysRole.setDescription("测试用户2");
        boolean result=sysRoleService.saveOrUpdate(sysRole);
        if (result){
            System.out.println("插入成功！");
        }else{
            System.out.println("插入失败！");
        }
    }

    @Test
    void demo4(){
        SysRole sysRole=new SysRole();
        sysRole.setRoleName("yimoo");
        QueryWrapper<SysRole> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",1587751133196238851l);
        boolean result=sysRoleService.saveOrUpdate(sysRole,queryWrapper);
        if (result){
            System.out.println("插入成功！");
        }else{
            System.out.println("插入失败！");
        }
    }

    @Test
    void testCount(){
        int num=sysRoleService.count();
        System.out.println(num);
    }

    @Test
    void testLogicDelete(){
        boolean result=sysRoleService.removeById(1587751133196238851l);
        if (result){
            System.out.println("删除成功！");
        }else{
            System.out.println("删除失败！");
        }
    }

    @Test
    void testBatchDelete(){
        List<Long> lists=new ArrayList<>();
        lists.add(1587751133196238850l);
        lists.add(1587751133196238851l);
        sysRoleService.removeByIds(lists);
    }
}
