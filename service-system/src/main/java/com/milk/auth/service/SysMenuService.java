package com.milk.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.milk.model.params.RoleMenuParam;
import com.milk.model.pojo.SysMenu;
import com.milk.model.vo.RouterVo;

import java.util.List;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/4 17:23
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> treeList();

    List<SysMenu> findSysMenuByRoleId(Long roleId);

    boolean doAssign(RoleMenuParam roleMenuParam);

    List<RouterVo> findUserMenuList(Long userId);

    List<String> findUserPermsList(Long userId);

}
