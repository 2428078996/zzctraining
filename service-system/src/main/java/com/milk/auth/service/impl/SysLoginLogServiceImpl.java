package com.milk.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.milk.auth.mapper.SysLoginLogMapper;
import com.milk.auth.service.SysLoginLogService;
import com.milk.model.pojo.SysLoginLog;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/9 10:51
 */
@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService {
}
