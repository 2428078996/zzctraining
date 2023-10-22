package com.milk.common;

import com.milk.model.pojo.SysDept;
import com.milk.model.pojo.SysMenu;
import com.milk.model.vo.MetaVo;
import com.milk.model.vo.RouterVo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/6 17:06
 */
public class TreeUtils {

    public static List<SysMenu> buildTree(List<SysMenu> menuList){

        List<SysMenu> sysMenus = menuList.stream().filter(menu -> {
            return menu.getParentId() == 0;
        }).map(menu -> {
             menu.setChildren(findChildren(menu, menuList));
             return menu;
        }).collect(Collectors.toList());
        return sysMenus;
    }

    /**
     * 根据菜单构建路由
     * @param menus
     * @return
     */
    public static List<RouterVo> buildRouters(List<SysMenu> menus) {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (SysMenu menu : menus) {
            RouterVo router = new RouterVo();
            router.setHidden(false);
            router.setAlwaysShow(false);
            router.setPath(getRouterPath(menu));
            router.setComponent(menu.getComponent());
            router.setMeta(new MetaVo(menu.getName(), menu.getIcon()));
            List<SysMenu> children = menu.getChildren();
            //如果当前是菜单，需将按钮对应的路由加载出来，如：“角色授权”按钮对应的路由在“系统管理”下面
            if(menu.getType().intValue() == 1) {
                List<SysMenu> hiddenMenuList = children.stream().filter(item -> !Strings.isEmpty(item.getComponent())).collect(Collectors.toList());
                for (SysMenu hiddenMenu : hiddenMenuList) {
                    RouterVo hiddenRouter = new RouterVo();
                    hiddenRouter.setHidden(true);
                    hiddenRouter.setAlwaysShow(false);
                    hiddenRouter.setPath(getRouterPath(hiddenMenu));
                    hiddenRouter.setComponent(hiddenMenu.getComponent());
                    hiddenRouter.setMeta(new MetaVo(hiddenMenu.getName(), hiddenMenu.getIcon()));
                    routers.add(hiddenRouter);
                }
            } else {
                if (!CollectionUtils.isEmpty(children)) {
                    if(children.size() > 0) {
                        router.setAlwaysShow(true);
                    }
                    router.setChildren(buildRouters(children));
                }
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public static String getRouterPath(SysMenu menu) {
        String routerPath = "/" + menu.getPath();
        if(menu.getParentId().intValue() != 0) {
            routerPath = menu.getPath();
        }
        return routerPath;
    }

    private static List<SysMenu> findChildren(SysMenu sysMenu, List<SysMenu> menuList) {

        List<SysMenu> sysMenuList = menuList.stream().filter(menu -> {
            return menu.getParentId().toString().equals(sysMenu.getId());
        }).map(menu -> {
            menu.setChildren(findChildren(menu, menuList));
            return menu;
        }).collect(Collectors.toList());
        return sysMenuList;
    }

    public static List<SysDept> buildDeptTree(List<SysDept> deptList) {

        List<SysDept> sysDeptList=new ArrayList<>();

        for (SysDept sysDept : deptList) {

            if (sysDept.getParentId().longValue()==0){
                sysDeptList.add(findDeptChildren(sysDept,deptList));
            }
        }

        return sysDeptList;
    }

    private static SysDept findDeptChildren(SysDept sysDept, List<SysDept> deptList) {

        sysDept.setChildren(new ArrayList<>());

        for (SysDept dept : deptList) {

            if (sysDept.getId().equals(dept.getParentId().toString())){

                if (sysDept.getChildren()==null){
                    sysDept.setChildren(new ArrayList<>());
                }
                sysDept.getChildren().add(findDeptChildren(dept,deptList));
            }
        }

        return sysDept;

    }
}
