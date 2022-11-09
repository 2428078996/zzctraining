package com.milk.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/2 20:03
 */
@Getter
public enum ResultEnum {

    SUCCESS(200,"成功"),
    FAIL(201, "失败"),
    SERVICE_ERROR(2012, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    SYS_ERROR(999,"系统异常"),
    ILLEGAL_REQUEST(205, "非法请求"),
    REPEAT_SUBMIT(206, "重复提交"),
    ARGUMENT_VALID_ERROR(210, "参数校验异常"),

    LOGIN_AUTH(208, "未登陆或登录已过期"),
    PERMISSION(209, "没有权限"),
    ACCOUNT_ERROR(214, "账号不正确"),
    PASSWORD_ERROR(215, "密码不正确"),
    LOGIN_MOBILE_ERROR( 216, "账号不正确"),
    ACCOUNT_STOP( 217, "账号已停用"),
    NODE_ERROR( 218, "该节点下有子节点，不可以操作")
    ;

    private Integer code;

    private String msg;

    private ResultEnum(Integer code,String msg){
        this.code=code;
        this.msg=msg;

    }

}
