package com.runer.cibao.domain;

public class SaleBean {
    int studentCount;
    int activeCount;
    int withinThirtyDays;//30天内激活数
    int notUseCount;//未激活
    int outTimeCount;//过期

    public int getNotUseCount() {
        return notUseCount;
    }

    public void setNotUseCount(int notUseCount) {
        this.notUseCount = notUseCount;
    }

    public int getOutTimeCount() {
        return outTimeCount;
    }

    public void setOutTimeCount(int outTimeCount) {
        this.outTimeCount = outTimeCount;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public int getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(int activeCount) {
        this.activeCount = activeCount;
    }

    public int getWithinThirtyDays() {
        return withinThirtyDays;
    }

    public void setWithinThirtyDays(int withinThirtyDays) {
        this.withinThirtyDays = withinThirtyDays;
    }
}
