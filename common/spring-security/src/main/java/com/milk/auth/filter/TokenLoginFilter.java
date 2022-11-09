package com.milk.auth.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.milk.auth.service.AsyncLoginLogService;
import com.milk.common.*;
import com.milk.model.params.LoginParam;
import com.milk.auth.custom.CustomUser;
import com.milk.model.vo.LoginLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * @Description 登录过滤器，继承UsernamePasswordAuthenticationFilter，对用户名密码进行登录校验
 * @Author @Milk
 * @Date 2022/11/8 11:06
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private RedisTemplate redisTemplate;

    private AsyncLoginLogService loginLogService;

    public TokenLoginFilter(AuthenticationManager authenticationManager, RedisTemplate redisTemplate) {
        this.setAuthenticationManager(authenticationManager);
        this.setPostOnly(false);
        //指定登录接口及提交方式，可以指定任意路径
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/system/index/login","POST"));

        this.redisTemplate=redisTemplate;
    }

    /*
    * 登录认证
    * */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            LoginParam loginParam = new ObjectMapper().readValue(request.getInputStream(), LoginParam.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(loginParam.getUsername(), loginParam.getPassword());

            return this.getAuthenticationManager().authenticate(authentication);
        }catch(IOException e){
            throw new RuntimeException(e);
        }


    }

    /*
    * 登陆成功
    * */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        CustomUser customUser =(CustomUser) authResult.getPrincipal();

        String token = TokenUtils.createToken(customUser.getSysUser().getUsername(), customUser.getSysUser().getId());

        Collection<GrantedAuthority> authorities = customUser.getAuthorities();


        redisTemplate.opsForValue().set(customUser.getSysUser().getUsername(), JSON.toJSONString(authorities));

        //记录日志
        loginLogService.recordLoginLog(customUser.getUsername(), 1, IpUtils.getIpAddress(request), "登录成功");

        ResponseUtils.out(response, R.success().add("token",token));

    }


    /*
    * 登录失败
    * */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
      if (failed.getCause() instanceof RuntimeException){
          ResponseUtils.out(response,R.fail(204,failed.getMessage()));
      }else {
          ResponseUtils.out(response,R.fail(ResultEnum.LOGIN_MOBILE_ERROR));
      }
    }
}
