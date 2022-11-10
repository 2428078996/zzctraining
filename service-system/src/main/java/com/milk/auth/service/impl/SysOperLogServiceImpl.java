package com.milk.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.milk.auth.mapper.SysOperLogMapper;
import com.milk.auth.service.SysOperLogService;
import com.milk.model.pojo.SysOperLog;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/9 11:50
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements SysOperLogService {
}
