package com.runer.cibao.domain;

import java.util.List;

public class ListBean {
    List<Long> newWordNumList;
    List<Long> reviewWordNumList;

    public List<Long> getNewWordNumList() {
        return newWordNumList;
    }

    public void setNewWordNumList(List<Long> newWordNumList) {
        this.newWordNumList = newWordNumList;
    }

    public List<Long> getReviewWordNumList() {
        return reviewWordNumList;
    }

    public void setReviewWordNumList(List<Long> reviewWordNumList) {
        this.reviewWordNumList = reviewWordNumList;
    }
}
