package com.runer.cibao.base;


import com.runer.cibao.exception.ResultMsg;

import java.beans.Transient;
import java.io.Serializable;

/**
 * @Author szhua
 * 2018-04-16
 * 14:46
 * @Descriptionsbaby_photos== ApiResult
 **/
public class ApiResult implements Serializable {

    String msg;
    int msgCode;
    Object data;


    public static ApiResult ok(){
          return  new ApiResult(null,true) ;
    }

    public static ApiResult ok(Object data){
        return  new ApiResult(data,true) ;
    }



    public ApiResult() {
    }

    /**
     * 默认正确
     * @param data
     */
    public ApiResult(Object data , boolean success){
       if (success){
           this.msg = ResultMsg.SUCCESS.getMessage() ;
           this.msgCode = ResultMsg.SUCCESS.getMsgCode() ;
           this.data =data ;
       }else{
           this.msg = (String) data;
           this.msgCode = ResultMsg.OS_ERROR.getMsgCode() ;
           this.data =null ;
       }
    }

    public ApiResult(ResultMsg resultMsg, Object data){
        this.msg = resultMsg.getMessage() ;
        this.msgCode =resultMsg.getMsgCode() ;
        this.data =data ;
    }

    /**
     * 默认错误；
     * @param msg
     */
    public ApiResult(String msg ){
        this.msg =msg ;
        this.msgCode= ResultMsg.OS_ERROR.getMsgCode() ;
        this.data =null ;
    }

    public ApiResult(String msg, int msgCode, Object data) {
        this.msg = msg;
        this.msgCode = msgCode;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    @Transient
    public boolean isSuccess(){
        if (getMsgCode()!= ResultMsg.SUCCESS.getMsgCode()){
            return  false ;
        }
        return  true ;
    }

    @Transient
    public boolean isFailed(){
        if (getMsgCode()!= ResultMsg.SUCCESS.getMsgCode()){
            return  true ;
        }
        return false ;
    }




}
