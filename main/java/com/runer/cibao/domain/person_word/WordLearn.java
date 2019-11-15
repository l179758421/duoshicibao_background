package com.runer.cibao.domain.person_word;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.runer.cibao.base.BaseBean;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/17
 * 听音复习
 **/
@Entity
@Table(name = "word_learn")
public class WordLearn extends BaseBean {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    PersonalLearnWord personalLearnWord ;

    int state ;

    int isSuccess ;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    Date learnDate  ;

    int  learnTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonalLearnWord getPersonalLearnWord() {
        return personalLearnWord;
    }

    public void setPersonalLearnWord(PersonalLearnWord personalLearnWord) {
        this.personalLearnWord = personalLearnWord;
    }

    public Date getLearnDate() {
        return learnDate;
    }

    public void setLearnDate(Date learnDate) {
        this.learnDate = learnDate;
    }

    public int getLearnTime() {
        return learnTime;
    }

    public void setLearnTime(int learnTime) {
        this.learnTime = learnTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(int isSuccess) {
        this.isSuccess = isSuccess;
    }
}
