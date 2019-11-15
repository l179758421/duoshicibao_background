package com.runer.cibao.service.impl;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.runer.cibao.Config;
import com.runer.cibao.service.SendSMSService;
import com.runer.cibao.util.SmsUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author k
 * @Date: Created in 15:34 2018/8/28
 * @Description:
 */
@Service
public class SendSMSServiceImpl implements SendSMSService {

    @Value("${aliyun.sms.product}")
    private String product;

    @Value("${aliyun.sms.domain}")
    private String domain ;

    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId ;

    @Value("${aliyun.sms.accessKeySecret}")
    private String accessKeySecret ;

    //@Value("${aliyun.sms.signName}")
    private String signName = "词宝";

    @Value("${aliyun.sms.templateCode.register}")
    private String registerTemplateCode;

    @Value("${aliyun.sms.templateCode.changePassword}")
    private String changePasswordTemplateCode;

    @Value("${aliyun.sms.templateCode.changeInfo}")
    private String changeInfoTemplateCode;

    @Override
    public Boolean sendCode(String phone, String code, Integer type){
        String templateCode;
        switch (type){
            case Config.REGISTER    :
                templateCode = registerTemplateCode;
                break;
            case Config.UPDATE_PASS_OR_FORGET :
                templateCode = changePasswordTemplateCode;
                break;
            case Config.BIND_PHONE :
                templateCode = changeInfoTemplateCode;
                break;
            default :
                templateCode = registerTemplateCode;
        }
        SendSmsResponse sendSmsResponse ;
        try {
            sendSmsResponse =  SmsUtil.sendSms(product,domain,accessKeyId,accessKeySecret, signName,templateCode,phone,code);
        } catch (ClientException e) {
            System.err.println(e.getMessage());
           return false ;
        }
        System.err.println(sendSmsResponse.getMessage());
        return sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK");
    }


}
