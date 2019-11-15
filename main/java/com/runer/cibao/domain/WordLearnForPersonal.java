package com.runer.cibao.domain;

import com.runer.cibao.base.BaseBean;

import javax.persistence.*;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/27
 **/
@Entity
@Table(name = "WordLearnForPersonal")
public class WordLearnForPersonal extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    /**
     * 用户的ID
     */
    Long appUserId ;
    /**
     * bookId；
     */
    Long bookId ;
    /**
     *当前学习的单元；
     */
    Long currentUnitId ;

    String allUnitIds ;

    String leftUnitIds ;

    /*已经学习的words*/
    String learnedWords;
    /**
     * 未学习的words
     */
    String leftWords ;

    Integer isPassed ;

    boolean  currentFinished;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

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

    public String getLeftUnitIds() {
        return leftUnitIds;
    }

    public void setLeftUnitIds(String leftUnitIds) {
        this.leftUnitIds = leftUnitIds;
    }

    public String getLearnedWords() {
        return learnedWords;
    }

    public void setLearnedWords(String learnedWords) {
        this.learnedWords = learnedWords;
    }

    public String getLeftWords() {
        return leftWords;
    }

    public void setLeftWords(String leftWords) {
        this.leftWords = leftWords;
    }

    public Integer getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(Integer isPassed) {
        this.isPassed = isPassed;
    }

    public boolean isCurrentFinished() {
        return currentFinished;
    }

    public void setCurrentFinished(boolean currentFinished) {
        this.currentFinished = currentFinished;
    }
}
