package com.milk.model.params;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/3 22:39
 */
@Data
public class UserPageParam {


    private Integer page;

    private Integer pageSize;

    private String keyword;

    private String phone;

    private String createTimeBegin;

    private String createTimeEnd;

}

