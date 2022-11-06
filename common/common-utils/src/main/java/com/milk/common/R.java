package com.milk.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/2 20:01
 */
@Data
public class R<T> {

    private Integer code;
    private boolean success;
    private String msg;
    private T data;
    private Map<String,Object> map=new HashMap<>();

    public static<T> R<T> success(){
        R<T> r = new R<T>();
        r.setSuccess(true);
        r.setCode(ResultEnum.SUCCESS.getCode());
        r.setMsg(ResultEnum.SUCCESS.getMsg());
        return r;

    }

    public static<T> R<T> success(T data){
        R<T> r = R.success();
        r.setData(data);
        return r;
    }

    public static<T> R<T> success(String msg){
        R<T> r = R.success();
        r.setMsg(msg);
        return r;
    }

    public static<T> R<T> fail(){
        R<T> r = new R<T>();
        r.setSuccess(false);
        r.setCode(ResultEnum.FAIL.getCode());
        r.setMsg(ResultEnum.FAIL.getMsg());
        return r;

    }

    public static<T> R<T> fail(String msg){
        R<T> r = R.fail();
        r.setMsg(msg);
        return r;
    }

    public static<T> R<T> fail(Integer code,String msg){
        R<T> r = R.fail();
        r.setMsg(msg);
        r.setCode(code);
        return r;
    }

    public static<T> R<T> fail(ResultEnum resultEnum){
        R<T> r = R.fail();
        r.setCode(resultEnum.getCode());
        r.setMsg(resultEnum.getMsg());
        return r;
    }

    public R add(String key,Object value){
        this.map.put(key,value);
        return this;
    }
}
