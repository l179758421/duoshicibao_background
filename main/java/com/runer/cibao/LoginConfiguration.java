package com.runer.cibao;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Author: sww
 * Date: 2019/9/26
 * Time: 下午2:58
 * Description:登录拦截器配置
 */

@Configuration
public class LoginConfiguration implements WebMvcConfigurer {
    public LoginConfiguration() {
    }

    public void addInterceptors(InterceptorRegistry registry) {
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        InterceptorRegistration loginRegistry = registry.addInterceptor(loginInterceptor);
        loginRegistry.addPathPatterns(new String[]{"/**"});
        loginRegistry.excludePathPatterns(new String[]{"/"});
        loginRegistry.excludePathPatterns(new String[]{"/api/appUser/*"});
        loginRegistry.excludePathPatterns(new String[]{"/login/*"});
        loginRegistry.excludePathPatterns(new String[]{"/v2/*"});
        loginRegistry.excludePathPatterns(new String[]{"/file/*","/app/*",
                "/audio/**/*","/audioZip/**/*","/ciBaoAdmin/*",
                "/json/*", "/zip/*","/eng/*"});
    }
}
