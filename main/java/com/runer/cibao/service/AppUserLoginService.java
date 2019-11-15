package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.AppUser;
import com.runer.cibao.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/12
 **/
public interface AppUserLoginService {

    ApiResult login(String phone, String pass);

    ApiResult sendCode(String phone, Integer type);

    ApiResult register(String phone, String pass);

    ApiResult forgetPass(String phone, String code, String pass);

    /**
     * 通过第三方进行登录
     *
     * @param openId
     * @param thirdNum
     * @param loginType
     * @param headerImgUrl
     * @param nickName
     * @return
     */
    ApiResult thirdLogin(String openId, String thirdNum, Integer loginType, String headerImgUrl, String nickName);

    /**
     * 账号绑定
     *
     * @param openId
     * @param loginType
     * @param thirdNum
     * @return
     */
    ApiResult bindThirdAccount(Long userId, String openId, Integer loginType, String thirdNum);


    ApiResult bindPhone(Long userId, String phone, String codeNum, String pass);

    /**
     * 只校验验证码
     *
     * @param phone
     * @param code
     * @return
     */
    ApiResult onlyVerifyCode(String phone, String code, Long type);

    /**
     * 设置密码
     *
     * @param
     * @param passWord
     * @return
     */
    ApiResult setPassWord(String passWord);


    /**
     * 解绑第三方的账号；
     *
     * @param appUserId
     * @param type
     * @return
     */
    ApiResult unBindThirdNum(Long appUserId, int type);

    /**
     * 登录
     */
    ApiResult checkLoginUser(String phone, String pass);


    AppUser getCurrentUser(HttpServletRequest request);
}
