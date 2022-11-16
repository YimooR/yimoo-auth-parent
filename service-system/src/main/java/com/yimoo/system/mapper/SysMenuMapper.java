package com.yimoo.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yimoo.model.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author haoyang
* @description 针对表【sys_menu(菜单表)】的数据库操作Mapper
* @createDate 2022-11-14 22:10:22
* @Entity com.yimoo.system.pojo.SysMenu
*/
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    //根据userId查询菜单权限数据
    List<SysMenu> findMenuListUserId(@Param("userId") String userId);
}




