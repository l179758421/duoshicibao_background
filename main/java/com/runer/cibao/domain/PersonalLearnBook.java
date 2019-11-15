package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.runer.cibao.Config;
import com.runer.cibao.base.BaseBean;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/25
 * 个人的学习书本
 **/
@Entity
@Table(name = "personalLearnBook")
public class PersonalLearnBook  extends BaseBean {

    /**
     * 对应的order；
     */
    @JsonIgnore
    @NotFound(action =NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "order_id")
    AppUserOrder  appUserOrder ;

    /**
     * 包含的个人书本测试
     */
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY ,mappedBy = "personalLearnBook")
    List<PersonalTestForBook> personalTestForBooks =new ArrayList<>() ;

    /**
     * 包含的个人单元
     */
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "personalLearnBook")
    List<PersonalLearnUnit> personalLearnUnits =new ArrayList<>() ;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;


    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    LearnBook learnBook ;


    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="personalLearnBooksId")
    PersonalLearnBooks personalLearnBooks ;


    private String currentLeftWords ;

    /**
     * 当前学习的进度单词
     */
    Long currentWord ;

    String currnetWordname ;

    Long currentWordNum ;


    @Temporal(TemporalType.TIMESTAMP)
    Date boughtTime ;

    /**
     * 是否是当前学习的book ；
     */
    Integer isCurrentBook = Config.NOT_CURRENT;

    /**
     * 当前的测试分数；
     */
    Integer score ;


    Integer preScore ;

    /**
     * 是否通过了测试；
     */
    Integer isPassed ;

    //当前学习的单元
    Long unitId ;

    //当前学习单元的进度
    Long  unitWordProgress ;

    String currentUnitName ;

    long totalWordNum ;

    String learnedWords ;

    Integer isFinished = Config.notFinished ;

    Integer isPreTested = Config.NOT_PRE_TEST ;

    boolean isCurrentUnitFinished ;

    /**
     * 是否购买
     */
    boolean  isBuy;
    /**
     * 是否过期--一年的时间；
     */
    boolean isValiable =true  ;

    @Temporal(TemporalType.TIMESTAMP)
    Date   freeBoughtTime ;



    @Transient
    private String lastTestTime;//最后一次测试时间

    @Transient
    private String learnBeforeScore;//学前成绩

    @Transient
    private  String learnAfterScore;//学后成绩

    @Transient
    private String extractPoints;//提分

    @Transient
    private String teacherSuggest;//老师点评

    @Transient
    private Integer appUserNumber;//人员数

    public Integer getAppUserNumber() {
        return appUserNumber;
    }

    public void setAppUserNumber(Integer appUserNumber) {
        this.appUserNumber = appUserNumber;
    }

    public String getLastTestTime() {
        return lastTestTime;
    }

    public void setLastTestTime(String lastTestTime) {
        this.lastTestTime = lastTestTime;
    }

    public String getLearnBeforeScore() {
        return learnBeforeScore;
    }

    public void setLearnBeforeScore(String learnBeforeScore) {
        this.learnBeforeScore = learnBeforeScore;
    }

    public String getLearnAfterScore() {
        return learnAfterScore;
    }

    public void setLearnAfterScore(String learnAfterScore) {
        this.learnAfterScore = learnAfterScore;
    }

    public String getTeacherSuggest() {
        return teacherSuggest;
    }

    public void setTeacherSuggest(String teacherSuggest) {
        this.teacherSuggest = teacherSuggest;
    }

    public PersonalLearnBook(){

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LearnBook getLearnBook() {
        return learnBook;
    }

    public void setLearnBook(LearnBook learnBook) {
        this.learnBook = learnBook;
    }

    public PersonalLearnBooks getPersonalLearnBooks() {
        return personalLearnBooks;
    }

    public void setPersonalLearnBooks(PersonalLearnBooks personalLearnBooks) {
        this.personalLearnBooks = personalLearnBooks;
    }

    public Long getCurrentWord() {
        return currentWord;
    }

    public void setCurrentWord(Long currentWord) {
        this.currentWord = currentWord;
    }

    public Date getBoughtTime() {
        return boughtTime;
    }

    public void setBoughtTime(Date boughtTime) {
        this.boughtTime = boughtTime;
    }

    public Integer getIsCurrentBook() {
        return isCurrentBook;
    }

    public void setIsCurrentBook(Integer isCurrentBook) {
        this.isCurrentBook = isCurrentBook;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(Integer isPassed) {
        this.isPassed = isPassed;
    }


    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }


    public List<PersonalLearnUnit> getPersonalLearnUnits() {
        return personalLearnUnits;
    }

    public void setPersonalLearnUnits(List<PersonalLearnUnit> personalLearnUnits) {
        this.personalLearnUnits = personalLearnUnits;
    }

    public List<PersonalTestForBook> getPersonalTestForBooks() {
        return personalTestForBooks;
    }

    public void setPersonalTestForBooks(List<PersonalTestForBook> personalTestForBooks) {
        this.personalTestForBooks = personalTestForBooks;
    }

    public void setUnitWordProgress(Long unitWordProgress) {
        this.unitWordProgress = unitWordProgress;
    }

    public String getCurrentUnitName() {
        return currentUnitName;
    }

    public void setCurrentUnitName(String currentUnitName) {
        this.currentUnitName = currentUnitName;
    }

    public Long getCurrentWordNum() {

        if (currentWordNum==null){
            currentWordNum = 0L;
        }

        return currentWordNum;
    }

    public void setCurrentWordNum(Long currentWordNum) {
        this.currentWordNum = currentWordNum;
    }

    public Long getUnitWordProgress() {
        return unitWordProgress;
    }

    public AppUserOrder getAppUserOrder() {
        return appUserOrder;
    }

    public void setAppUserOrder(AppUserOrder appUserOrder) {
        this.appUserOrder = appUserOrder;
    }

    public String getCurrnetWordname() {
        return currnetWordname;
    }

    public void setCurrnetWordname(String currnetWordname) {
        this.currnetWordname = currnetWordname;
    }

    public long getTotalWordNum() {
        return totalWordNum;
    }

    public void setTotalWordNum(long totalWordNum) {
        this.totalWordNum = totalWordNum;
    }

    public String getLearnedWords() {
        return learnedWords;
    }

    public void setLearnedWords(String learnedWords) {
        this.learnedWords = learnedWords;
    }

    public Integer getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Integer isFinished) {
        this.isFinished = isFinished;
    }

    public Integer getIsPreTested() {
        return isPreTested;
    }

    public void setIsPreTested(Integer isPreTested) {
        this.isPreTested = isPreTested;
    }

    public boolean isBuy() {
        return isBuy;
    }
    public void setBuy(boolean buy) {
        isBuy = buy;
    }

    public boolean isValiable() {
        return isValiable;
    }

    public void setValiable(boolean valiable) {
        isValiable = valiable;
    }

    public Date getFreeBoughtTime() {
        return freeBoughtTime;
    }

    public void setFreeBoughtTime(Date freeBoughtTime) {
        this.freeBoughtTime = freeBoughtTime;
    }

    public boolean isCurrentUnitFinished() {
        return isCurrentUnitFinished;
    }

    public void setCurrentUnitFinished(boolean currentUnitFinished) {
        isCurrentUnitFinished = currentUnitFinished;
    }

    public String getCurrentLeftWords() {
        return currentLeftWords;
    }

    public void setCurrentLeftWords(String currentLeftWords) {
        this.currentLeftWords = currentLeftWords;
    }

    public Integer getPreScore() {
        return preScore;
    }

    public void setPreScore(Integer preScore) {
        this.preScore = preScore;
    }

    public String getExtractPoints() {
        return extractPoints;
    }

    public void setExtractPoints(String extractPoints) {
        this.extractPoints = extractPoints;
    }
}
