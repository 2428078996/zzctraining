package com.milk.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.milk.auth.service.SysPostService;
import com.milk.common.R;
import com.milk.model.params.PostPageParam;
import com.milk.model.pojo.SysPost;
import com.milk.model.vo.SysPostVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/8 19:49
 */

@Api(tags = "岗位管理")
@RestController
@RequestMapping("/admin/system/post")
@Slf4j
public class SysPostController {

    @Autowired
    private SysPostService sysPostService;


    @ApiOperation(value = "获取分页列表")
    @PostMapping("/list")
    public R page(@RequestBody PostPageParam postPageParam) {

        log.info("数据{}",postPageParam);

        Page<SysPost> pageInfo = sysPostService.selectPage(postPageParam);

        return R.success(pageInfo);
    }

    @ApiOperation(value = "获取所有岗位")
    @PostMapping("/all")
    public R getAll() {

        LambdaQueryWrapper<SysPost> queryWrapper=new LambdaQueryWrapper<>();

        queryWrapper.select(SysPost::getName,SysPost::getId);

        List<SysPost> postList = sysPostService.list(queryWrapper);

        return R.success(postList);
    }

    @ApiOperation(value = "根据ID获取岗位")
    @GetMapping("/{id}")
    public R get(@PathVariable("id") Long id) {
        SysPostVo post = sysPostService.getPostById(id);
        return R.success(post);
    }

    @ApiOperation(value = "修改岗位状态")
    @PostMapping("/{id}/{status}")
    public R status(@PathVariable Long id,@PathVariable Integer status){


        sysPostService.updateStatus(id,status);

        return R.success("状态修改成功！");
    }

    @ApiOperation(value = "添加岗位")
    @PostMapping("/save")
    public R save(@RequestBody SysPost post) {
        sysPostService.save(post);
        return R.success("添加成功！");
    }

    @ApiOperation(value = "更新岗位信息")
    @PutMapping("/update")
    public R updateById(@RequestBody SysPost post) {
        sysPostService.updateById(post);
        return R.success("修改成功！");
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/del")
    public R remove(@RequestBody List<Long> ids) {
        sysPostService.removeByIds(ids);
        return R.success("删除成功！");
    }


}
