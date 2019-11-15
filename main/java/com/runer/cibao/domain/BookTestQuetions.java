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
 * @Date 2018/6/19
 **/

@Entity
@Table(name = "BookTestQuetions")
public class BookTestQuetions extends BaseBean {


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "questions")
    List<PersonTest> personTests =new ArrayList<>() ;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    /**
     * 对应的单元
     */
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="unit_id")
    BookUnit bookUnit ;


    /**
     * 测试的名称--可以设置默认的值
     */
    String testName ;

    /**
     * 总分数；
     */
    Integer totalScore ;
    /**
     * 总时间（分钟）
     */
    Integer totalTime ;
    /**
     * 及格的分数
     */
    Integer passScore ;

    /*
    关联的测试的ids ；
     */
    @Column(length = Integer.MAX_VALUE)
    String   bookTestIds ;


    @Transient
    boolean LAY_CHECKED ;


    public List<PersonTest> getPersonTests() {
        return personTests;
    }

    public void setPersonTests(List<PersonTest> personTests) {
        this.personTests = personTests;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public BookUnit getBookUnit() {
        return bookUnit;
    }

    public void setBookUnit(BookUnit bookUnit) {
        this.bookUnit = bookUnit;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public Integer getPassScore() {
        return passScore;
    }

    public void setPassScore(Integer passScore) {
        this.passScore = passScore;
    }

    public String getBookTestIds() {
        return bookTestIds;
    }

    public void setBookTestIds(String bookTestIds) {
        this.bookTestIds = bookTestIds;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public boolean isLAY_CHECKED() {
        return LAY_CHECKED;
    }

    public void setLAY_CHECKED(boolean LAY_CHECKED) {
        this.LAY_CHECKED = LAY_CHECKED;
    }
}
