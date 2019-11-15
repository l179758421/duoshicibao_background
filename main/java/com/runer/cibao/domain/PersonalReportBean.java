package com.runer.cibao.domain;

public class PersonalReportBean {
    String name;
    String className;
    String headMaster;
    String phone; //电话班主任
    String benginStartTime;//开始学习时间
    long cardDays;//打卡天数
    long unitScore;//单元测试成绩
    String learnBookWords;//学习进度
    Long wordNum;//累计单词数
    String learnTestBefore; //学前测试成绩
    String learnTestAfter; //学后测试成绩


    public String getLearnTestBefore() {
        return learnTestBefore;
    }

    public void setLearnTestBefore(String learnTestBefore) {
        this.learnTestBefore = learnTestBefore;
    }

    public String getLearnTestAfter() {
        return learnTestAfter;
    }

    public void setLearnTestAfter(String learnTestAfter) {
        this.learnTestAfter = learnTestAfter;
    }

    public Long getWordNum() {
        return wordNum;
    }

    public void setWordNum(Long wordNum) {
        this.wordNum = wordNum;
    }

    public String getLearnBookWords() {
        return learnBookWords;
    }

    public void setLearnBookWords(String learnBookWords) {
        this.learnBookWords = learnBookWords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getHeadMaster() {
        return headMaster;
    }

    public void setHeadMaster(String headMaster) {
        this.headMaster = headMaster;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBenginStartTime() {
        return benginStartTime;
    }

    public void setBenginStartTime(String benginStartTime) {
        this.benginStartTime = benginStartTime;
    }

    public long getCardDays() {
        return cardDays;
    }

    public void setCardDays(long cardDays) {
        this.cardDays = cardDays;
    }

    public long getUnitScore() {
        return unitScore;
    }

    public void setUnitScore(long unitScore) {
        this.unitScore = unitScore;
    }
}
