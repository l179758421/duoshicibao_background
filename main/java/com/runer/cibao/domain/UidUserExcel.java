package com.runer.cibao.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.util.Date;

public class UidUserExcel  extends BaseRowModel {

    @ExcelProperty(value = "UID",index = 0)
    private String UID ;

    @ExcelProperty(value="密码",index =1)
    private String passWord;

    @ExcelProperty(value = "使用学校",index = 2)
    private String school ;


    @ExcelProperty(value = "生成时间",index =3)
    private Date createTime;

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
