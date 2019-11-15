package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.runer.cibao.base.BaseBean;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/27
 *
 **/


@Entity
@Table(name = "appUserOrder")
public class AppUserOrder  extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    AppUserAccount appUserAccount ;


    @NotFound(action =NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.LAZY ,mappedBy =  "appUserOrder")
    @JoinColumn(name = "book_id")
    PersonalLearnBook personalLearnBook ;


    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.LAZY,mappedBy =  "appUserOrder")
    @JoinColumn(name = "reedemId")
    RedeemCode redeemCode ;

    Integer type ;//1 充值 2购买

    String title ;

    String des ;

    Long relatedId ;

    int  changeNum ;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    Date createDate ;

    public AppUserOrder() {
    }

    public AppUserOrder(AppUserAccount appUserAccount, Integer type, String title, String des, Long relatedId, int changeNum, Date createDate) {
        this.appUserAccount = appUserAccount;
        this.type = type;
        this.title = title;
        this.des = des;
        this.relatedId = relatedId;
        this.changeNum = changeNum;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUserAccount getAppUserAccount() {
        return appUserAccount;
    }

    public void setAppUserAccount(AppUserAccount appUserAccount) {
        this.appUserAccount = appUserAccount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Long getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Long relatedId) {
        this.relatedId = relatedId;
    }

    public int getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(int changeNum) {
        this.changeNum = changeNum;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PersonalLearnBook getPersonalLearnBook() {
        return personalLearnBook;
    }

    public void setPersonalLearnBook(PersonalLearnBook personalLearnBook) {
        this.personalLearnBook = personalLearnBook;
    }

    public RedeemCode getRedeemCode() {
        return redeemCode;
    }

    public void setRedeemCode(RedeemCode redeemCode) {
        this.redeemCode = redeemCode;
    }
}
