package com.milk.auth.controller;

import com.milk.auth.service.SysDeptService;
import com.milk.common.R;
import com.milk.model.pojo.SysDept;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/8 21:37
 */

@Api(tags = "部门模块")
@RestController
@Slf4j
@RequestMapping("/admin/system/dept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @PreAuthorize("hasAnyAuthority('bnt.sysDept.list')")
    @ApiOperation(value = "获取部门")
    @GetMapping("/list")
    public R findNodes() {
        List<SysDept> list = sysDeptService.treeList();
        return R.success(list);
    }


    @PreAuthorize("hasAnyAuthority('bnt.sysDept.add')")
    @ApiOperation(value = "新增部门")
    @PostMapping("/save")
    public R save(@RequestBody SysDept permission) {
        sysDeptService.save(permission);
        return R.success("添加成功");
    }

    @PreAuthorize("hasAnyAuthority('bnt.sysDept.update')")
    @ApiOperation(value = "修改部门信息")
    @PutMapping("/update")
    public R updateById(@RequestBody SysDept permission) {
        sysDeptService.updateById(permission);
        return R.success("修改成功");
    }


    @PreAuthorize("hasAnyAuthority('bnt.sysDept.remove')")
    @ApiOperation(value = "删除部门")
    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable Long id) {
        sysDeptService.removeMenu(id);
        return R.success();
    }

    @PreAuthorize("hasAnyAuthority('bnt.sysDept.status')")
    @ApiOperation(value = "修改部门状态")
    @PostMapping("/{id}/{status}")
    public R changeStatus(@PathVariable Long id ,@PathVariable Integer status) {
        sysDeptService.changeStatus(id, status);
        return R.success("状态修改成功！");
    }


}
