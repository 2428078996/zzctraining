package com.milk.model.vo;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/6 19:15
 */

import com.milk.model.pojo.SysRole;
import lombok.Data;

import java.util.List;

@Data
public class UserInfoVo {

    private String name;

    private String avatar;

    private List<SysRole> roles;

    private List<RouterVo> routers;

    private List<String> buttons;
}
