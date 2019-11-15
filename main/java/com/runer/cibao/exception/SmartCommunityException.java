package com.runer.cibao.exception;

/**
 * @Author szhua
 * 2018-05-14
 * 14:32
 * @Descriptionssmartcommunity== SmartCommunityException
 **/
public class SmartCommunityException extends Exception {

    private ResultMsg resultMsg ;
    private int exceptionCode ;
    public int getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(int exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public ResultMsg getResultMsg() {
        if (resultMsg==null){
            return  ResultMsg.OS_ERROR ;
        }
        return resultMsg;
    }


    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public void setResultMsg(ResultMsg resultMsg) {
        this.resultMsg = resultMsg;
    }

    public SmartCommunityException(ResultMsg resultMsg) {
        super(resultMsg.getMessage());
        this.exceptionCode =resultMsg.getMsgCode() ;
        this.resultMsg =resultMsg ;
    }
}
