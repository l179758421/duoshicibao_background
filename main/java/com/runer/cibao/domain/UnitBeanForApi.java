package com.runer.cibao.domain;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/9/4
 **/
public class UnitBeanForApi {



    /**
     *
     *  单元id
     * 是否认知完成
     * 是否通关
     * ／／今天是否测试
     * 当前的分数;
     *
     */
    //用户的ID；
    long userId;
    //单元的id
    long unitId ;
    //是否通关
    boolean isPassed ;
    //今天是否测试了
    boolean isTodayTest ;
    //现在的分数
    int currentScore ;
    //单元的名称
    String unitName ;
    //课本的id
    long bookId ;
    //是否学习完成
    boolean isLearnFinished;
    //个人的课本ID；
    long personalBookId;
    //个人的unitID；
    long personalUnitId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }

    public boolean isTodayTest() {
        return isTodayTest;
    }

    public void setTodayTest(boolean todayTest) {
        isTodayTest = todayTest;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public boolean isLearnFinished() {
        return isLearnFinished;
    }

    public void setLearnFinished(boolean learnFinished) {
        isLearnFinished = learnFinished;
    }

    public long getPersonalBookId() {
        return personalBookId;
    }

    public void setPersonalBookId(long personalBookId) {
        this.personalBookId = personalBookId;
    }

    public long getPersonalUnitId() {
        return personalUnitId;
    }

    public void setPersonalUnitId(long personalUnitId) {
        this.personalUnitId = personalUnitId;
    }
}
