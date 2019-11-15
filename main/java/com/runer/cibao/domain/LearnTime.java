package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.runer.cibao.base.BaseBean;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/11/3
 **/
@Entity
@Table(name = "learn_time")
public class LearnTime extends BaseBean {

    /**
     * 版本 课本名称 年级 课本单词数 被下载次数 创建时间 阶段
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long appUserId;

    long time;

    /**
     * 上传学习的日期；
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    Date date;

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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public String getStringDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
