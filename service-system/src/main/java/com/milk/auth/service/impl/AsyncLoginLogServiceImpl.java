package com.milk.auth.service.impl;

import com.milk.auth.mapper.SysLoginLogMapper;
import com.milk.auth.service.AsyncLoginLogService;
import com.milk.model.pojo.SysLoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/8 23:22
 */
@Service
public class AsyncLoginLogServiceImpl implements AsyncLoginLogService {

    @Autowired
    private SysLoginLogMapper sysLoginLogMapper;

    @Override
    public boolean saveLogin(SysLoginLog sysLoginLog) {
        int count = sysLoginLogMapper.insert(sysLoginLog);

        if (count>0){
            return true;
        }
        return false;
    }
}
