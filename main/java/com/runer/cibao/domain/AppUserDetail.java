package com.runer.cibao.domain;

public class AppUserDetail {
    //学员姓名
    String name;
    //学员班级
    String userClass;
    //最近登录时间
    String newSignTime;
    //累计在线时长
    String totalOnlineTime;
    //有效学习时长
    String volidLearnTime;
    //今日有效时长
    String todayVolidTime;
    //单词记忆总量
    String totalWords;
    //今日新学单词
    String todayLearnWord;
    //今日复习单词
    String todayReviewWord;
    //学习进度
    String learnStage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
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

    public String getVolidLearnTime() {
        return volidLearnTime;
    }

    public void setVolidLearnTime(String volidLearnTime) {
        this.volidLearnTime = volidLearnTime;
    }

    public String getTodayVolidTime() {
        return todayVolidTime;
    }

    public void setTodayVolidTime(String todayVolidTime) {
        this.todayVolidTime = todayVolidTime;
    }

    public String getTotalWords() {
        return totalWords;
    }

    public void setTotalWords(String totalWords) {
        this.totalWords = totalWords;
    }

    public String getTodayLearnWord() {
        return todayLearnWord;
    }

    public void setTodayLearnWord(String todayLearnWord) {
        this.todayLearnWord = todayLearnWord;
    }

    public String getTodayReviewWord() {
        return todayReviewWord;
    }

    public void setTodayReviewWord(String todayReviewWord) {
        this.todayReviewWord = todayReviewWord;
    }

    public String getLearnStage() {
        return learnStage;
    }

    public void setLearnStage(String learnStage) {
        this.learnStage = learnStage;
    }
}
