package com.runer.cibao.domain;

import com.runer.cibao.base.BaseBean;

import javax.persistence.*;

@Entity
@Table(name="IntegralDes")
public class IntegralDes extends BaseBean {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String integralSource;//积分的来源
    String integralUse;//积分的用处

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntegralSource() {
        return integralSource;
    }

    public void setIntegralSource(String integralSource) {
        this.integralSource = integralSource;
    }

    public String getIntegralUse() {
        return integralUse;
    }

    public void setIntegralUse(String integralUse) {
        this.integralUse = integralUse;
    }
}
