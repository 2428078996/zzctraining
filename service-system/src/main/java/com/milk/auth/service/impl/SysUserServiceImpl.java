package com.milk.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.milk.auth.exce.CustomerException;
import com.milk.auth.mapper.SysRoleMapper;
import com.milk.auth.mapper.SysUserMapper;
import com.milk.auth.mapper.SysUserRoleMapper;
import com.milk.auth.service.SysUserService;
import com.milk.common.ResultEnum;
import com.milk.model.params.UserPageParam;
import com.milk.model.params.UserRoleParam;
import com.milk.model.pojo.SysRole;
import com.milk.model.pojo.SysUser;
import com.milk.model.pojo.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/3 22:35
 */
@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;


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

    @Override
    public Map<String, Object> getRoleByUserId(Long userId) {

        if (userId==null){
            throw new CustomerException(ResultEnum.ARGUMENT_VALID_ERROR);
        }

        Map<String,Object> map=new HashMap<>();

//       获取所有角色
        QueryWrapper<SysRole> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().select(SysRole::getId,SysRole::getRoleName);
        List<SysRole> roleList = roleMapper.selectList(queryWrapper);
        map.put("allRole",roleList);


        QueryWrapper<SysUserRole> userRoleWrapper=new QueryWrapper<>();
        userRoleWrapper.lambda().eq(SysUserRole::getUserId,userId);
        List<SysUserRole> userRoleList = sysUserRoleMapper.selectList(userRoleWrapper);
        List<String> ids=new ArrayList<>();
        for (SysUserRole sysUserRole : userRoleList) {
            ids.add(sysUserRole.getRoleId());
        }

        map.put("userRoleIds",ids);


        return map;
    }

    @Override
    public boolean doAssign(UserRoleParam userRoleParam) {
        String userId = userRoleParam.getUserId();
//      删除原有权限
        QueryWrapper<SysUserRole> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserRole::getUserId,userId);
        sysUserRoleMapper.delete(queryWrapper);

        sysUserRoleMapper.batchInsert(userRoleParam);

        return false;
    }
}
