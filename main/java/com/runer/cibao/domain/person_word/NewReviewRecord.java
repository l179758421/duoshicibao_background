package com.runer.cibao.domain.person_word;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.runer.cibao.base.BaseBean;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/9/3
 **/
@Entity
@Table(name = "new_revice_record")
public class NewReviewRecord  extends BaseBean {

    /**
     * 每天进行统计一次；
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    Long userId ;

    @Column(length = 2000)
    String newWordsIds;

    @Column(length = 2000)
    String reviewsIds;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    Date uploadTime;

    @Transient
    int newNum ;

    @Transient
    int oldNum ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNewWordsIds() {
        if (StringUtils.isEmpty(newWordsIds)){
            return "" ;
        }
        return newWordsIds;
    }

    public void setNewWordsIds(String newWordsIds) {
        this.newWordsIds = newWordsIds;
    }

    public String getReviewsIds() {
        if (StringUtils.isEmpty(reviewsIds)){
            return "" ;
        }
        return reviewsIds;
    }

    public void setReviewsIds(String reviewsIds) {
        this.reviewsIds = reviewsIds;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public int getNewNum() {



        return newNum;
    }

    public void setNewNum(int newNum) {
        this.newNum = newNum;
    }

    public int getOldNum() {
        return oldNum;
    }

    public void setOldNum(int oldNum) {
        this.oldNum = oldNum;
    }
}
