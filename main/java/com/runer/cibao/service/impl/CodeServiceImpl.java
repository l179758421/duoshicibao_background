package com.runer.cibao.service.impl;

import com.alibaba.fastjson.JSON;
import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.IRedisService;
import com.runer.cibao.domain.VerifyCode;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.CodeService;
import org.springframework.stereotype.Service;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/1
 **/
@Service
public class CodeServiceImpl  extends IRedisService implements CodeService {

    @Override
    protected String getRedisKey() {
        return VerifyCode.class.getSimpleName();
    }

    @Override
    public ApiResult saveOnePhoneCode(String phone, String code, Integer type,long time) {
        VerifyCode codeBean =new VerifyCode();
        codeBean.setCode(code);
        codeBean.setPhone(phone);
        codeBean.setType(type);
        codeBean.setTime(System.currentTimeMillis()/1000);
        put(phone+":"+type,JSON.toJSONString(codeBean), Config.codeOutTime);
        System.err.println(code);
        return new ApiResult(ResultMsg.SUCCESS,codeBean);
    }
    @Override
    public ApiResult getOnePhoneCode(String phone, Integer type) {

        Object code = JSON.parseObject((String) get(phone+":"+type), VerifyCode.class);
        if (code!=null){
           return  new ApiResult(ResultMsg.SUCCESS,code) ;
       }else{
           return  new ApiResult(ResultMsg.NOT_FOUND,null) ;
       }

    }

    @Override
    public ApiResult getALlCode() {
        String result = JSON.toJSON(getAll()).toString();
        return new ApiResult(ResultMsg.SUCCESS,result);
    }
}
