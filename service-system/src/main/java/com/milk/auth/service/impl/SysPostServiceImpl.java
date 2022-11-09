package com.milk.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.milk.auth.exce.CustomerException;
import com.milk.auth.mapper.SysDeptMapper;
import com.milk.auth.mapper.SysPostMapper;
import com.milk.auth.service.SysDeptService;
import com.milk.auth.service.SysPostService;
import com.milk.common.ResultEnum;
import com.milk.model.params.PostPageParam;
import com.milk.model.pojo.SysDept;
import com.milk.model.pojo.SysPost;
import com.milk.model.pojo.SysRole;
import com.milk.model.pojo.SysUser;
import com.milk.model.vo.SysPostVo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/6 20:18
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService {
    @Override
    public Page<SysPost> selectPage(PostPageParam postPageParam) {
        Page<SysPost> page = new Page<>(postPageParam.getPage(), postPageParam.getPageSize());

        LambdaQueryWrapper<SysPost> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(Strings.isNotEmpty(postPageParam.getName()),SysPost::getName,postPageParam.getName())
                .like(Strings.isNotEmpty(postPageParam.getPostCode()),SysPost::getName,postPageParam.getPostCode())
                .eq((postPageParam.getStatus()!=null),SysPost::getStatus,postPageParam.getStatus());

        Page<SysPost> pageInfo = this.page(page, queryWrapper);
        return pageInfo;
    }

    @Override
    public boolean updateStatus(Long id, Integer status) {

        if (id==null || status ==null){

            throw new CustomerException(ResultEnum.ARGUMENT_VALID_ERROR);
        }
        LambdaUpdateWrapper<SysPost> updateWrapper = new LambdaUpdateWrapper<>();

        updateWrapper.eq(SysPost::getId,id).eq(SysPost::getStatus,status);

        SysPost post = new SysPost();

        post.setStatus(status==1 ? 0 : 1);

        return this.update(post,updateWrapper);
    }

    @Override
    public SysPostVo getPostById(Long id) {
        SysPost post = this.getById(id);

        SysPostVo sysPostVo = new SysPostVo();

        BeanUtils.copyProperties(post,sysPostVo);

        return sysPostVo;
    }
}
