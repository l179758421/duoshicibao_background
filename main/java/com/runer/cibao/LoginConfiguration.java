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
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        InterceptorRegistration loginRegistry = registry.addInterceptor(loginInterceptor);

        // 拦截路径
        loginRegistry.addPathPatterns("/**");

        // 排除路径
        loginRegistry.excludePathPatterns("/");
        loginRegistry.excludePathPatterns("/api/appUser/*");
        loginRegistry.excludePathPatterns("/login/*");
         loginRegistry.excludePathPatterns("/v2/*");
   //     loginRegistry.excludePathPatterns("/webjars/*");
      //  loginRegistry.excludePathPatterns("/webjars/springfox-swagger-ui/*");
     //   loginRegistry.excludePathPatterns("/swagger-resources/*");
        // 排除资源请求
//        loginRegistry.excludePathPatterns("/css/login/*.css");
//        loginRegistry.excludePathPatterns("/js/login/**/*.js");
//        loginRegistry.excludePathPatterns("/image/login/*.png");
    }
}