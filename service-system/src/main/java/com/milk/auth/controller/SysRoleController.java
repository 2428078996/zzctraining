package com.milk.auth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.milk.auth.exce.CustomerException;
import com.milk.auth.service.SysRoleService;
import com.milk.common.R;
import com.milk.common.ResultEnum;
import com.milk.model.params.PageParam;
import com.milk.model.pojo.SysRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/2 19:12
 */
@Api(tags="角色模块")
@RestController
@RequestMapping("/admin/system/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation(value="获取所有角色列表")
    @GetMapping
    public R<List<SysRole>> list(){
        List<SysRole> sysRoleList = sysRoleService.list();
        return R.success(sysRoleList);
    }

    @ApiOperation(value="分页获取角色列表")
    @PostMapping("/list")
    public R page(@RequestBody PageParam pageParam){
        IPage<SysRole> pageInfo = sysRoleService.pageList(pageParam);
        return R.success(pageInfo);
    }

    @ApiOperation(value="根据id获取角色")
    @GetMapping("/{id}")
    public R<SysRole> findRoleById(@PathVariable("id") Long id){
        SysRole sysRole = sysRoleService.getById(id);
        return R.success(sysRole);
    }



    @ApiOperation(value="根据id删除角色")
    @DeleteMapping("/del/{id}")
    public R<SysRole> delByRoleId(@PathVariable("id") Long id){
        sysRoleService.removeById(id);
        return R.success("删除成功！");

    }

    @ApiOperation(value="根据id批量删除角色")
    @DeleteMapping("/del")
    public R<SysRole> bathDelRole(@RequestBody List<Long> ids){
        sysRoleService.removeByIds(ids);
        return R.success("批量删除成功！");
    }

    @PostMapping("/save")
    @ApiOperation(value="添加角色信息")
    public R save(@RequestBody SysRole sysRole){
        sysRoleService.save(sysRole);
        return R.success("添加成功！");
    }


    @PutMapping("/update")
    @ApiOperation(value="修改角色信息")
    public R update(@RequestBody SysRole sysRole){
        sysRoleService.save(sysRole);
        return R.success("修改成功！");
    }
}
