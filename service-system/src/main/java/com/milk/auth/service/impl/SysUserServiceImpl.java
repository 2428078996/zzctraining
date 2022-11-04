package com.milk.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.milk.auth.exce.CustomerException;
import com.milk.auth.mapper.SysUserMapper;
import com.milk.auth.service.SysUserService;
import com.milk.common.ResultEnum;
import com.milk.model.params.UserPageParam;
import com.milk.model.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/3 22:35
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;


    @Override
    public IPage<SysUser> selectPage(UserPageParam userPageParam) {
        if (userPageParam==null){
            throw new CustomerException(ResultEnum.ARGUMENT_VALID_ERROR);
        }

        Page<SysUser> page = new Page<>(userPageParam.getPage(), userPageParam.getPageSize());
        IPage<SysUser> pageInfo = sysUserMapper.selectPage(page, userPageParam);

        return pageInfo;
    }

    @Override
    public boolean updateStatus(Long id,Integer status) {
        if (id==null || status ==null){
            throw new CustomerException(ResultEnum.ARGUMENT_VALID_ERROR);
        }
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();

        updateWrapper.eq(SysUser::getId,id).eq(SysUser::getStatus,status);

        SysUser user = new SysUser();

        user.setStatus(status==1 ? 0 : 1);

        return this.update(user,updateWrapper);
    }
}
