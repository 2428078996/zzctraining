package com.milk.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.milk.model.params.PostPageParam;
import com.milk.model.pojo.SysPost;
import com.milk.model.vo.SysPostVo;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/6 20:17
 */
public interface SysPostService extends IService<SysPost> {

    Page<SysPost> selectPage(PostPageParam postPageParam);

    boolean updateStatus(Long id, Integer status);

    SysPostVo getPostById(Long id);

}
