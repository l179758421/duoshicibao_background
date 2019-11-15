package com.runer.cibao.config;

public enum LevelState {
    level1("1-1000分",1),
    level2("1001-5000分",2),
    level3("5001-10000分",3),
    level4("10000-20000分",4),
    level5("20001-50000分",5),
    level6("50001分",6);
    private String des ;
    private int stateCode ;

    LevelState(String des, int stateCode) {
        this.des = des;
        this.stateCode = stateCode;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }
}
