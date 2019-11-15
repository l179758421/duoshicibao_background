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
 * @Date 2018/6/14
 * 课本的单元
 **/

@Entity
@Table(name = "BookUnit")
public class BookUnit extends BaseBean {



    /**
     * 包含的测试
     */
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "bookUnit")
    private List<PersonalLearnUnit> personalLearnUnits =new ArrayList<>() ;



    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.LAZY,mappedBy = "bookUnit")
    private BookTestQuetions bookTestQuetions ;


    /**
     * 包含的单词
     */
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "unit")
    private List<BookWord> bookWordList =new ArrayList<>();


    /**
     * 读经的课本
     */
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id")
    LearnBook learnBook ;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    String name ;

    @Transient
    Integer testScore;
    @Transient
    String testTime;
    @Transient
    String passTime;
    @Transient
    String unitTypeNumber1;//语音复习次数
    @Transient
    String unitTypeNumber2;//听音辨意次数
    @Transient
    String unitTypeNumber4;//智能默写次数
    @Transient
    String unitTypeNumber5;//句子填空次数
    @Transient
    String unitTypeNumber6;//句式练习次数
    @Transient
    String unitTestStart;//记忆复习


    public String getUnitTestStart() {
        return unitTestStart;
    }

    public void setUnitTestStart(String unitTestStart) {
        this.unitTestStart = unitTestStart;
    }

    public String getUnitTypeNumber2() {
        return unitTypeNumber2;
    }

    public void setUnitTypeNumber2(String unitTypeNumber2) {
        this.unitTypeNumber2 = unitTypeNumber2;
    }

    public String getUnitTypeNumber1() {
        return unitTypeNumber1;
    }

    public void setUnitTypeNumber1(String unitTypeNumber1) {
        this.unitTypeNumber1 = unitTypeNumber1;
    }

    public String getUnitTypeNumber4() {
        return unitTypeNumber4;
    }

    public void setUnitTypeNumber4(String unitTypeNumber4) {
        this.unitTypeNumber4 = unitTypeNumber4;
    }

    public String getUnitTypeNumber5() {
        return unitTypeNumber5;
    }

    public void setUnitTypeNumber5(String unitTypeNumber5) {
        this.unitTypeNumber5 = unitTypeNumber5;
    }

    public String getUnitTypeNumber6() {
        return unitTypeNumber6;
    }

    public void setUnitTypeNumber6(String unitTypeNumber6) {
        this.unitTypeNumber6 = unitTypeNumber6;
    }

    public Integer getTestScore() {
        return testScore;
    }

    public void setTestScore(Integer testScore) {
        this.testScore = testScore;
    }

    public String getTestTime() {
        return testTime;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }

    public String getPassTime() {
        return passTime;
    }

    public void setPassTime(String passTime) {
        this.passTime = passTime;
    }

    public List<BookWord> getBookWordList() {
        return bookWordList;
    }

    public void setBookWordList(List<BookWord> bookWordList) {
        this.bookWordList = bookWordList;
    }

    public LearnBook getLearnBook() {
        return learnBook;
    }

    public void setLearnBook(LearnBook learnBook) {
        this.learnBook = learnBook;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BookTestQuetions getBookTestQuetions() {
        return bookTestQuetions;
    }

    public void setBookTestQuetions(BookTestQuetions bookTestQuetions) {
        this.bookTestQuetions = bookTestQuetions;
    }


    public List<PersonalLearnUnit> getPersonalLearnUnits() {
        return personalLearnUnits;
    }

    public void setPersonalLearnUnits(List<PersonalLearnUnit> personalLearnUnits) {
        this.personalLearnUnits = personalLearnUnits;
    }


}
