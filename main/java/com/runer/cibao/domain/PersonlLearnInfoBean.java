package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.runer.cibao.base.BaseBean;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/9/5
 **/
@Entity
@Table(name = "personalLearnInfo")
public class PersonlLearnInfoBean extends BaseBean {

     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     Long id ;

     long onlineTime ;

     //总时间
     long time ;

     long learnTime ;

     //新学的数量
     long wordsNumNew ;

     //复习的数量
     long wordsNumReview ;

     long allWords ;


     @Transient
     int rank ;

     @Transient
     String name ;

     @Transient
     boolean index ;

     //todo;
     @Temporal(TemporalType.TIMESTAMP)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
     Date date ;


     @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "user_id")
    AppUser appUser ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getWordsNumNew() {
        return wordsNumNew;
    }

    public void setWordsNumNew(long wordsNumNew) {
        this.wordsNumNew = wordsNumNew;
    }

    public long getWordsNumReview() {
        return wordsNumReview;
    }

    public void setWordsNumReview(long wordsNumReview) {
        this.wordsNumReview = wordsNumReview;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }


    public long getAllWords() {
        return allWords;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAllWords(long allWords) {
        this.allWords = allWords;
    }

    public long getLearnTime() {
        return learnTime;
    }

    public void setLearnTime(long learnTime) {
        this.learnTime = learnTime;
    }

    public boolean isIndex() {
        return index;
    }

    public void setIndex(boolean index) {
        this.index = index;
    }

    public long getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(long onlineTime) {
        this.onlineTime = onlineTime;
    }
}
