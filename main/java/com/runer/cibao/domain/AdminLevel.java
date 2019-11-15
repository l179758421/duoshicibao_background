package com.runer.cibao.domain;

import com.runer.cibao.base.BaseBean;

import javax.persistence.*;

@Entity
@Table(name = "AdminLevel")
public class AdminLevel extends BaseBean {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    String levelRule;
    String levelUse;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevelRule() {
        return levelRule;
    }

    public void setLevelRule(String levelRule) {
        this.levelRule = levelRule;
    }

    public String getLevelUse() {
        return levelUse;
    }

    public void setLevelUse(String levelUse) {
        this.levelUse = levelUse;
    }
}
