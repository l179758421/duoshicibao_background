package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.runer.cibao.Config;
import com.runer.cibao.base.BaseBean;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/28
 **/
@Entity
@Table(name = "NewBookWord")
public class NewBookWord extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    AppUser appUser ;



    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    BookWord bookWord ;


    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createDate ;


    Integer deleteState = Config.DELETED_NOT;


    Integer isNowWord = Config.IS_NOW_WORDS ;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public BookWord getBookWord() {
        return bookWord;
    }

    public void setBookWord(BookWord bookWord) {
        this.bookWord = bookWord;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(Integer deleteState) {
        this.deleteState = deleteState;
    }

    public Integer getIsNowWord() {
        return isNowWord;
    }

    public void setIsNowWord(Integer isNowWord) {
        this.isNowWord = isNowWord;
    }
}
