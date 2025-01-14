package com.milk.auth;

import com.milk.auth.exce.MyException;
import com.milk.common.VerifyImgUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/2 19:01
 */
@SpringBootApplication
@Import({MyException.class, VerifyImgUtil.class})
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class,args);
    }
}
