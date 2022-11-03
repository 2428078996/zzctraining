package com.milk.auth.exce;

import com.milk.common.R;
import com.milk.common.ResultEnum;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/2 21:34
 */
@ControllerAdvice
@Slf4j
public class MyException {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R globExc(Exception e){
        e.printStackTrace();
        log.info("nimade{}","wodefff");
        return R.fail(ResultEnum.SYS_ERROR);
    }

    @ExceptionHandler(CustomerException.class)
    @ResponseBody
    public R error(CustomerException e){
        e.printStackTrace();
        return R.fail(e.getCode(),e.getMsg());
    }
}
