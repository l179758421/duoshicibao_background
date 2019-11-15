package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.runer.cibao.base.BaseBean;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "Comment")
public class Comment extends BaseBean {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    String comment;

    Long userId;

    Long unitId;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    Date commemtTime ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCommemtTime() {
        return commemtTime;
    }

    public void setCommemtTime(Date commemtTime) {
        this.commemtTime = commemtTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }
}
