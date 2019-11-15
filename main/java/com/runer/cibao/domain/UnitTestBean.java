package com.runer.cibao.domain;

import javax.persistence.Transient;

public class UnitTestBean {

    long unitId ;
    String unitName;
    String name;
    Long testTime;
    Integer count;
    Integer isfinished;
    int score ;
    String rightIds;
    String errorIds ;
    String date ;
    boolean isCurrentLearn;
    Long  totalTime;
    @Transient
    String state;


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long  getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long  totalTime) {
        this.totalTime = totalTime;
    }

    public boolean isCurrentLearn() {
        return isCurrentLearn;
    }

    public void setCurrentLearn(boolean currentLearn) {
        isCurrentLearn = currentLearn;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getRightIds() {
        return rightIds;
    }

    public void setRightIds(String rightIds) {
        this.rightIds = rightIds;
    }

    public String getErrorIds() {
        return errorIds;
    }

    public void setErrorIds(String errorIds) {
        this.errorIds = errorIds;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTestTime() {
        return testTime;
    }

    public void setTestTime(Long testTime) {
        this.testTime = testTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getIsfinished() {
        return isfinished;
    }

    public void setIsfinished(Integer isfinished) {
        this.isfinished = isfinished;
    }
}
