package com.runer.cibao;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/31
 **/
@Configuration
public class UploadConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(60L*1024L * 1024L);
        return factory.createMultipartConfig();
    }

}
