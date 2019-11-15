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

    /**
     * 在请求被处理之前调用
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String Origin = request.getHeader("Origin");
        System.out.println("LoginInterceptor="+Origin);

        return true;
//        StringBuffer requestURL = request.getRequestURL();
//        if(!StringUtils.isEmpty(requestURL)){
//            String  url=String.valueOf(requestURL);
//            if(url.contains("swagger")){
//                return  true;
//            }
//        }
//        try{
//           String token = request.getHeader("token");
//           if(StringUtils.isEmpty(token)){
//                token = request.getParameter("token");
//               if(StringUtils.isEmpty(token)){
//                   returnJson(response);
//                   return false;
//               }
//           }
//           Claims claims = new JwtUtil().parseJWT(token);
//           if(claims==null){
//               returnJson(response);
//               return false;
//           }
//           if(StringUtils.isEmpty(claims.getId())){
//               returnJson(response);
//               return false;
//           }
//           return true;
//       }catch(Exception e){
//           returnJson(response);
//           return false;
//       }
    }

    private void returnJson(HttpServletResponse response){
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            String s = JSON.toJSONString(new ApiResult(ResultMsg.COOKIE_IS_TIME_OUT, null));
            writer.print(s);
        } catch (IOException e){
        } finally {
            if(writer != null){
                writer.close();
            }
        }
    }


    /**
     * 在请求被处理后，视图渲染之前调用
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在整个请求结束后调用
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}