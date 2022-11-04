package com.milk.auth.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.milk.model.params.UserPageParam;
import com.milk.model.pojo.SysUser;
import com.milk.model.vo.SysUserVo;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/3 22:35
 */
public interface SysUserService extends IService<SysUser> {

    IPage<SysUser> selectPage(UserPageParam userPageParam);

    boolean updateStatus(Long id,Integer status);
}
