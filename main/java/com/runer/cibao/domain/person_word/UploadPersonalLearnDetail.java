package com.runer.cibao.domain.person_word;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/12/30
 **/
public class UploadPersonalLearnDetail {


    Long bookId; Long currentUnitId; String allUnitIds; String leftUnitIDs; boolean isFinished ;long userId ;
    //上一个已经学习完成的单元；
    long preUnitId ;
    //当前是否在学习状态；
    boolean currentLearning ;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getCurrentUnitId() {
        return currentUnitId;
    }

    public void setCurrentUnitId(Long currentUnitId) {
        this.currentUnitId = currentUnitId;
    }

    public String getAllUnitIds() {
        return allUnitIds;
    }

    public void setAllUnitIds(String allUnitIds) {
        this.allUnitIds = allUnitIds;
    }

    public String getLeftUnitIDs() {
        return leftUnitIDs;
    }

    public void setLeftUnitIDs(String leftUnitIDs) {
        this.leftUnitIDs = leftUnitIDs;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public long getPreUnitId() {
        return preUnitId;
    }

    public void setPreUnitId(long preUnitId) {
        this.preUnitId = preUnitId;
    }

    public boolean isCurrentLearning() {
        return currentLearning;
    }

    public void setCurrentLearning(boolean currentLearning) {
        this.currentLearning = currentLearning;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
