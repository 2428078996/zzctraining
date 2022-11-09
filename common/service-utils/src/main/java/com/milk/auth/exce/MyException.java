package com.milk.auth.exce;

import com.milk.common.R;
import com.milk.common.ResultEnum;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/2 21:34
 */
@ControllerAdvice
@Slf4j
public class MyException {



    /*
     * sql字段值唯一异常
     * */
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public R exceptionHandler(DuplicateKeyException e){

        log.info(e.getMessage());

        if (e.getMessage().contains("Duplicate entry")){
            String[] s = e.getMessage().split(" ");
            String msg=s[2]+"已存在！";
            return R.fail(msg);
        }

        return R.fail("未知错误！");
    }


    /*
    * 系统异常
    * */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R globExc(Exception e){
        e.printStackTrace();
        String name = e.getClass().getName();

        log.info("{}",name);
        return R.fail(ResultEnum.SYS_ERROR);
    }


    /*
    * 业务异常
    * */
    @ExceptionHandler(CustomerException.class)
    @ResponseBody
    public R error(CustomerException e){

        return R.fail(e.getCode(),e.getMsg());
    }

    /*
    * 权限异常
    * */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public R error(AccessDeniedException e){
        return R.fail(ResultEnum.PERMISSION);
    }
}
