package com.milk.model.params;

import lombok.Data;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/2 20:54
 */
@Data
public class PageParam {

    private Integer page;

    private Integer pageSize;

    private String roleQuery;
}
