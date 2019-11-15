package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.runer.cibao.Config;
import com.runer.cibao.base.BaseBean;
import com.runer.cibao.domain.person_word.UnitStateBean;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/26
 **/

@Entity
@Table(name = "PersonalLearnUnit")
public class PersonalLearnUnit extends BaseBean {
    /**
     * 包含的测试
     */
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY ,mappedBy = "personalLearnUnit")
    List<PersonalTestForUnit> personalTestForUnits =new ArrayList<>() ;
    /**
     * 对应的课本
     */
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    @JoinColumn(name="personal_book_id")
    PersonalLearnBook personalLearnBook ;
    /**
     * 对应的单元；
     */
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="unit_id")
    BookUnit bookUnit ;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    Integer score ;

    Integer isPassed = Config.NOT_PASSED;

    Integer isCurrentLearnedUnit = Config.NOT_CURRENT;




    /**
     * 当前单元的状态相关 start ---------------------
     */
    String news ;

    String knows ;

    String strongs ;

    String leftIds ;

    String allWords ;

    //当前单元的学习阶段；
    int stage ;


    Integer isFinished  = Config.notFinished;
    /**
     * 当前的单元的状态相关 end -----------------------
     */



    @Transient
    Long personalLeanrBookId ;

    @Transient
    int count;

    @Transient
    long bookId ;
    @Transient
    long unitId ;

    @Transient
    String unitName;

    @Transient
    private Long personalLearnUnitCount;


    //此处为复习的状态；
    /**
     * 复习测试时间为效果检测后的第1、2、4、7、15天，共五次复习，两者同时需要复习优先进行复习测试
     */
    int reviewTestState;
    /**
     * 最近一次复习的时间；
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    Date lastedReviewTestDate ;

    /**
     * 最近一次复习的时间；
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    Date finishedDate;


    int is_learned;



    /**
     * 单元的学习状态 ；
     */
    private Integer  state ;



    public Integer getState() {
        return state;
    }
    public void setState(Integer state) {
        this.state = state;
    }

    public Long getPersonalLeanrBookId() {
        return personalLeanrBookId;
    }

    public void setPersonalLeanrBookId(Long personalLeanrBookId) {
        this.personalLeanrBookId = personalLeanrBookId;
    }

    public PersonalLearnUnit() {
    }

    public Long getPersonalLearnUnitCount() {
        return personalLearnUnitCount;
    }

    public void setPersonalLearnUnitCount(Long personalLearnUnitCount) {
        this.personalLearnUnitCount = personalLearnUnitCount;
    }

    public PersonalLearnBook getPersonalLearnBook() {
        return personalLearnBook;
    }

    public void setPersonalLearnBook(PersonalLearnBook personalLearnBook) {
        this.personalLearnBook = personalLearnBook;
    }

    public BookUnit getBookUnit() {
        return bookUnit;
    }

    public void setBookUnit(BookUnit bookUnit) {
        this.bookUnit = bookUnit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getIsCurrentLearnedUnit() {
        return isCurrentLearnedUnit;
    }

    public void setIsCurrentLearnedUnit(Integer isCurrentLearnedUnit) {
        this.isCurrentLearnedUnit = isCurrentLearnedUnit;
    }

    public List<PersonalTestForUnit> getPersonalTestForUnits() {
        return personalTestForUnits;
    }

    public void setPersonalTestForUnits(List<PersonalTestForUnit> personalTestForUnits) {
        this.personalTestForUnits = personalTestForUnits;
    }





    public Integer getIsFinished() {
        return isFinished;
    }


    public void setIsFinished(Integer isFinished) {
        this.isFinished = isFinished;
    }

    public int getReviewTestState() {
        return reviewTestState;
    }

    public void setReviewTestState(int reviewTestState) {
        this.reviewTestState = reviewTestState;
    }

    public Date getLastedReviewTestDate() {
        return lastedReviewTestDate;
    }

    public void setLastedReviewTestDate(Date lastedReviewTestDate) {
        this.lastedReviewTestDate = lastedReviewTestDate;
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    public UnitBeanForApi personalUnitToUnitApi(){
        UnitBeanForApi unitBeanForApi =new UnitBeanForApi() ;
        if (getPersonalLearnBook()!=null&&getPersonalLearnBook().getLearnBook()!=null)
        unitBeanForApi.setUserId(getPersonalLearnBook().getPersonalLearnBooks().getAppUser().getId());
        if (getBookUnit()!=null)
        unitBeanForApi.setUnitId(getBookUnit().getId());
        if (isPassed!=null)
        unitBeanForApi.setPassed(isPassed== Config.PASSED?true:false);
        unitBeanForApi.setTodayTest(false);
        if (score==null){
            score=0 ;
        }
        unitBeanForApi.setCurrentScore(score);
        if (getBookUnit()!=null)
        unitBeanForApi.setUnitName(getBookUnit().getName());
        if (getPersonalLearnBook()!=null&&getPersonalLearnBook().getLearnBook()!=null)
        unitBeanForApi.setBookId(getPersonalLearnBook().getLearnBook().getId());
        if (isFinished!=null)
        unitBeanForApi.setLearnFinished(isFinished== Config.isFinished?true:false);
        if (getPersonalLearnBook()!=null)
        unitBeanForApi.setPersonalBookId(getPersonalLearnBook().getId());
        unitBeanForApi.setPersonalUnitId(id);
        return  unitBeanForApi ;

    }



    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getKnows() {
        return knows;
    }

    public void setKnows(String knows) {
        this.knows = knows;
    }

    public String getStrongs() {
        return strongs;
    }

    public void setStrongs(String strongs) {
        this.strongs = strongs;
    }

    public String getLeftIds() {
        return leftIds;
    }

    public void setLeftIds(String leftIds) {
        this.leftIds = leftIds;
    }

    public String getAllWords() {
        return allWords;
    }

    public void setAllWords(String allWords) {
        this.allWords = allWords;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public String getUnitName() {
        return unitName;
    }

    public int getIs_learned() {
        return is_learned;
    }

    public void setIs_learned(int is_learned) {
        this.is_learned = is_learned;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setFinishedState(){
        //学习完成
        this.setState(1);
        this.setLeftIds("");
        this.setNews("");
        this.setStrongs("");
        this.setKnows("");
        this.setFinishedDate(new Date());
        this.setIsFinished(Config.isFinished);
    }


    /**
     * 设置学习的状态
     * @param stateBean
     */
    public void setUnitState(UnitStateBean stateBean){
        this.setStage(stateBean.getStage());
        this.setNews(stateBean.getNews());
        this.setStrongs(stateBean.getStrongs());
        this.setKnows(stateBean.getKnows());
        this.setLeftIds(stateBean.getLeftIds());
        this.setAllWords(stateBean.getAllWords());
    }

}
