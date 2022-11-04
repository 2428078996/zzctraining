package com.milk.auth.controller;

import com.milk.common.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 登录
     * @return
     */
    @PostMapping("/login")
    public R login() {
        Map<String, Object> map = new HashMap<>();
        map.put("token","admin");
        return R.success(map);
    }
    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/info")
    public R info() {
        Map<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name","admin");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return R.success(map);
    }
    /**
     * 退出
     * @return
     */
    @PostMapping("/logout")
    public R logout(){
        return R.success();
    }
}
