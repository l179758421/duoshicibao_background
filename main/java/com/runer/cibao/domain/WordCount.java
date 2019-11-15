package com.runer.cibao.domain;

import com.runer.cibao.base.BaseBean;

import javax.persistence.*;
import java.util.Date;

/**
 * @author k
 * @Date: Created in 15:11 2018/8/23
 * @Description:
 */
@Table
@Entity(name = "wordCount")
public class WordCount extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    private Long appUserId;

    private Integer status;

    private Long bookUnitId ;

    private Long newWordsCount;

    private Date createDate;

    public Long getNewWordsCount() {
        return newWordsCount;
    }

    public void setNewWordsCount(Long newWordsCount) {
        this.newWordsCount = newWordsCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getBookUnitId() {
        return bookUnitId;
    }

    public void setBookUnitId(Long bookUnitId) {
        this.bookUnitId = bookUnitId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
