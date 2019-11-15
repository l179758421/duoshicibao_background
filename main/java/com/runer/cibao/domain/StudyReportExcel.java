package com.runer.cibao.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class StudyReportExcel {

    //学员学校
    @Excel(name = "学员学校",orderNum = "0",isWrap = true)
    String userSchool;

    //学员班级
    @Excel(name = "学员班级",orderNum = "1" ,isWrap = true)
    String userClass;

    //学员姓名
    @Excel(name = "学员姓名",orderNum = "2")
    String name;

    //最近登录时间
    @Excel(name = "最近登录时间",orderNum = "3",isWrap = true)
    String newSignTime;

    //累计在线时长
    @Excel(name = "累计在线时长",orderNum = "4")
    String totalOnlineTime;

    //今日有效时长
    @Excel(name = "有效学习时长",orderNum = "5")
    String todayVolidTime;

    //达标天数
    @Excel(name = "达标天数",orderNum = "6")
    Long totalDays;

    //最近一次学习时长
    @Excel(name = "最近一次在线时长",orderNum = "7")
    String totalNewOnlineTime;

    //最近一次有效时长
    @Excel(name = "最近一次有效时长",orderNum = "8")
    String totalNewVolidTime;

    //目前学习课本
    @Excel(name = "目前学习课本",orderNum = "9")
    String learnBook;

    //目前已学单词数
    @Excel(name = "目前已学单词数",orderNum = "10")
    String learnWords;

    //累计学习单词数
    @Excel(name = "累计学习单词数",orderNum = "11")
    String totalWords;

    public String getUserSchool() {
        return userSchool;
    }

    public void setUserSchool(String userSchool) {
        this.userSchool = userSchool;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewSignTime() {
        return newSignTime;
    }

    public void setNewSignTime(String newSignTime) {
        this.newSignTime = newSignTime;
    }

    public String getTotalOnlineTime() {
        return totalOnlineTime;
    }

    public void setTotalOnlineTime(String totalOnlineTime) {
        this.totalOnlineTime = totalOnlineTime;
    }

    public String getTodayVolidTime() {
        return todayVolidTime;
    }

    public void setTodayVolidTime(String todayVolidTime) {
        this.todayVolidTime = todayVolidTime;
    }

    public Long getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Long totalDays) {
        this.totalDays = totalDays;
    }

    public String getTotalNewOnlineTime() {
        return totalNewOnlineTime;
    }

    public void setTotalNewOnlineTime(String totalNewOnlineTime) {
        this.totalNewOnlineTime = totalNewOnlineTime;
    }

    public String getTotalNewVolidTime() {
        return totalNewVolidTime;
    }

    public void setTotalNewVolidTime(String totalNewVolidTime) {
        this.totalNewVolidTime = totalNewVolidTime;
    }

    public String getLearnBook() {
        return learnBook;
    }

    public void setLearnBook(String learnBook) {
        this.learnBook = learnBook;
    }

    public String getLearnWords() {
        return learnWords;
    }

    public void setLearnWords(String learnWords) {
        this.learnWords = learnWords;
    }

    public String getTotalWords() {
        return totalWords;
    }

    public void setTotalWords(String totalWords) {
        this.totalWords = totalWords;
    }
}
