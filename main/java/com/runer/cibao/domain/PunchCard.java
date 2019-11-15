package com.runer.cibao.domain;

import com.runer.cibao.base.BaseBean;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/29
 * 打卡
 **/
@Entity
@Table(name = "PunchCard")
public class PunchCard extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    AppUser appUser ;

    @Temporal(TemporalType.TIMESTAMP)
    Date punchDate ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Date getPunchDate() {
        return punchDate;
    }

    public void setPunchDate(Date punchDate) {
        this.punchDate = punchDate;
    }
}
