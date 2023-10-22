package com.milk.auth.exce;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.milk.common.R;
import com.milk.common.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description 全局异常拦截器
 * @Author @Milk
 * @Date 2022/11/2 21:34
 */
@RestControllerAdvice
@Slf4j
public class MyException {

    /*
     * sql字段值唯一异常
     * */
    @ExceptionHandler(DuplicateKeyException.class)
    
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
    public R error(CustomerException e){

        return R.fail(e.getCode(),e.getMsg());
    }
    // 拦截：未登录异常
    @ExceptionHandler(NotLoginException.class)
    public R handlerException(NotLoginException e) {

        // 打印堆栈，以供调试
        e.printStackTrace();
        System.out.println(e.getType());

        // 返回给前端
        return R.fail(e.getMessage());
    }

    // 拦截：缺少权限异常
    @ExceptionHandler(NotPermissionException.class)
    public R handlerException(NotPermissionException e) {
        e.printStackTrace();
        return R.fail("缺少权限：" + e.getPermission());
    }

    // 拦截：缺少角色异常
    @ExceptionHandler(NotRoleException.class)
    public R handlerException(NotRoleException e) {
        e.printStackTrace();
        return R.fail("缺少角色：" + e.getRole());
    }


}
