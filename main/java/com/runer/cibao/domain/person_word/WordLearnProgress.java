package com.runer.cibao.domain.person_word;

import com.runer.cibao.base.BaseBean;

import javax.persistence.*;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/17
 *
 *
 * 以每个单元的个体
 *
 **/
@Entity
@Table(name = "wordLearn_progress")
public class WordLearnProgress  extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    Long unitId ;

     Long userId ;

    String allWords;

    String allLeftWords;

    String tennewsWords ;

    String knowsWords ;

    String strongWords ;

    String resultCacheWords;

    String resultCacheHistory ;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getAllWords() {
        return allWords;
    }

    public void setAllWords(String allWords) {
        this.allWords = allWords;
    }

    public String getAllLeftWords() {
        return allLeftWords;
    }

    public void setAllLeftWords(String allLeftWords) {
        this.allLeftWords = allLeftWords;
    }

    public String getTennewsWords() {
        return tennewsWords;
    }

    public void setTennewsWords(String tennewsWords) {
        this.tennewsWords = tennewsWords;
    }

    public String getKnowsWords() {
        return knowsWords;
    }

    public void setKnowsWords(String knowsWords) {
        this.knowsWords = knowsWords;
    }

    public String getStrongWords() {
        return strongWords;
    }

    public void setStrongWords(String strongWords) {
        this.strongWords = strongWords;
    }

    public String getResultCacheWords() {
        return resultCacheWords;
    }

    public void setResultCacheWords(String resultCacheWords) {
        this.resultCacheWords = resultCacheWords;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getResultCacheHistory() {
        return resultCacheHistory;
    }

    public void setResultCacheHistory(String resultCacheHistory) {
        this.resultCacheHistory = resultCacheHistory;
    }
}
