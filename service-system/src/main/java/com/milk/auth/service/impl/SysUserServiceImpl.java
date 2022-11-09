package com.milk.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.milk.auth.exce.CustomerException;
import com.milk.auth.mapper.SysRoleMapper;
import com.milk.auth.mapper.SysUserMapper;
import com.milk.auth.mapper.SysUserRoleMapper;
import com.milk.auth.service.SysDeptService;
import com.milk.auth.service.SysMenuService;
import com.milk.auth.service.SysPostService;
import com.milk.auth.service.SysUserService;
import com.milk.common.MD5Utils;
import com.milk.common.RequestUtils;
import com.milk.common.ResultEnum;
import com.milk.common.TokenUtils;
import com.milk.model.params.LoginParam;
import com.milk.model.params.UserPageParam;
import com.milk.model.params.UserRoleParam;
import com.milk.model.pojo.*;
import com.milk.model.vo.RouterVo;
import com.milk.model.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysPostService sysPostService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public IPage<SysUser> selectPage(UserPageParam userPageParam) {
        if (userPageParam==null){
            throw new CustomerException(ResultEnum.ARGUMENT_VALID_ERROR);
        }

        Page<SysUser> page = new Page<>(userPageParam.getPage(), userPageParam.getPageSize());
        IPage<SysUser> pageInfo = sysUserMapper.selectPage(page, userPageParam);

        List<SysUser> records = pageInfo.getRecords();
        for (SysUser user : records) {

            Long deptId = user.getDeptId();
            if (deptId.longValue()==0){
                user.setDeptName("暂未分配部门");
            }else{
                SysDept dept = sysDeptService.getById(deptId);
                user.setDeptName(dept.getName());
            }

            Long postId = user.getPostId();
            if (postId.longValue()==0){
                user.setPostName("无岗位");
            }else{
                SysPost post = sysPostService.getById(postId);
                user.setPostName(post.getName());
            }

        }
        pageInfo.setRecords(records);
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
            ids.add(sysUserRole.getRoleId().toString());
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

    @Override
    public String login(LoginParam loginParam) {

        if (loginParam==null){
            throw new CustomerException(ResultEnum.ARGUMENT_VALID_ERROR);
        }

        SysUser user = this.getOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, loginParam.getUsername()));


        if(user==null){
            throw new CustomerException(ResultEnum.ACCOUNT_ERROR);
        }

        if (user.getStatus()==0){
            throw new CustomerException(ResultEnum.ACCOUNT_STOP);
        }
        String password = user.getPassword();

        if(!password.equals(MD5Utils.encrypt(loginParam.getPassword()))){
            throw new CustomerException(ResultEnum.PASSWORD_ERROR);
        }

        String token = TokenUtils.createToken(user.getUsername(),user.getId());

        return token;
    }

    @Override
    public UserInfoVo getUserInfo() {

        HttpServletRequest request = RequestUtils.getRequest();
        String token = request.getHeader("token");
        if (token == null){
            throw new CustomerException(ResultEnum.LOGIN_AUTH);
        }

        String username=TokenUtils.getUsername(token);

        SysUser user=this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername,username));

        if (user==null){
            throw new CustomerException(ResultEnum.ACCOUNT_ERROR);
        }

        Long userId=Long.valueOf(user.getId());

//        路由权限地址
        List<RouterVo> userMenuList = sysMenuService.findUserMenuList(userId);

//        按钮权限列表
        List<String> userPermsList = sysMenuService.findUserPermsList(userId);

        UserInfoVo userInfo = new UserInfoVo();

        userInfo.setName(user.getName());
        userInfo.setAvatar(user.getHeadUrl());
        userInfo.setButtons(userPermsList);
        userInfo.setRouters(userMenuList);

        return userInfo;
    }

    @Override
    public boolean logout() {

        HttpServletRequest request = RequestUtils.getRequest();
        String token = request.getHeader("token");
        String username = TokenUtils.getUsername(token);
        redisTemplate.delete(username);
        return true;
    }


}
