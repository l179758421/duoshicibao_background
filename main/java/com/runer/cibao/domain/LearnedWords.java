package com.runer.cibao.domain;

import com.runer.cibao.base.BaseBean;

import javax.persistence.*;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/29
 **/
@Table(name = "learnedWord")
@Entity
public class LearnedWords extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    long bookId ;

    String learnedWords ;

    String leftWords ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getLearnedWords() {
        return learnedWords;
    }

    public void setLearnedWords(String learnedWords) {
        this.learnedWords = learnedWords;
    }

    public String getLeftWords() {
        return leftWords;
    }

    public void setLeftWords(String leftWords) {
        this.leftWords = leftWords;
    }
}
