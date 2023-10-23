package com.milk.auth.service;

import com.milk.model.pojo.SysLoginLog;

/**
 * @Author : beibeioo
 * @Date : 2023/10/23 9:32
 * @Description Is Description
 */
public interface AsyncLoginLogService {

    boolean saveLogin(SysLoginLog sysLoginLog);
}
