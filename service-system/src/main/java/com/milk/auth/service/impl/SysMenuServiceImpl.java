package com.milk.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.milk.auth.exce.CustomerException;
import com.milk.auth.mapper.SysMenuMapper;
import com.milk.auth.mapper.SysRoleMenuMapper;
import com.milk.auth.service.SysMenuService;
import com.milk.common.ResultEnum;
import com.milk.common.TreeUtils;
import com.milk.model.params.RoleMenuParam;
import com.milk.model.pojo.SysMenu;
import com.milk.model.pojo.SysRoleMenu;
import com.milk.model.vo.RouterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/4 17:23
 */
@Transactional
@Service
@Slf4j
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper,SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> treeList() {

        List<SysMenu> menuList = this.list();

//        转换树形列表
        List<SysMenu> sysMenus= TreeUtils.buildTree(menuList);

        return sysMenus;
    }

    @Override
    public List<SysMenu> findSysMenuByRoleId(Long roleId) {

        if (roleId == null){
            throw new CustomerException(ResultEnum.ARGUMENT_VALID_ERROR);
        }

        //获取所有status为1的权限列表
        List<SysMenu> menuList = sysMenuMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1));
        //根据角色id获取角色权限
        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(new QueryWrapper<SysRoleMenu>().eq("role_id",roleId));
        //获取该角色已分配的所有权限id
        List<String> roleMenuIds = new ArrayList<>();
        for (SysRoleMenu roleMenu : roleMenus) {
            roleMenuIds.add(roleMenu.getMenuId().toString());
        }
        //遍历所有权限列表
        for (SysMenu sysMenu : menuList) {
            if(roleMenuIds.contains(sysMenu.getId())){
                //设置该权限已被分配
                sysMenu.setSelect(true);
            }else {
                sysMenu.setSelect(false);
            }
        }
        //将权限列表转换为权限树
        List<SysMenu> sysMenus = TreeUtils.buildTree(menuList);
        return sysMenus;
    }

    @Override
    public boolean doAssign(RoleMenuParam roleMenuParam) {

        if (roleMenuParam == null){
            throw new CustomerException(ResultEnum.ARGUMENT_VALID_ERROR);
        }
        //删除已分配的权限
        sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("role_id",roleMenuParam.getRoleId()));

        //添加新权限
        sysRoleMenuMapper.addRoleMenu(roleMenuParam.getRoleId(),roleMenuParam.getMenuIdList());

        return true;
    }

    @Override
    public List<RouterVo> findUserMenuList(Long userId) {

        List<SysMenu> sysMenus = null;
//      超级管理员拥有所哟权限
        if(userId==1){
             sysMenus=sysMenuMapper.selectList(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus,1));
        }else{
            sysMenus = sysMenuMapper.findUserRoleList(userId);
        }

        sysMenus = TreeUtils.buildTree(sysMenus);
        log.info("Tree后:{}",sysMenus);
        List<RouterVo> routerVoList = TreeUtils.buildRouters(sysMenus);
        return routerVoList;
    }

    @Override
    public List<String> findUserPermsList(Long userId) {

        List<SysMenu> sysMenus = null;
//        超级管理员拥有所哟权限
        if(userId==1){
           sysMenus = sysMenuMapper.selectList(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus,1));
        }else{
            sysMenus = sysMenuMapper.findUserRoleList(userId);
        }

        List<String> permissionList = sysMenus.stream()
                .filter(item -> item.getType() ==2)
                .map(item -> item.getPerms())
                .collect(Collectors.toList());
        return permissionList;
    }

    @Override
    public boolean changeStatus(Long id, Integer status) {

        int count = this.count(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, id).eq(SysMenu::getStatus,1));
        if (count >0 ){
            throw new CustomerException(ResultEnum.NODE_ERROR);
        }

        SysMenu menu = new SysMenu();
        menu.setId(id.toString());

        menu.setStatus(status==1 ? 0 : 1);

        this.updateById(menu);
        return true;
    }

    @Override
    public boolean removeMenu(Long id) {

        int count= this.count(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, id).eq(SysMenu::getStatus,1));
        if (count >0 ){
            throw new CustomerException(ResultEnum.NODE_ERROR);
        }
        this.removeById(id);
        return true;
    }

}
