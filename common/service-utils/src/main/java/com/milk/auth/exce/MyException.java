package com.milk.auth.exce;

import com.milk.common.R;
import com.milk.common.ResultEnum;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;
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
    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public R exceptionHandler(SQLException e){

        log.info(e.getMessage());

        if (e.getMessage().contains("Duplicate entry")){
            String[] s = e.getMessage().split(" ");
            String msg=s[2]+"已存在！";
            return R.fail(msg);
        }

        return R.fail("未知错误！");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R globExc(Exception e){
        e.printStackTrace();
        String name = e.getClass().getName();

        log.info("{}",name);
        return R.fail(ResultEnum.SYS_ERROR);
    }

    @ExceptionHandler(CustomerException.class)
    @ResponseBody
    public R error(CustomerException e){
        e.printStackTrace();
        return R.fail(e.getCode(),e.getMsg());
    }
}
