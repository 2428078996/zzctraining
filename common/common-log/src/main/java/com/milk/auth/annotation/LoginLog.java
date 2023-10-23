package com.milk.auth.annotation;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginLog {
    String value() default "登录";
}
