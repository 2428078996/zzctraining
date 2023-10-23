package com.milk.auth.aspectJ;

import com.alibaba.fastjson.JSON;
import com.milk.auth.annotation.CacheAuth;
import com.milk.common.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * @Description 缓存文件,已使用spring cache + redis 实现
 * @Author @Milk
 * @Date 2022/11/10 16:53
 */

@Aspect
@Slf4j
@Component
public class CacheAspect {

    @Autowired
    private RedisTemplate<String ,String> redisTemplate;

    @Pointcut("@annotation(com.milk.auth.annotation.CacheAuth)")
    public void pointcut(){}


    @Around("pointcut()")
    public Object  around(ProceedingJoinPoint joinPoint){

        try {
            Signature signature = joinPoint.getSignature();
            String className= joinPoint.getTarget().getClass().getSimpleName();
            String methodName=signature.getName();

            Object[] args = joinPoint.getArgs();
            Class[] parameterType=new Class[args.length];

            String params="";

            for (int i = 0; i < args.length; i++) {

                if (args[i]!=null){
                    params+= JSON.toJSONString(args[i]);
                    parameterType[i]=args[i].getClass();
                }else{
                    parameterType[i]=null;
                }
            }

            if(Strings.isNotEmpty(params)){
                params= DigestUtils.md5DigestAsHex(params.getBytes()) ;
            }

            Method method = signature.getDeclaringType().getMethod(methodName, parameterType);

            CacheAuth cache = method.getAnnotation(CacheAuth.class);

            long overtime = cache.overtime();

            String name = cache.name();

            String redisKey=name+"::" +className+"::" +methodName+"::"+params;

            String redisValue=redisTemplate.opsForValue().get(redisKey);

            if (Strings.isNotEmpty(redisValue)){
                log.info("走了缓存----,{},{}",className,methodName);
                return JSON.parseObject(redisValue, R.class);
            }

            Object proceed = joinPoint.proceed();

            redisTemplate.opsForValue().set(redisKey,JSON.toJSONString(proceed), Duration.ofMillis(overtime));

            log.info("添加缓存,{},{}",className,methodName);
            return proceed;
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }
        return R.fail(999,"系统错误！");
    }
}
