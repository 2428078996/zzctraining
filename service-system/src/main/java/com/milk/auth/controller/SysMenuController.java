package com.milk.auth.controller;

import com.milk.auth.service.SysMenuService;
import com.milk.common.R;
import com.milk.model.params.RoleMenuParam;
import com.milk.model.pojo.SysMenu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/4 17:20
 */

@Api(tags = "菜单模块")
@RestController
@Slf4j
@RequestMapping("/admin/system/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation(value = "获取菜单")
    @GetMapping("/treeList")
    public R findNodes() {
        List<SysMenu> list = sysMenuService.treeList();
        return R.success(list);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping("/save")
    public R save(@RequestBody SysMenu permission) {
        sysMenuService.save(permission);
        return R.success();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("/update")
    public R updateById(@RequestBody SysMenu permission) {
        sysMenuService.updateById(permission);
        return R.success();
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable Long id) {
        sysMenuService.removeById(id);
        return R.success();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("/toAssign/{roleId}")
    public R toAssign(@PathVariable Long roleId) {
        List<SysMenu> sysMenuList = sysMenuService.findSysMenuByRoleId(roleId);
        return R.success(sysMenuList);
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public R doAssign(@RequestBody RoleMenuParam roleMenuParam) {
        sysMenuService.doAssign(roleMenuParam);
        return R.success("分配成功！");
    }

}
