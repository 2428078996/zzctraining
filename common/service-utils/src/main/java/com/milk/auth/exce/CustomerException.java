package com.milk.auth.exce;

import com.milk.common.ResultEnum;
import lombok.Data;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/2 21:40
 */
@Data
public class CustomerException extends RuntimeException {

    private Integer code;

    private String msg;

    private CustomerException(){

    }

    public CustomerException(Integer code, String msg){
        super(msg);
        this.code=code;
        this.msg=msg;
    }

    public CustomerException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code=resultEnum.getCode();
        this.msg=resultEnum.getMsg();
    }
}
