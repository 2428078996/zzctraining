package com.milk.auth.service.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/4 17:23
 */
@Transactional
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper,SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> treeList() {

        List<SysMenu> menuList = this.list();

//        转换树形列表
        List<SysMenu> sysMenus= TreeUtils.treeList(menuList);

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
            roleMenuIds.add(roleMenu.getMenuId());
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
        List<SysMenu> sysMenus = TreeUtils.treeList(menuList);
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

}
