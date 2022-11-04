package com.milk.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.milk.model.params.UserRoleParam;
import com.milk.model.pojo.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/4 15:04
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {


    boolean batchInsert(@Param("userRole")UserRoleParam userRoleParam);

}
