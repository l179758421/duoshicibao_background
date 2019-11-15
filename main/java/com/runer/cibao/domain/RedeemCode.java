package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.runer.cibao.Config;
import com.runer.cibao.base.BaseBean;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/7
 兑换码


 forDes:
       /  chongzhima ;可以个人进行激活
                     也可以学校进行激活；

 **/

@Entity
@Table(name="RedeemCode")
public class RedeemCode  extends BaseBean {


    /**
     * 对应的order；
     */
    @JsonIgnore
    @NotFound(action =NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "order_id")
    AppUserOrder appUserOrder ;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    @Column(unique = true)
    String codeNum ;

    /*描述*/
    String des ;

    /*充值金额*/
    Integer codeMoney ;

    Integer state = Config.CODE_NOT_USE;

    /*个人激活*/
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    AppUser user ;


    /*学校激活*/
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    School school ;

    public RedeemCode() {

    }

    //有效期
    Integer termOfvalidity ;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    Date activeTime;


     @NotFound(action = NotFoundAction.IGNORE)
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "up_user_id")
     private User upLoadUser ;

     public boolean  isTimeOut(){
         if (state== Config.CODE_ACTIVE){
             return  false ;
         }
         if (DateUtils.addDays(createTime,termOfvalidity).before(new Date())){
              state = Config.CODE_OUT_TIME ;
              return  true ;
         }
         return  false ;
     }




    public Integer getCodeMoney() {
        return codeMoney;
    }

    public void setCodeMoney(Integer codeMoney) {
        this.codeMoney = codeMoney;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(String codeNum) {
        this.codeNum = codeNum;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public User getUpLoadUser() {
        return upLoadUser;
    }

    public void setUpLoadUser(User upLoadUser) {
        this.upLoadUser = upLoadUser;
    }

    public Integer getTermOfvalidity() {
        return termOfvalidity;
    }

    public void setTermOfvalidity(Integer termOfvalidity) {
        this.termOfvalidity = termOfvalidity;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public AppUserOrder getAppUserOrder() {
        return appUserOrder;
    }

    public void setAppUserOrder(AppUserOrder appUserOrder) {
        this.appUserOrder = appUserOrder;
    }
}
