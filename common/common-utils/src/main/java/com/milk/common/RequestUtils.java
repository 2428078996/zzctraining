package com.milk.common;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/6 18:40
 */
public class RequestUtils {

    public static HttpServletRequest getRequest(){

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return  attributes.getRequest();
    }
}
