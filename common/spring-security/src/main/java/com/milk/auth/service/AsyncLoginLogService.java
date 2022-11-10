package com.milk.auth.service;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/8 23:15
 */

public interface AsyncLoginLogService {

    int recordLoginLog(String username,Integer status,String ipaddr ,String msg);
}
