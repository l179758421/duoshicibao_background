package com.runer.cibao.domain;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/22
 **/



import com.runer.cibao.base.BaseBean;

import javax.persistence.*;

/**
 * 关于后台的一些配置
 */
@Entity
@Table(name="AdminConfig")
public class AdminConfig extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;
    /**
     * 免费体验时间
     */
    int freeExperienceTime = 3;

    /**
     * 打卡学习时间
     */
    int cardLearningTime =20;//min
    /**
     * 打卡几天可以获得积分
     */
    int getScoreCardTime =5; //5;
    /**
     * 打卡满天数以后获得的积分
     */
    int cardScroeForFullDay =50 ; //50
    /**
     * 充值奖励积分
     */
    int rechargeScore = 30;


    int ad_width_ratio =  6 ;

    int ad_height_ratio =2;

    /**
     *一个单词获得的积分
     */
    int everyWordScore  =1 ;
    int everyWordScoreUp =100 ;

    /**
     //复习单词获得的积分 todo；
     */
    int everyOldWordScore =1 ;
    float everyOldWordScoreUp =100 ;

    /**
     * 学习10分钟，可得积分数有效学习 (有效学习获得的积分)
     */
    int studyScore = 1;
    int studyScoreUp =60 ;

    //闯关通过；
    int passTestScore =10 ;
    int passTestScoreUp =20 ;

    /**
     *  打卡每天获得的积分
     */
    int avrageCardScrore =10 ; //10
    /**
     * 分享奖励积分
     */
    int shareScore = 10;

    @Column(nullable=false,columnDefinition="INT default 30")
    int shareScoreUp =30;


    public AdminConfig() {}

    public AdminConfig(int freeExperienceTime, int cardLearningTime, int getScoreCardTime, int cardScroeForFullDay,
                       int avrageCardScrore) {
        this.freeExperienceTime = freeExperienceTime;
        this.cardLearningTime = cardLearningTime;
        this.getScoreCardTime = getScoreCardTime;
        this.cardScroeForFullDay = cardScroeForFullDay;
        this.avrageCardScrore = avrageCardScrore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFreeExperienceTime() {
        return freeExperienceTime;
    }

    public void setFreeExperienceTime(int freeExperienceTime) {
        this.freeExperienceTime = freeExperienceTime;
    }

    public int getCardLearningTime() {
        return cardLearningTime;
    }

    public void setCardLearningTime(int cardLearningTime) {
        this.cardLearningTime = cardLearningTime;
    }

    public int getGetScoreCardTime() {
        return getScoreCardTime;
    }

    public void setGetScoreCardTime(int getScoreCardTime) {
        this.getScoreCardTime = getScoreCardTime;
    }

    public int getCardScroeForFullDay() {
        return cardScroeForFullDay;
    }

    public void setCardScroeForFullDay(int cardScroeForFullDay) {
        this.cardScroeForFullDay = cardScroeForFullDay;
    }

    public int getAvrageCardScrore() {
        return avrageCardScrore;
    }

    public void setAvrageCardScrore(int avrageCardScrore) {
        this.avrageCardScrore = avrageCardScrore;
    }

    public int getAd_width_ratio() {
        return ad_width_ratio;
    }

    public void setAd_width_ratio(int ad_width_ratio) {
        this.ad_width_ratio = ad_width_ratio;
    }

    public int getAd_height_ratio() {
        return ad_height_ratio;
    }

    public void setAd_height_ratio(int ad_height_ratio) {
        this.ad_height_ratio = ad_height_ratio;
    }

    public int getShareScore() {
        return shareScore;
    }

    public void setShareScore(int shareScore) {
        this.shareScore = shareScore;
    }

    public int getRechargeScore() {
        return rechargeScore;
    }

    public void setRechargeScore(int rechargeScore) {
        this.rechargeScore = rechargeScore;
    }

    public int getStudyScore() {
        return studyScore;
    }

    public void setStudyScore(int studyScore) {
        this.studyScore = studyScore;
    }

    public int getEveryWordScore() {
        return everyWordScore;
    }

    public void setEveryWordScore(int everyWordScore) {
        this.everyWordScore = everyWordScore;
    }

    public int getEveryWordScoreUp() {
        return everyWordScoreUp;
    }

    public void setEveryWordScoreUp(int everyWordScoreUp) {
        this.everyWordScoreUp = everyWordScoreUp;
    }

    public int getEveryOldWordScore() {
        return everyOldWordScore;
    }

    public void setEveryOldWordScore(int everyOldWordScore) {
        this.everyOldWordScore = everyOldWordScore;
    }

    public float getEveryOldWordScoreUp() {
        return everyOldWordScoreUp;
    }

    public void setEveryOldWordScoreUp(float everyOldWordScoreUp) {
        this.everyOldWordScoreUp = everyOldWordScoreUp;
    }

    public int getStudyScoreUp() {
        return studyScoreUp;
    }

    public void setStudyScoreUp(int studyScoreUp) {
        this.studyScoreUp = studyScoreUp;
    }

    public int getPassTestScore() {
        return passTestScore;
    }

    public void setPassTestScore(int passTestScore) {
        this.passTestScore = passTestScore;
    }

    public int getPassTestScoreUp() {
        return passTestScoreUp;
    }

    public void setPassTestScoreUp(int passTestScoreUp) {
        this.passTestScoreUp = passTestScoreUp;
    }

    public int getShareScoreUp() {
        return shareScoreUp;
    }

    public void setShareScoreUp(int shareScoreUp) {
        this.shareScoreUp = shareScoreUp;
    }
}
