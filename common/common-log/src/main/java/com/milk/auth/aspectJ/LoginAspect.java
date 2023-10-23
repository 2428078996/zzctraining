package com.milk.auth.aspectJ;

import cn.hutool.core.date.DateTime;
import com.milk.auth.service.AsyncLoginLogService;
import com.milk.common.IpUtils;
import com.milk.common.RequestUtils;
import com.milk.model.params.LoginParam;
import com.milk.model.pojo.SysLoginLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author : beibeioo
 * @Date : 2023/10/23 9:04
 * @Description Is Description
 */

@Aspect
@Slf4j
@Component
public class LoginAspect {

    @Autowired
    private AsyncLoginLogService asyncLoginLogService;

    @AfterReturning(pointcut = "@annotation(com.milk.auth.annotation.LoginLog)",returning = "r" )
    public void successRecord(JoinPoint joinPoint,Object r){
        handelLoginLog(joinPoint,r,null);
    }

    @AfterThrowing(pointcut = "@annotation(com.milk.auth.annotation.LoginLog)",throwing = "e" )
    public void exceptionRecord(JoinPoint joinPoint,Exception e){
        handelLoginLog(joinPoint,null,e);
    }

    private void handelLoginLog(JoinPoint joinPoint, Object o, Exception e) {
        SysLoginLog sysLoginLog = new SysLoginLog();

        try {
            sysLoginLog.setStatus(1);
            sysLoginLog.setMsg("登录成功！");
            sysLoginLog.setAccessTime(new DateTime());

            Object[] args = joinPoint.getArgs();
            LoginParam loginParam = (LoginParam)args[0];
            sysLoginLog.setUsername(loginParam.getUsername());

            HttpServletRequest request = RequestUtils.getRequest();
            sysLoginLog.setIpaddr(IpUtils.getIpAddress(request));

            if (e!=null){
                sysLoginLog.setStatus(0);
                sysLoginLog.setMsg(e.getMessage());
            }
            asyncLoginLogService.saveLogin(sysLoginLog);
        } catch (Exception ex) {
            log.error("本地异常：{}",ex.getMessage());
            ex.printStackTrace();
        }

    }

}
