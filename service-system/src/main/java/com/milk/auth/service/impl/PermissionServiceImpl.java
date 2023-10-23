package com.milk.auth.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import com.milk.auth.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author : beibeioo
 * @Date : 2023/10/22 17:56
 * @Description 存储用户所拥有的权限
 */


@Component
@Slf4j
public class PermissionServiceImpl implements StpInterface {

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public List<String> getPermissionList(Object loginId, String s) {

        List<String> userPermsList = sysMenuService.findUserPermsList(Long.parseLong((String)loginId));
        log.info("权限：{}",userPermsList);
        return userPermsList;
    }

    @Override
    public List<String> getRoleList(Object loginId, String s) {
        return null;
    }
}
