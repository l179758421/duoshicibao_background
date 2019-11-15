package com.runer.cibao.config;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/17
 **/
public enum  WordLearnState {

    /**
     * WordLearnState
     *  /**
     *      *
     *      *
     *      * 1。未学习，提取出来进行认知---
     *      *  *                      2。认知成功-》
     *      *  *                      3。强化成功-》    ---不再是生词--
     *      *  *                      4。效果检测-》 -> a,生词本  b,变为生词了
     *      *  *                      //复习；
     *      *  *                      5。  a:语音学习 b:听音辨意   c智能听写  d句子填空  e句式练习
     *      */
    learn("认知",0), //@1

    strongLearn("强化",1), //@2

    resultTests("效果检测",2),//@3

    audioRelearn("语音学习",3),//@4
    audioJudge("听音辨意",4),//@5

    autoListenWrite("智能听写",5),//@6
    sentenceInput("句子填空",6), //@7
    sentencePractice("句子练习",7),//@8


    ;




    private String des ;
    private int stateCode ;

    WordLearnState(String des ,int stateCode){
     this.des =des ;
     this.stateCode =stateCode ;
    }

    public int getStateCode() {
        return stateCode;
    }

    public String getDes() {
        return des;
    }
}
