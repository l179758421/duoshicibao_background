package com.runer.cibao.service;

/**
 * @author k
 * @Date: Created in 16:05 2018/8/28
 * @Description:
 */
public interface SendSMSService {
    Boolean sendCode(String phone, String code, Integer type);
}
