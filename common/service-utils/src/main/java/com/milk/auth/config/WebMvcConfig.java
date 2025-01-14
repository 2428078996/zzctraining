package com.milk.auth.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;

/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/3 19:54
 */

@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter(){
        log.info("添加消息转换器");
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setDateFormat("yyyy-MM-dd HH:mm");
        config.setCharset(Charset.forName("UTF-8"));
        config.setSerializerFeatures(
                //输出类名
//                SerializerFeature.WriteClassName,
                //输出map中value为null的数据
                SerializerFeature.WriteMapNullValue,
                //json格式化
                SerializerFeature.PrettyFormat,
                //输出空list为[]，而不是null
                SerializerFeature.WriteNullListAsEmpty,
                //输出空string为""，而不是null
                SerializerFeature.WriteNullStringAsEmpty
        );
        converter.setFastJsonConfig(config);
        return converter;
    }
    }
