package com.runer.cibao.domain;

/**
 * @author k
 */
public class StudyReport {
    private String userSchool;
    private String userClass;
    private String name;
    private String newSignTime;
    private String totalOnlineTime;
    private String todayVolidTime;
    private Long totalDays;
    private String totalNewOnlineTime;
    private String totalNewVolidTime;
    private String learnBook;
    private String learnWords;
    private String totalWords;


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
