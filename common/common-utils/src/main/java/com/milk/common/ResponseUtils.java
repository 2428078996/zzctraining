package com.milk.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/8 11:18
 */
public class ResponseUtils {

    public static void out(HttpServletResponse response,R r){

        ObjectMapper mapper=new ObjectMapper();

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        try {
            mapper.writeValue(response.getWriter(),r);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
