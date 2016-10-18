package com.yliyun.util;/**
 * Created by Administrator on 2016/10/15.
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/15 - 15:14
 * 配置rest 请求 ，支持http，文件下载；
 */

@Configuration
public class RestConfig {
    
    @Value("app.download.host")
    private String AppHost ;

//    public String getAppHost() {
//        return AppHost;
//    }
//
//    public void setAppHost(String appHost) {
//        AppHost = appHost;
//    }

    @Bean
    public RestTemplate restTemplate(List<HttpMessageConverter<?>> messageConverters){

        return new RestTemplate(messageConverters);
    }


    @Bean
    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
        return new ByteArrayHttpMessageConverter();
    }

}
