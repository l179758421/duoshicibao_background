package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.runer.cibao.Config;
import com.runer.cibao.base.BaseBean;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/23
 *
 *
 *  广告管理
 *
 *
 **/
@Entity
@Table(name="Advertisement")
public class Advertisement extends BaseBean {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    /**
     * todo 检验url的正确
     */
    String url ;

    String imgUrl ;

    String title ;

    Integer type  = Config.RECHARGE_AD_TYPE;


    //todo
    Long relatedId ;


    Integer orderNum ;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    Date startTime ;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    Date endTime ;



    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createDate ;

    @Transient
    String dateRange ;


    @Transient
    boolean isOutDate ;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Long relatedId) {
        this.relatedId = relatedId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public boolean isOutDate() {
        return isOutDate;
    }

    public void setOutDate(boolean outDate) {
        isOutDate = outDate;
    }
}
