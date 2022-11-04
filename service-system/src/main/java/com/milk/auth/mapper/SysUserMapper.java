package com.milk.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.milk.model.params.UserPageParam;
import com.milk.model.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/3 22:30
 */
public interface SysUserMapper extends BaseMapper<SysUser> {


    IPage<SysUser> selectPage(Page<SysUser> page,@Param("user") UserPageParam userPageParam);


}
