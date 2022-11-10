package com.milk.auth.service.impl;

import com.milk.auth.mapper.SysOperLogMapper;
import com.milk.auth.service.AsyncOperLogService;
import com.milk.model.pojo.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/9 11:34
 */
@Service
public class AsyncOperLogServiceImpl implements AsyncOperLogService {

    @Autowired
    private SysOperLogMapper sysOperLogMapper;

    @Override
    public boolean saveOperLog(SysOperLog sysOperLog) {
        sysOperLogMapper.insert(sysOperLog);
        return true;
    }
}
