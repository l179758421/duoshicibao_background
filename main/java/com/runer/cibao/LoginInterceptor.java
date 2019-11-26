package com.runer.cibao;


import com.alibaba.fastjson.JSON;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Author: sww
 * Date: 2019/9/26
 * Time: 下午2:58
 * Description:登陆拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    public LoginInterceptor() {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        StringBuffer requestURL = request.getRequestURL();
        String token;
        if (!StringUtils.isEmpty(requestURL)) {
            token = String.valueOf(requestURL);
            if (token.contains("swagger")) {
                return true;
            }
        }

        try {
            token = request.getHeader("token");
            if (StringUtils.isEmpty(token)) {
                token = request.getParameter("token");
                if (StringUtils.isEmpty(token)) {
                    this.returnJson(response);
                    return false;
                }
            }

            Claims claims = (new JwtUtil()).parseJWT(token);
            if (claims == null) {
                this.returnJson(response);
                return false;
            } else if (StringUtils.isEmpty(claims.getId())) {
                this.returnJson(response);
                return false;
            } else {
                return true;
            }
        } catch (Exception var7) {
            this.returnJson(response);
            return false;
        }
    }

    private void returnJson(HttpServletResponse response) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        try {
            writer = response.getWriter();
            String s = JSON.toJSONString(new ApiResult(ResultMsg.COOKIE_IS_TIME_OUT, (Object)null));
            writer.print(s);
        } catch (IOException var7) {
            ;
        } finally {
            if (writer != null) {
                writer.close();
            }

        }

    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
