package com.milk.auth.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * @Author : beibeioo
 * @Date : 2023/10/22 10:46
 * @Description Is Description
 */

@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    public void addInterceptors(InterceptorRegistry registry){

        List<String> notMatch =  Arrays.asList("/admin/system/index/login","/favicon.ico","/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/doc.html");
            registry.addInterceptor(new SaInterceptor(handel ->{
                        SaRouter.match("/**")
                                .notMatch(notMatch)
                                .check(r-> StpUtil.checkLogin());
            })).addPathPatterns("/**")
                    .excludePathPatterns(notMatch);
    }


    public void addCorsMappings(CorsRegistry registry){
        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/cors/**")
                .allowedHeaders("*")
                .allowedMethods("POST","GET")
                .allowedOrigins("*");
    }
}
