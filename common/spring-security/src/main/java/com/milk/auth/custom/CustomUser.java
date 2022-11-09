package com.milk.auth.custom;

import com.milk.model.pojo.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/7 22:40
 */
public class CustomUser extends User {

    private SysUser sysUser;


    public CustomUser(SysUser user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getName(), user.getPassword(), authorities);
        this.sysUser= user;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }
}
