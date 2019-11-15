package com.runer.cibao.code;

/**
 * @Author szhua
 * 2018-04-16
 * 16:54
 * @Descriptionsbaby_photos== CodeResult
 **/
public class CodeResult {
    /**
     * CodeResult codeResult1 =JSON.parse(sendCode.sendCodeToPhone(userPhone)) ;
     * {
     * "code": 200,
     * "msg": "88",
     * "obj": "1908"
     * }
     */

    String obj;
    Integer code;
    String msg;

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
