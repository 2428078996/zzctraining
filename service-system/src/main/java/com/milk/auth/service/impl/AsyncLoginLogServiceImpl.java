package com.milk.auth.service.impl;

import com.milk.auth.mapper.SysLoginLogMapper;
import com.milk.auth.service.AsyncLoginLogService;
import com.milk.model.pojo.SysLoginLog;
import com.milk.model.vo.LoginLogVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/8 23:22
 */
@Service
public class AsyncLoginLogServiceImpl implements AsyncLoginLogService {

    @Resource
    private SysLoginLogMapper sysLoginLogMapper;

    @Override
    public int recordLoginLog(String username,Integer status,String ipaddr ,String msg) {

        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setStatus(status);
        sysLoginLog.setUsername(username);
        sysLoginLog.setMsg(msg);
        sysLoginLog.setIpaddr(ipaddr);

        return sysLoginLogMapper.insert(sysLoginLog);
    }
}
