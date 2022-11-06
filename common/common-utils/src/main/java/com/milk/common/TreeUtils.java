package com.milk.common;

import com.milk.model.pojo.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/6 17:06
 */
public class TreeUtils {

    public static List<SysMenu> treeList( List<SysMenu> menuList){

        List<SysMenu> sysMenus=new ArrayList<>();
        for (SysMenu sysMenu : menuList) {
            if (sysMenu.getParentId() == 0){
                sysMenus.add(findChildren(sysMenu,menuList));
            }
        }

        return sysMenus;
    }

    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> menuList) {

        sysMenu.setChildren(new ArrayList<SysMenu>());

        for (SysMenu menu : menuList) {

            if(sysMenu.getId().equals(menu.getParentId().toString()) ) {
                if (sysMenu.getChildren() == null) {
                    sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add(findChildren(menu,menuList));
            }

        }
        return sysMenu;
    }
}
