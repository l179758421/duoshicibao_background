package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.runer.cibao.base.BaseBean;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Integral")
public class Integral extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Long total;//积分总和
    Long sign;//打卡积分
    Long share; //分享积分
    Long recharge; //充值积分
    Long offset; //抵消积分
    Long study;//学习时间积分
    long studyWord ;//学习单词积分；


    long testpassed ;


    public long getTestpassed() {
        return testpassed;
    }

    public void setTestpassed(long testpassed) {
        this.testpassed = testpassed;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    Date createTime;

    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    AppUser appUser;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSign() {
        return sign;
    }

    public void setSign(Long sign) {
        this.sign = sign;
    }

    public Long getShare() {
        return share;
    }

    public void setShare(Long share) {
        this.share = share;
    }

    public Long getRecharge() {
        return recharge;
    }

    public void setRecharge(Long recharge) {
        this.recharge = recharge;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }


    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getStudy() {
        return study;
    }

    public void setStudy(Long study) {
        this.study = study;
    }

    public long getStudyWord() {
        return studyWord;
    }

    public void setStudyWord(long studyWord) {
        this.studyWord = studyWord;
    }
}
