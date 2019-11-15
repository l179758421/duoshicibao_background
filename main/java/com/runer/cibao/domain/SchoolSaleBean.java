package com.runer.cibao.domain;

public class SchoolSaleBean {
    String bookName;//书名
    String stage;//阶段
    String boughtTime;//激活时间
    String user;//激活人

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getBoughtTime() {
        return boughtTime;
    }

    public void setBoughtTime(String boughtTime) {
        this.boughtTime = boughtTime;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
