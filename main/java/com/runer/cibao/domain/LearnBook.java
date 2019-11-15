package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * @Date 2018/6/11
 * 课本
 **/
@Entity
@Table(name="learnBook")
public class LearnBook  extends BaseBean {

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "learnBook")
    private List<BookWord> bookWords  =new ArrayList<>() ;
    /**
     * 包含的个人图书；
     */
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "learnBook")
    private List<PersonalLearnBook> personalLearnBooks =new ArrayList<>() ;
    /**
     * 包含的单元
     */
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "learnBook")
    List<BookUnit> bookUnitList = new ArrayList<>() ;

    /**
     * 版本 课本名称 年级 课本单词数 被下载次数 创建时间 阶段
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    String version ;

    String bookName ;

    String grade ;

    Long wordsNum;

    Long downLoadNum ;

    @Transient
    Integer download;//购买数量


    //最近的一次更新时间；
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    Date updateTime ;

    @Column(nullable = false)
    String stage ;

    String imgUrl ;

    int price ;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    Date createTime ;

    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user ;


    public Integer getDownload() {
        return download;
    }

    public void setDownload(Integer download) {
        this.download = download;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Long getWordsNum() {
        return wordsNum;
    }

    public void setWordsNum(Long wordsNum) {
        this.wordsNum = wordsNum;
    }

    public Long getDownLoadNum() {
        return downLoadNum;
    }

    public void setDownLoadNum(Long downLoadNum) {
        this.downLoadNum = downLoadNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<BookUnit> getBookUnitList() {
        return bookUnitList;
    }

    public void setBookUnitList(List<BookUnit> bookUnitList) {
        this.bookUnitList = bookUnitList;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    public List<PersonalLearnBook> getPersonalLearnBooks() {
        return personalLearnBooks;
    }

    public void setPersonalLearnBooks(List<PersonalLearnBook> personalLearnBooks) {
        this.personalLearnBooks = personalLearnBooks;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setBookWords(List<BookWord> bookWords) {
        this.bookWords = bookWords;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
