package com.runer.cibao.config;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/18
 **/
public enum  WordProgressState {
    /**
     * WordLearnState

     1.新学
     2.认知
     3.强化
     4.效果
     5.cancel;

     *      */
    newLearn("未学习-提取状态",0),
    konw("学习失败、复习失败",1),
    strong("认知成功",2),
    result("强化成功",3),
    cancel("效果检测",4);



    private String des ;
    private int stateCode ;

    WordProgressState(String des ,int stateCode){
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
