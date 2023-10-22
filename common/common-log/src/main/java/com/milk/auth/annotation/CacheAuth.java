package com.milk.auth.annotation;

import jdk.jfr.Relational;

import java.lang.annotation.*;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/10 16:51
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheAuth {
    long overtime() default 1*60*60*1000;
    String name() default "";
}
