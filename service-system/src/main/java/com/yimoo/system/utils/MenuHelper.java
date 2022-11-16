package com.yimoo.system.utils;

import com.yimoo.model.system.SysMenu;
import net.sf.jsqlparser.expression.LongValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MenuHelper
 * @Description TODO
 * @date 2022/11/14 22:39
 * @Version 1.0
 * @Author hao yang
 */
public class MenuHelper {

    //构建树形结构
    //递归结构
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList){
        //创建集合封装最终数据
        List<SysMenu> trees=new ArrayList<>();
        //遍历所有菜单集合
        for(SysMenu sysMenu:sysMenuList){
            //找到递归入口，parentid=0
            Long parentId= Long.valueOf(sysMenu.getParentId());
            if(parentId==0){
                trees.add(findChildren(sysMenu,sysMenuList));
            }
        }
        return trees;
    }

    //从节点进行递归查询，查询子节点
    //判断id=parentid是否相同，如果相同是子节点，进行数据封装
    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> treeNodes) {
         //数据初始化
        sysMenu.setChildren(new ArrayList<SysMenu>());
        //遍历递归查找
        for(SysMenu it:treeNodes){
             //获取当前菜单id
            //获取所有菜单parentid
            //比对
            if(Long.valueOf(sysMenu.getId())==Long.valueOf(it.getParentId())){
                if (sysMenu.getChildren() == null) {
                    sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add(findChildren(it,treeNodes));
            }
        }
         return sysMenu;
    }
}
