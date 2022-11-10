package com.milk.auth.service;

import com.milk.model.pojo.SysOperLog;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/9 11:33
 */
public interface AsyncOperLogService {
    boolean saveOperLog(SysOperLog sysOperLog);
}
