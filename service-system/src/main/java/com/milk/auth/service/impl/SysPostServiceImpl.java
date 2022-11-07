package com.milk.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.milk.auth.mapper.SysDeptMapper;
import com.milk.auth.mapper.SysPostMapper;
import com.milk.auth.service.SysDeptService;
import com.milk.auth.service.SysPostService;
import com.milk.model.pojo.SysDept;
import com.milk.model.pojo.SysPost;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/6 20:18
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService {
}