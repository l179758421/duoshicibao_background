package com.runer.cibao.base;


import com.runer.cibao.exception.ResultMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author szhua
 * 2018-04-26
 * 23:07
 * @Descriptionsbaby_photos== LayPageResult
 **/
public class LayPageResult<T> {

    /**
     * code: 0,
     * msg: "",
     * count: 1000,
     * data: [
     */

    int code;
    String msg;
    long count;
    List<T> data;

    public LayPageResult(ResultMsg resultMsg, List<T> data){
        this.msg = resultMsg.getMessage() ;
        this.code =resultMsg.getMsgCode() ;
        this.data =data ;
    }

    public LayPageResult(List<T> data){
        this.msg="success";
        this.code =0 ;
        if (data==null){
            data =new ArrayList<>() ;
        }
        this.count =data.size();
        this.data =data ;
    }

    public LayPageResult(String msg) {
       this.msg =msg ;
       this.code =1 ;
       this.data =new ArrayList<>() ;

    }


    public LayPageResult() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data == null ? new ArrayList<>() : data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
