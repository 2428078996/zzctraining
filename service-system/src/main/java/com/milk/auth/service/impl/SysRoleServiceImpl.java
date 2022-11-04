package com.milk.auth.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.milk.auth.mapper.SysRoleMapper;
import com.milk.auth.service.SysRoleService;
import com.milk.model.params.RolePageParam;
import com.milk.model.pojo.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/2 19:14
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Override
    public IPage<SysRole> pageList(RolePageParam rolePageParam) {

        Page<SysRole> page = new Page<>(rolePageParam.getPage(), rolePageParam.getPageSize());
        IPage<SysRole> pageInfo = sysRoleMapper.pageList(page, rolePageParam.getQueryParams());
        return pageInfo;
    }
}
