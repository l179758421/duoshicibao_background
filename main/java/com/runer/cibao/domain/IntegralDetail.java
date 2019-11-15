package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.runer.cibao.base.BaseBean;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "IntegralDetail")
public class IntegralDetail extends BaseBean {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Integer type;
    String name;
    Long number;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    Date createTime;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "integral_id")
    Integral integral;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integral getIntegral() {
        return integral;
    }

    public void setIntegral(Integral integral) {
        this.integral = integral;
    }
}
