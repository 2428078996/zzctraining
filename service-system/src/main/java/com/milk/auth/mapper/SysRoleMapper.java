package com.milk.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.milk.model.pojo.SysRole;
import org.apache.ibatis.annotations.Param;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/2 19:11
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    IPage<SysRole> pageList(Page<SysRole> page, @Param("name") String roleQuery);
}
