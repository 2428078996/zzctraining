package com.milk.auth.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.milk.auth.exce.CustomerException;
import com.milk.auth.service.SysUserService;
import com.milk.common.R;
import com.milk.common.ResultEnum;
import com.milk.model.params.UserPageParam;
import com.milk.model.params.UserRoleParam;
import com.milk.model.pojo.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/3 22:43
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/system/user")
@CrossOrigin
@Slf4j
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "获取分页列表")
    @PostMapping("/list")
    public R page(@RequestBody UserPageParam userPageParam) {

        log.info("数据{}",userPageParam);

        IPage<SysUser> pageInfo = sysUserService.selectPage(userPageParam);

        return R.success(pageInfo);
    }

    @ApiOperation(value = "根据ID获取用户")
    @GetMapping("/{id}")
    public R get(@PathVariable("id") Long id) {
        SysUser user = sysUserService.getById(id);
        return R.success(user);
    }

    @ApiOperation(value = "修改用户状态")
    @PostMapping("/{id}/{status}")
    public R status(@PathVariable Long id,@PathVariable Integer status){


        sysUserService.updateStatus(id,status);

        return R.success("状态修改成功！");
    }

    @ApiOperation(value = "添加用户")
    @PostMapping("/save")
    public R save(@RequestBody SysUser user) {
        sysUserService.save(user);
        return R.success("添加成功！");
    }

    @ApiOperation(value = "更新用户")
    @PutMapping("/update")
    public R updateById(@RequestBody SysUser user) {
        sysUserService.updateById(user);
        return R.success("修改成功！");
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/remove")
    public R remove(@RequestBody List<Long> ids) {
        sysUserService.removeByIds(ids);
        return R.success("删除成功！");
    }

    @ApiOperation(value="根据用户id查询所拥有的的角色")
    @GetMapping("/toRole/{userId}")
    public R getRoleByUserId(@PathVariable Long userId){

        Map<String,Object> map=sysUserService.getRoleByUserId(userId);

        return R.success(map);
    }

    @ApiOperation(value="分配角色")
    @PostMapping("/doRole")
    public R getRoleByUserId(@RequestBody UserRoleParam userRoleParam){

        sysUserService.doAssign(userRoleParam);

        return R.success("分配成功！");
    }

}

