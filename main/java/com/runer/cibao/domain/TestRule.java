package com.runer.cibao.domain;

import com.runer.cibao.base.BaseBean;

import javax.persistence.*;

@Entity
@Table(name = "TestRule")
public class TestRule extends BaseBean {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    String rule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
