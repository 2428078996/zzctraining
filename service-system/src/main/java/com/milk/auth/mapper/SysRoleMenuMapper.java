package com.milk.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.milk.model.pojo.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/5 12:44
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    boolean addRoleMenu(@Param("roleId") String  roleId, @Param("menuIds")List<String> menuIds);

    void removeByRoleId(@Param("roleId") Long id);
}
