package com.yimoo.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yimoo.model.system.SysMenu;
import com.yimoo.model.vo.AssginMenuVo;
import com.yimoo.model.vo.RouterVo;

import java.util.List;

/**
* @author haoyang
* @description 针对表【sys_menu(菜单表)】的数据库操作Service
* @createDate 2022-11-14 22:10:22
*/
public interface SysMenuService extends IService<SysMenu> {


    List<SysMenu> findNodes();

    void removeMenuById(String id);

    List<SysMenu> findMenuByRoleId(String roleId);

    void doAssign(AssginMenuVo assginMenuVo);

    List<RouterVo> getUserMenuList(String id);

    List<String> getUserButtonList(String id);
}
