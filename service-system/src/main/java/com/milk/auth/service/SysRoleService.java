package com.milk.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.milk.model.params.PageParam;
import com.milk.model.pojo.SysRole;

import java.util.List;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/2 19:13
 */
public interface SysRoleService extends IService<SysRole> {
    IPage<SysRole> pageList(PageParam pageParam);

}
