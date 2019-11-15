package com.runer.cibao.domain;

import com.runer.cibao.base.BaseBean;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/11/26
 **/
@Entity
@Table(name = "user_book_update_record")
public class UserBookUpdateRecord extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    long bookId ;

    long userId ;

    Date updateOrDownloadTime ;

    // string : download , String update ;
    String type ;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getUpdateOrDownloadTime() {
        return updateOrDownloadTime;
    }

    public void setUpdateOrDownloadTime(Date updateOrDownloadTime) {
        this.updateOrDownloadTime = updateOrDownloadTime;
    }
}
