package com.runer.cibao.domain.person_word;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/12/30
 **/
public class UnitStateBean {

    public static final int NEW_LEARN_WORD_SATE = 1;
    public static final int STRONG_LEARN_WORD_STATE = 2;
    public static final int CHECK_WORD_STATE = 3;



    long unitId;

    String news ;

    String leftIds;

    String knows ;

    String strongs ;

    //String caches;

    String allWords ;

    //阶段；
    int stage ;


    String wrongWords ;


    long userId ;





    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getLeftIds() {
        return leftIds;
    }

    public void setLeftIds(String leftIds) {
        this.leftIds = leftIds;
    }

    public String getKnows() {
        return knows;
    }

    public void setKnows(String knows) {
        this.knows = knows;
    }

    public String getStrongs() {
        return strongs;
    }

    public void setStrongs(String strongs) {
        this.strongs = strongs;
    }

    public String getAllWords() {
        return allWords;
    }

    public void setAllWords(String allWords) {
        this.allWords = allWords;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public String getWrongWords() {
        return wrongWords;
    }

    public void setWrongWords(String wrongWords) {
        this.wrongWords = wrongWords;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
