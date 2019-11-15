package com.runer.cibao.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class TestBookScoreExcel {

    @Excel(name = "学校名称",orderNum = "0")
    String schoolName;

    @Excel(name = "班级名称",orderNum = "1")
    String className;

    @Excel(name = "学生姓名",orderNum = "2")
    String userName;

    @Excel(name = "学生id",orderNum = "3")
    Long userId;

    @Excel(name = "目前学习课本",orderNum = "4")
    String bookName;

    @Excel(name = "学前测试时长",orderNum = "5")
    String preLearnTime;

    @Excel(name = "学后测试时长",orderNum = "6")
    String aftLearnTime;

    @Excel(name = "学前测试成绩",orderNum = "7")
    Integer preLearnScore;

    @Excel(name = "学后测试成绩",orderNum = "8")
    Integer aftLearnScore;


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

    public String getPreLearnTime() {
        return preLearnTime;
    }

    public void setPreLearnTime(String preLearnTime) {
        this.preLearnTime = preLearnTime;
    }

    public String getAftLearnTime() {
        return aftLearnTime;
    }

    public void setAftLearnTime(String aftLearnTime) {
        this.aftLearnTime = aftLearnTime;
    }

    public Integer getPreLearnScore() {
        return preLearnScore;
    }

    public void setPreLearnScore(Integer preLearnScore) {
        this.preLearnScore = preLearnScore;
    }

    public Integer getAftLearnScore() {
        return aftLearnScore;
    }

    public void setAftLearnScore(Integer aftLearnScore) {
        this.aftLearnScore = aftLearnScore;
    }
}
