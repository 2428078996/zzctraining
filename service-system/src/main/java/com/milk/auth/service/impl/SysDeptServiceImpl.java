package com.milk.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.milk.auth.exce.CustomerException;
import com.milk.auth.mapper.SysDeptMapper;
import com.milk.auth.service.SysDeptService;
import com.milk.common.ResultEnum;
import com.milk.common.TreeUtils;
import com.milk.model.pojo.SysDept;
import com.milk.model.pojo.SysMenu;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/6 20:18
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
    @Override
    public List<SysDept> treeList() {

        List<SysDept> deptList = this.list();

        List<SysDept> treeList= TreeUtils.buildDeptTree(deptList);

        return treeList;
    }

    @Override
    public boolean removeMenu(Long id) {
        int count= this.count(new LambdaQueryWrapper<SysDept>().eq(SysDept::getParentId, id).eq(SysDept::getStatus,1));
        if (count >0 ){
            throw new CustomerException(ResultEnum.NODE_ERROR);
        }
        this.removeById(id);
        return true;
    }

    @Override
    public boolean changeStatus(Long id, Integer status) {

        int count = this.count(new LambdaQueryWrapper<SysDept>().eq(SysDept::getParentId, id).eq(SysDept::getStatus,1));
        if (count >0 ){
            throw new CustomerException(ResultEnum.NODE_ERROR);
        }

        SysDept dept = new SysDept();
        dept.setId(id.toString());

       dept.setStatus(status==1 ? 0 : 1);

        this.updateById(dept);
        return true;
    }
}
