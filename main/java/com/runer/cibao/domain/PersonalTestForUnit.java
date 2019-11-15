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
@Table(name = "PresonalTestForUnit")
public class PersonalTestForUnit extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    /**
     * 对应的个人单元
     */
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY )
    PersonalLearnUnit personalLearnUnit ;

    Long totalTestTime ;

    Long totalWordsNum ;

    Long testTime ;

    Long testWordsNum ;

    Long rightWordsNum ;

    /**
     * 正确的单词的ids
     */
    @Column(length = 1000)
    String ids ;

    /**
     * 错误单词的ids
     */
    String errorIds ;

    /**
     * 是否是学前测试
     */
    Integer isPreLearnTest = Config.NOT_PRE_TEST ;

    /**
     * 学后测试成绩
     */
    Integer score ;
    /**
     * 学前测试成绩
     */
    Integer preScore;

    Integer isPassed  = Config.NOT_PASSED;


    public Integer getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(Integer isPassed) {
        this.isPassed = isPassed;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    Date testDate ;


    public Integer getPreScore() {
        return preScore;
    }

    public void setPreScore(Integer preScore) {
        this.preScore = preScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public Integer getIsPreLearnTest() {
        return isPreLearnTest;
    }

    public void setIsPreLearnTest(Integer isPreLearnTest) {
        this.isPreLearnTest = isPreLearnTest;
    }


    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public PersonalLearnUnit getPersonalLearnUnit() {
        return personalLearnUnit;
    }

    public void setPersonalLearnUnit(PersonalLearnUnit personalLearnUnit) {
        this.personalLearnUnit = personalLearnUnit;
    }
}
