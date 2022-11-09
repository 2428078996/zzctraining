package com.milk.model.vo;

import lombok.Data;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/8 23:19
 */

@Data
public class LoginLogVo {

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status 状态
     * @param ipaddr ip
     * @param message 消息内容
     * @return
     */

    private String username;

    private Integer status;

    private String ipaddr;

    private String msg;
}
