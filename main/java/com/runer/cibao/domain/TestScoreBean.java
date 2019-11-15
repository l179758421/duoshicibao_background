package com.runer.cibao.domain;

public class TestScoreBean {
    Long id;//学员id
    String name;
    String bookName;
    Long bookId;
    Long preLearnScore;
    Long aftLearnScore;
    Long testTime;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Long getPreLearnScore() {
        return preLearnScore;
    }

    public void setPreLearnScore(Long preLearnScore) {
        this.preLearnScore = preLearnScore;
    }

    public Long getAftLearnScore() {
        return aftLearnScore;
    }

    public void setAftLearnScore(Long aftLearnScore) {
        this.aftLearnScore = aftLearnScore;
    }

    public Long getTestTime() {
        return testTime;
    }

    public void setTestTime(Long testTime) {
        this.testTime = testTime;
    }
}
