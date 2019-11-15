package com.runer.cibao.domain;

import com.runer.cibao.base.BaseBean;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ReviewTest")
public class ReviewTest extends BaseBean {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    Long userId;
    Long unitId;

    Long testUseTime ;

    Long testTime;//测试时间

    String rightIds;//正确单词
    String errorIds;//错误单词

    @Temporal(TemporalType.TIMESTAMP)
    private Date testDate;

    public Long getTestTime() {
        return testTime;
    }

    public void setTestTime(Long testTime) {
        this.testTime = testTime;
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

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getTestUseTime() {
        return testUseTime;
    }

    public void setTestUseTime(Long testUseTime) {
        this.testUseTime = testUseTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }
}
