package com.runer.cibao.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/14
 **/
public class SchoolUidForExcel {

    @Excel(name = "学校", orderNum = "0" ,width = 40)
    private String schoolName;

    @Excel(name = "UID",orderNum = "1" ,width = 40)
    private String UID ;

    @Excel(name = "电话",orderNum = "2",width = 40)
    private String phone ;

    @Excel(name = "地址",orderNum = "3" ,width = 40)
    private String address ;

    @Excel(name = "生成时间",orderNum = "4",width = 40)
    private Date createTime;


    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
