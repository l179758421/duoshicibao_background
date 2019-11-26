package com.runer.cibao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/31
 **/

@Configuration
public class UploadConfig extends WebMvcConfigurerAdapter {
    @Value("${web.upload-path}")
    private String abPath;
    @Value("${web.upload-basePath}")
    private String rePath;

    @Value("${web.upload-cibaoPath}")
    private String cibaoPath;
    @Value("${web.upload-cibao}")
    private String cibao;

    @Value("${web.upload-appPath}")
    private String appPath;
    @Value("${web.upload-app}")
    private String app;

    @Value("${web.upload-rePath}")
    private String headerPath;
    @Value("${web.upload-header}")
    private String header;

    @Value("${web.upload-engPath}")
    private String engPath;
    @Value("${web.upload-audioEng}")
    private String audioEng;

    @Value("${web.upload-usaPath}")
    private String usaPath;
    @Value("${web.upload-audioUsa}")
    private String audioUsa;

    @Value("${web.upload-audioPath}")
    private String audioPath;
    @Value("${web.upload-audio}")
    private String audio;

    @Value("${web.upload-zipPath}")
    private String zipPath;
    @Value("${web.upload-zip}")
    private String zip;

    @Value("${web.upload-audioZipPath}")
    private String audioZipPath;
    @Value("${web.upload-audioZip}")
    private String audioZip;

    @Value("${web.upload-audioZipPathUsa}")
    private String audioZipPathUsa;
    @Value("${web.upload-audioZipUsa}")
    private String audioZipUsa;

    @Value("${web.upload-jsonPath}")
    private String jsonPath;
    @Value("${web.upload-json}")
    private String json;

    public UploadConfig() {
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(62914560L);
        factory.setMaxRequestSize(62914560L);
        return factory.createMultipartConfig();
    }

    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
        resourceHandlerRegistry.addResourceHandler(new String[]{this.rePath + "**"}).addResourceLocations(new String[]{"file:" + this.abPath});
        resourceHandlerRegistry.addResourceHandler(new String[]{this.cibao + "**"}).addResourceLocations(new String[]{"file:" +"/"+ this.cibaoPath});
        resourceHandlerRegistry.addResourceHandler(new String[]{this.app + "**"}).addResourceLocations(new String[]{"file:" +"/"+ this.appPath});
        resourceHandlerRegistry.addResourceHandler(new String[]{this.header + "**"}).addResourceLocations(new String[]{"file:" +"/"+ this.headerPath});
        resourceHandlerRegistry.addResourceHandler(new String[]{this.audioEng + "**"}).addResourceLocations(new String[]{"file:" + "/"+this.engPath});
        resourceHandlerRegistry.addResourceHandler(new String[]{this.audioUsa + "**"}).addResourceLocations(new String[]{"file:" + "/"+this.usaPath});
        resourceHandlerRegistry.addResourceHandler(new String[]{this.zip + "**"}).addResourceLocations(new String[]{"file:" +"/"+ this.zipPath});
        resourceHandlerRegistry.addResourceHandler(new String[]{this.audioZip + "**"}).addResourceLocations(new String[]{"file:" +"/"+ this.audioZipPath});
        resourceHandlerRegistry.addResourceHandler(new String[]{this.audioZipUsa + "**"}).addResourceLocations(new String[]{"file:" +"/"+ this.audioZipPathUsa});
        resourceHandlerRegistry.addResourceHandler(new String[]{this.json + "**"}).addResourceLocations(new String[]{"file:" +"/"+ this.jsonPath});

    }
}
