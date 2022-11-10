package com.milk.model.params;

import lombok.Data;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/9 12:16
 */
@Data
public class OperLogParam {


    private Integer page;

    private Integer pageSize;

    private String opername;

    private String title;

    private String createTimeBegin;

    private String createTimeEnd;

}
