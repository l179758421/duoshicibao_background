package com.runer.cibao.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class PersonalReportExcel {
    @Excel(name = "学生姓名",orderNum = "0")
    String name;

    @Excel(name = "班级名称",orderNum = "1")
    String className;//班级名称

    @Excel(name = "班主任",orderNum = "2")
    String headMaster;//班主任

    @Excel(name = "手机号",orderNum = "3")
    String phone;//手机

    @Excel(name = "开始学习时间",orderNum = "4")
    String beginStudyTime;//开始学习时间

    @Excel(name = "打卡天数",orderNum = "5")
    int punchCardDays;//打卡天数

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getHeadMaster() {
        return headMaster;
    }

    public void setHeadMaster(String headMaster) {
        this.headMaster = headMaster;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBeginStudyTime() {
        return beginStudyTime;
    }

    public void setBeginStudyTime(String beginStudyTime) {
        this.beginStudyTime = beginStudyTime;
    }

    public int getPunchCardDays() {
        return punchCardDays;
    }

    public void setPunchCardDays(int punchCardDays) {
        this.punchCardDays = punchCardDays;
    }
}
