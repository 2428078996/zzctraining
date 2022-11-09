package com.milk.model.params;

import lombok.Data;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/8 19:52
 */
@Data
public class PostPageParam {
    private Integer page;

    private Integer pageSize;

    private String name;

    private String postCode;

    private Integer status;



}
