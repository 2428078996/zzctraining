package com.milk.auth.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.temp.SaTempUtil;
import cn.dev33.satoken.util.SaResult;
import com.milk.auth.service.SysUserService;
import com.milk.common.R;
import com.milk.common.VerifyImgUtil;
import com.milk.model.params.LoginParam;
import com.milk.model.pojo.SysUser;
import com.milk.model.vo.UserInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

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

    @Autowired
    private VerifyImgUtil verifyImgUtil;


    /**
     * 在获取验证码接口和登录接口前，先调用，获取一个临时token，用来作为验证码的key
     * @return
     */

    @SaIgnore
    @GetMapping("tempToken")
    public R getTempToken(@RequestParam("tokenKey") String tokenKey) {
        String token = SaTempUtil.createToken(tokenKey, 5 * 60);
        sysUserService.saveTempToken(token);
        return R.success(token);
    }

    /**
     * 获取图片验证码
     * @param tempToken 获取到的临时token
     * @param response
     */
    @SaIgnore
    @GetMapping("getVerifyImg")
    public SaResult getVerifyImg(@RequestParam("tempToken") String tempToken, HttpServletResponse response) throws IOException {
        if (!sysUserService.checkTempToken(tempToken)) {
            return SaResult.error( "获取验证码失败！");
        }
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        OutputStream os = response.getOutputStream();
        verifyImgUtil.lineCaptcha(os, tempToken);
        try {
            os.flush();
        } finally {
            os.close();
        }
        return null;
    }



    /**
     * 登录
     * @return
     */
    @ApiOperation(value="登录")
    @PostMapping("/login")
    public R login(@RequestBody LoginParam loginParam) {

        SysUser user = sysUserService.login(loginParam);

        StpUtil.login(user.getId());
        SaTempUtil.deleteToken(loginParam.getTempToken());

        return R.success().add("token",StpUtil.getTokenValue());
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
    @SaIgnore
    @ApiOperation(value = "退出操作")
    @PostMapping("/logout")
    public R logout(){
        sysUserService.logout();
        return R.success();
    }


}
