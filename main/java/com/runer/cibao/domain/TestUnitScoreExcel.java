package com.runer.cibao.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class TestUnitScoreExcel {

    @Excel(name = "学校名称",orderNum ="0")
    String schoolName;

    @Excel(name = "班级名称",orderNum ="1")
    String className;

    @Excel(name = "学生姓名",orderNum ="2")
    String userName;

    @Excel(name = "学生id",orderNum ="3")
    Long userId;

    @Excel(name = "课本名称",orderNum ="4")
    String bookName;

    @Excel(name = "单元名称",orderNum ="5")
    String unitName;

    @Excel(name = "测试成绩",orderNum ="6")
    Integer testTscore;

    @Excel(name = "测试时间",orderNum ="7")
    String testTime;


    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getTestTscore() {
        return testTscore;
    }

    public void setTestTscore(Integer testTscore) {
        this.testTscore = testTscore;
    }

    public String getTestTime() {
        return testTime;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }
}
