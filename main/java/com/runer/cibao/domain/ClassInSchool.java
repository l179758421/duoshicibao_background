package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
 * @Date 2018/6/7
 **/
@Entity
@Table(name = "ClassInSchool")
public class ClassInSchool extends BaseBean {



    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "classInSchool")
    List<Message> messages =new ArrayList<>() ;


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "classInSchool")
    List<AppUser> appUserList =new ArrayList<>() ;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;


    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    School school ;


    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    Date createTime ;

    String name ;
    @Transient
    String headMaster;//班主任
    @Transient
    Integer studentNum;//学员数量
    @Transient
    Integer withInTwoNum;//近两天学习人数
    @Transient
    Integer withInSevenNUm;//近七天学习人数

    public String getHeadMaster() {
        return headMaster;
    }

    public void setHeadMaster(String headMaster) {
        this.headMaster = headMaster;
    }

    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }

    public Integer getWithInTwoNum() {
        return withInTwoNum;
    }

    public void setWithInTwoNum(Integer withInTwoNum) {
        this.withInTwoNum = withInTwoNum;
    }

    public Integer getWithInSevenNUm() {
        return withInSevenNUm;
    }

    public void setWithInSevenNUm(Integer withInSevenNUm) {
        this.withInSevenNUm = withInSevenNUm;
    }

    String classInfo ;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addUser_id")
    User addUser ;


    @Transient
    @JsonProperty(value = "LAY_CHECKED")
    boolean LAY_CHECKED ;

    public List<AppUser> getAppUserList() {
        return appUserList;
    }

    public void setAppUserList(List<AppUser> appUserList) {
        this.appUserList = appUserList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public User getAddUser() {
        return addUser;
    }

    public void setAddUser(User addUser) {
        this.addUser = addUser;
    }

    public boolean isLAY_CHECKED() {
        return LAY_CHECKED;
    }

    public void setLAY_CHECKED(boolean LAY_CHECKED) {
        this.LAY_CHECKED = LAY_CHECKED;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
