package com.milk.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.milk.auth.service.SysMenuService;
import com.milk.auth.service.SysUserService;
import com.milk.model.pojo.SysUser;
import com.milk.auth.custom.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/7 22:43
 */

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser user = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));

        if(null == user) {
            throw new UsernameNotFoundException("用户名不存在！");
        }

        if(user.getStatus().intValue() == 0) {
            throw new RuntimeException("账号已停用");
        }

        List<String> userPermsList = sysMenuService.findUserPermsList(Long.valueOf(user.getId()));

        List<SimpleGrantedAuthority> authorities=new ArrayList<>();

        for (String per : userPermsList) {
            authorities.add(new SimpleGrantedAuthority(per.trim()));
        }
        return new CustomUser(user, authorities);
    }
}
