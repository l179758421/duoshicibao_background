package com.runer.cibao.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/7
 兑换码

 **/


public class RedeemCodeExcel  extends BaseRowModel {


    @ExcelProperty(value = "序号",index = 0)
    private Long id ;


    @ExcelProperty(value = "充值码",index = 1)
    private String codeNum ;


    @ExcelProperty(value = "充值码描述",index = 2)
    private String des ;


    @ExcelProperty(value = "充值码金额(金币)",index = 3)
    private Integer codeMoney ;


    @ExcelProperty(value = "状态",index =4)
    private String state;

    @ExcelProperty(value = "激活人",index = 5)
     private String user ;


    @ExcelProperty(value="激活学校",index = 6)
    private String school ;


    @ExcelProperty(value="有效期",index = 7)
    private String termOfvalidity ;


    @ExcelProperty(value = "创建时间",index = 8)
    private String createTime;

    @ExcelProperty(value = "激活时间",index = 9)
    private String activeTime;

    @ExcelProperty(value = "是否过期",index = 10)
     private String isOutTime ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(String codeNum) {
        this.codeNum = codeNum;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Integer getCodeMoney() {
        return codeMoney;
    }

    public void setCodeMoney(Integer codeMoney) {
        this.codeMoney = codeMoney;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getTermOfvalidity() {
        return termOfvalidity;
    }

    public void setTermOfvalidity(String termOfvalidity) {
        this.termOfvalidity = termOfvalidity;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    public String getIsOutTime() {
        return isOutTime;
    }

    public void setIsOutTime(String isOutTime) {
        this.isOutTime = isOutTime;
    }
}
