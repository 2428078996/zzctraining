package com.milk.model.params;

import lombok.Data;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/9 10:38
 */

@Data
public class LoginLogParam {

    private Integer page;

    private Integer pageSize;

    private String username;

    private String createTimeBegin;

    private String createTimeEnd;

}
