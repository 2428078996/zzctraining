package com.milk.model.params;

import lombok.Data;

import java.util.List;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/5 12:39
 */
@Data
public class RoleMenuParam {

    private String roleId;

    private List<String> menuIdList;
}
