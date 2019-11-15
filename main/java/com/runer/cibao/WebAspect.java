
package com.runer.cibao;

import cn.jiguang.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.runer.cibao.domain.Member;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Map;


/**
 * 用户请求数据跟踪；
 */

//注入切面；
@Aspect
@Component
public class WebAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 切入点
     */
    @Pointcut("execution(public * com.runer.cibao.web.*.*(..)) || execution(public * com.runer.cibao.api.*.*(..))")
    public void webLog() {

    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            // 记录下请求内容
            logger.info("URL : " + request.getRequestURL().toString());
            logger.info("HTTP_METHOD : " + request.getMethod());
            logger.info("IP : " + request.getRemoteAddr());
            logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            //打印一下参数
            Map<String, String[]> params = request.getParameterMap();
            if (params!=null) {
                params.forEach((s, strings) -> {
                    logger.info("ARGS : " + s + ":" + JSON.toJSON(strings));
                });
            }
        }
    }


    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        if ((ret instanceof Serializable)){
            // 处理完请求，返回内容
           // logger.info("RESPONSE : "+new ObjectMapper().writeValueAsString(ret));
        }
    }

}