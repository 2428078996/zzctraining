package com.milk.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.milk.model.pojo.SysDept;

import java.util.List;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/6 20:17
 */
public interface SysDeptService extends IService<SysDept> {
    List<SysDept> treeList();

    boolean removeMenu(Long id);

    boolean changeStatus(Long id, Integer status);
}
