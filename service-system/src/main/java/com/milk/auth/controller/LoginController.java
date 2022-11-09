package com.milk.auth.controller;

import com.milk.auth.service.SysUserService;
import com.milk.common.MD5Utils;
import com.milk.common.R;
import com.milk.common.RequestUtils;
import com.milk.common.TokenUtils;
import com.milk.model.params.LoginParam;
import com.milk.model.vo.UserInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/3 12:15
 */
@Api(tags = "后台登录管理")
@RestController
@RequestMapping("/admin/system/index")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录
     * @return
     */
    @ApiOperation(value="登录")
    @PostMapping("/login")
    public R login(@RequestBody LoginParam loginParam) {

        String token=sysUserService.login(loginParam);

        return R.success().add("token",token);
    }
    /**
     * 获取用户信息
     * @return
     */
    @ApiOperation( value="获取用户信息")
    @GetMapping("/info")
    public R info() {

        UserInfoVo userInfo=sysUserService.getUserInfo();
        return R.success(userInfo);
    }
    /**
     * 退出
     * @return
     */
    @ApiOperation(value = "退出操作")
    @PostMapping("/logout")
    public R logout(){
        sysUserService.logout();

        return R.success();
    }


}
