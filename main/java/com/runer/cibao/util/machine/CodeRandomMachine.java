package com.runer.cibao.util.machine;

import com.runer.cibao.domain.repository.RedeemCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/7
 验证码随机生成器；
 **/
@Component
public class CodeRandomMachine {


    @Value("${code-length}")
    private int codeLength ;

    @Autowired
    private RedeemCodeRepository redeemCodeRepository ;


    public String productCode(){
        String randomcode = "";
        for(int i=0;i<codeLength;i++)
        {
            //52个字母与6个大小写字母间的符号；范围为91~96
            int value = (int)(Math.random()*58+65);
            while(value>=91 && value<=96)
                value = (int)(Math.random()*58+65);
            randomcode = randomcode + (char)value;
        }
        //防止重复的码
        if (redeemCodeRepository.findRedeemCodeByCodeNum(randomcode)!=null){
            return  productCode() ;
        }
        return  randomcode ;
    }

    public String productCode11(){
        String randomcode = "";
        for(int i=0;i<11;i++)
        {
            //52个字母与6个大小写字母间的符号；范围为91~96
            int value = (int)(Math.random()*58+65);
            while(value>=91 && value<=96)
                value = (int)(Math.random()*58+65);
            randomcode = randomcode + (char)value;
        }
        //防止重复的码
        if (redeemCodeRepository.findRedeemCodeByCodeNum(randomcode)!=null){
            return  productCode() ;
        }
        return  randomcode ;
    }







}
