package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/4
 *
 * 后台用户登录
 **/
public interface UserLoginService {

    /**
     * 检查登录的用户
     * @param userName
     * @param pass
     * @return
     */
    ApiResult checkLoginUser(String userName, String pass);

    /**
     * 用户登录
     * @param userName
     * @param pass
     * @return
     */
    ApiResult login(String userName, String pass);


    /**
     *初始化admin；
     */
    void inintAdmin();


    /**
     * 登录的时候是否记住密码；
     * @param username
     * @param password
     * @param flag
     * @return
     */
     ApiResult loginWithRemeberPass(String username, String password, Integer flag, HttpServletResponse response)  ;

     ApiResult getUserNameAndPass(HttpServletResponse response);


    /**
     * 获得当前的user
     * @return
     */
     User getCurrentUser(HttpServletRequest request);


    /**
     * 获得当前的memeber对象 ；
     * @return
     */
    ApiResult getCurrentMember();

    /**
     * 修改用户的密码
     * @param id
     * @param loginName
     * @param pass
     * @return
     */
    ApiResult changeUserPass(Long id, String loginName, String pass, String originPass);


    /**
     * 重置密码
     */
    ApiResult resetPassword(String loginName, String loginPass);



    /**
     * 用户是否存在
     */
    public User existUser(String userName);
}
