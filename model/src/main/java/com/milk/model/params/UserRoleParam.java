package com.milk.model.params;

import lombok.Data;

import java.util.List;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/4 16:13
 */
@Data
public class UserRoleParam {

    private String userId;

    private List<String> userRoleIds;
}
