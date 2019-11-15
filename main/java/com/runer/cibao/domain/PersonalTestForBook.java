package com.runer.cibao.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.runer.cibao.Config;
import com.runer.cibao.base.BaseBean;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/25
 **/

@Entity
@Table(name = "PresonalTestForBook")
public class PersonalTestForBook extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id")
    PersonalLearnBook personalLearnBook ;

    Long totalTestTime ;

    Long totalWordsNum ;

    Long testTime ;

    Long testWordsNum ;

    Long rightWordsNum ;

    /**
     * 学前测试；
     */
    Integer isPreLearnTest  = Config.NOT_PRE_TEST;

    /**
     * 测试的所有单词的ids
     */
    @Column(length = 1000)
    String ids ;


    /**
     * 错误单词的ids
     */
    @Column(length = 1000)
    String errorIds ;

    /**
     * 学后成绩；
     */
    Integer score ;
    /**
     * 学前成绩
     */
    Integer preScore;

    /**
     * 通关
     */
    Integer isPassed ;



    public PersonalTestForBook() {

    }


    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    Date testDate ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPreScore() {
        return preScore;
    }

    public void setPreScore(Integer preScore) {
        this.preScore = preScore;
    }

    public Long getTotalTestTime() {
        return totalTestTime;
    }

    public void setTotalTestTime(Long totalTestTime) {
        this.totalTestTime = totalTestTime;
    }

    public Long getTotalWordsNum() {
        return totalWordsNum;
    }

    public void setTotalWordsNum(Long totalWordsNum) {
        this.totalWordsNum = totalWordsNum;
    }

    public Long getTestTime() {
        return testTime;
    }

    public void setTestTime(Long testTime) {
        this.testTime = testTime;
    }

    public Long getTestWordsNum() {
        return testWordsNum;
    }

    public void setTestWordsNum(Long testWordsNum) {
        this.testWordsNum = testWordsNum;
    }

    public Long getRightWordsNum() {
        return rightWordsNum;
    }

    public void setRightWordsNum(Long rightWordsNum) {
        this.rightWordsNum = rightWordsNum;
    }

    public Integer getIsPreLearnTest() {
        return isPreLearnTest;
    }

    public void setIsPreLearnTest(Integer isPreLearnTest) {
        this.isPreLearnTest = isPreLearnTest;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public String getErrorIds() {
        return errorIds;
    }

    public void setErrorIds(String errorIds) {
        this.errorIds = errorIds;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public PersonalLearnBook getPersonalLearnBook() {
        return personalLearnBook;
    }

    public void setPersonalLearnBook(PersonalLearnBook personalLearnBook) {
        this.personalLearnBook = personalLearnBook;
    }

    public Integer getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(Integer isPassed) {
        this.isPassed = isPassed;
    }




}
