package com.milk.model.params;

import lombok.Data;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/6 11:09
 */

@Data
public class LoginParam {

    private String username;

    private String password;

    private String tempToken;

    private String verifyCode;
}
