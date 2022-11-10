package com.milk.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.milk.auth.service.AsyncLoginLogService;
import com.milk.auth.service.SysLoginLogService;
import com.milk.common.R;
import com.milk.model.params.LoginLogParam;
import com.milk.model.pojo.SysLoginLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/9 10:31
 */

@RestController
@RequestMapping("/admin/system/loginLog")
@Slf4j
@Api(tags = "登录日志")
public class LoginLogController {

    @Resource
    private SysLoginLogService sysLoginLogService;

    @ApiOperation(value = "查询所有登录日志")
    @PostMapping("/list")
    public R findAll(@RequestBody LoginLogParam loginLogParam){

        Page<SysLoginLog> page =new Page<>(loginLogParam.getPage(),loginLogParam.getPageSize());

        LambdaQueryWrapper<SysLoginLog> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(Strings.isNotEmpty(loginLogParam.getUsername()),SysLoginLog::getUsername,loginLogParam.getUsername())
                .ge(Strings.isNotEmpty(loginLogParam.getCreateTimeBegin()),SysLoginLog::getCreateTime,loginLogParam.getCreateTimeBegin())
                .le(Strings.isNotEmpty(loginLogParam.getCreateTimeEnd()),SysLoginLog::getCreateTime,loginLogParam.getCreateTimeEnd());


        Page<SysLoginLog> pageInfo = sysLoginLogService.page(page, queryWrapper);

        return R.success(pageInfo);
    }

    @ApiOperation(value = "获取")
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        SysLoginLog sysLoginLog = sysLoginLogService.getById(id);
        return R.success(sysLoginLog);
    }


}
