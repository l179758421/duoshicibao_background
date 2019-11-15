package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/1


 验证码相关的service；

 **/
public interface CodeService {


    ApiResult saveOnePhoneCode(String phone, String code, Integer type, long time) ;
    ApiResult getOnePhoneCode(String phone, Integer type) ;

    ApiResult getALlCode();
}
