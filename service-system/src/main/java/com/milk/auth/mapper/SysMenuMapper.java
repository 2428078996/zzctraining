package com.milk.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.milk.model.pojo.SysMenu;

import java.util.List;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/4 17:22
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<SysMenu> findUserRoleList(Long userId);
}

