package com.runer.cibao.domain;

public class StudyCountBean {
    String volidTime;//有效时长
    long wordNew;//新学单词
    long wordReview;//复习单词
    String learnBookWords;//学习进度(已学单词/总单词)


    public String getLearnBookWords() {
        return learnBookWords;
    }

    public void setLearnBookWords(String learnBookWords) {
        this.learnBookWords = learnBookWords;
    }

    public String getVolidTime() {
        return volidTime;
    }

    public void setVolidTime(String volidTime) {
        this.volidTime = volidTime;
    }

    public long getWordNew() {
        return wordNew;
    }

    public void setWordNew(long wordNew) {
        this.wordNew = wordNew;
    }

    public long getWordReview() {
        return wordReview;
    }

    public void setWordReview(long wordReview) {
        this.wordReview = wordReview;
    }
}
