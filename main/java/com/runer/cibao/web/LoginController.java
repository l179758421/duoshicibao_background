package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.User;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.AdminService;
import com.runer.cibao.service.UserLoginService;
import com.runer.cibao.util.CookieUtil;
import com.runer.cibao.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author sww
 * @Date 2019/9/26
 *
 * 后台用户登录相关
 *
 **/
@RestController
@RequestMapping(value = "login")
public class LoginController {

    @Autowired
    UserLoginService userLoginService ;


    @Autowired
    AdminService adminService ;

    @Autowired
    JwtUtil jwtUtil;


    @RequestMapping(value = "userLogin")
    public ApiResult login(String loginName , String password , Integer isSavePass,  HttpServletResponse response){
        ApiResult apiResult = userLoginService.checkLoginUser(loginName, password);
        if(apiResult.isSuccess()){
            User user=(User) apiResult.getData();
            String jwt = jwtUtil.createJWT(String.valueOf(user.getId()), loginName);
            apiResult.setData(jwt);
            return apiResult;
        }
        return  new ApiResult(ResultMsg.PASSWORID_IS_ILLEGEAL,null);
    }

}
