package com.runer.cibao.base;


import com.runer.cibao.exception.ResultMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author szhua
 * 2018-04-16
 * 14:52
 * @Descriptionsbaby_photos== PageApiResult
 **/

public class PageApiResult<T> {



    String msg;
    int msgCode;
    List<T> datas;
    int currentPage;
    long allCount;


    public PageApiResult(ResultMsg resultMsg, List<T> data){
        this.msg = resultMsg.getMessage() ;
        this.msgCode =resultMsg.getMsgCode() ;
        this.datas =data ;
    }

    public PageApiResult(String msg ) {
          this.msg =msg ;
          this.msgCode = ResultMsg.OS_ERROR.getMsgCode();
          currentPage =0 ;
          allCount =0 ;
          datas =new ArrayList<>() ;
    }


    public PageApiResult() {
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

    public List<T> getDatas() {
        return null == datas ? new ArrayList<>() : datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getAllCount() {
        return allCount;
    }

    public void setAllCount(long allCount) {
        this.allCount = allCount;
    }


}
