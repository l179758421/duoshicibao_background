package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.runer.cibao.base.BaseBean;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/27
 **/
@Entity
@Table(name="AppUserAccount")
public class AppUserAccount  extends BaseBean {


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "appUserAccount")
    private List<AppUserOrder> appUserOrderList = new ArrayList<>() ;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;


    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    AppUser appUser ;

    Integer goldCoins;

    Long accumulatePoints ;


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

    public Integer getGoldCoins() {
        return goldCoins;
    }

    public void setGoldCoins(Integer goldCoins) {
        this.goldCoins = goldCoins;
    }

    public Long getAccumulatePoints() {
        return accumulatePoints;
    }

    public void setAccumulatePoints(Long accumulatePoints) {
        this.accumulatePoints = accumulatePoints;
    }

    public List<AppUserOrder> getAppUserOrderList() {
        return appUserOrderList;
    }

    public void setAppUserOrderList(List<AppUserOrder> appUserOrderList) {
        this.appUserOrderList = appUserOrderList;
    }
}
