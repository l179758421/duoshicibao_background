package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.runer.cibao.base.BaseBean;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/11/1
 **/
@Entity
@Table(name="oneWord")
public class OneWord  extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    @Column(length = 1000)
    String content ;

    @Column(length = 1000)
    String translation ;



    String oneWordName ;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    Date createTime ;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOneWordName() {
        return oneWordName;
    }

    public void setOneWordName(String oneWordName) {
        this.oneWordName = oneWordName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
