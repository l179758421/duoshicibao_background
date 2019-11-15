package com.runer.cibao.domain;


import java.io.Serializable;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/1
 **/
public class VerifyCode implements Serializable {

    String phone ;
    int type ;
    String code ;
    long time ;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
