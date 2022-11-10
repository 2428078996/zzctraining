package com.milk.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.milk.auth.service.SysOperLogService;
import com.milk.common.R;
import com.milk.model.params.OperLogParam;
import com.milk.model.pojo.SysOperLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/9 12:10
 */

@RequestMapping("/admin/system/operLog")
@RestController
@Slf4j
@Api(tags = "操作日志")
public class SysOperLogController {

    @Autowired
    private SysOperLogService sysOperLogService;

    @ApiOperation(value = "获取分页列表")
    @PostMapping("/list")
    public R index(@RequestBody OperLogParam operLogParam) {
        Page<SysOperLog> page =new Page<>(operLogParam.getPage(),operLogParam.getPageSize());

        LambdaQueryWrapper<SysOperLog> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(Strings.isNotEmpty(operLogParam.getOpername()), SysOperLog::getOperName,operLogParam.getOpername())
                .like(Strings.isNotEmpty(operLogParam.getTitle()), SysOperLog::getTitle,operLogParam.getTitle())
                .ge(Strings.isNotEmpty(operLogParam.getCreateTimeBegin()),SysOperLog::getCreateTime,operLogParam.getCreateTimeBegin())
                .le(Strings.isNotEmpty(operLogParam.getCreateTimeEnd()),SysOperLog::getCreateTime,operLogParam.getCreateTimeEnd());


        Page<SysOperLog> pageInfo = sysOperLogService.page(page, queryWrapper);

        return R.success(pageInfo);
    }

    @ApiOperation(value = "获取")
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        SysOperLog sysOperLog = sysOperLogService.getById(id);
        return R.success(sysOperLog);
    }


}
