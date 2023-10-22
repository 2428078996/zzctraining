package com.milk.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.milk.model.params.LoginParam;
import com.milk.model.params.UserPageParam;
import com.milk.model.params.UserRoleParam;
import com.milk.model.pojo.SysUser;
import com.milk.model.vo.UserInfoVo;

import java.util.Map;


/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/3 22:35
 */
public interface SysUserService extends IService<SysUser> {

    IPage<SysUser> selectPage(UserPageParam userPageParam);

    boolean updateStatus(Long id,Integer status);

    Map<String, Object> getRoleByUserId(Long userId);

    boolean doAssign(UserRoleParam userRoleParam);

    SysUser login(LoginParam loginParam);

    UserInfoVo getUserInfo();

    boolean logout();

    boolean checkTempToken(String tempToken);

    void saveTempToken(String token);
}
