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
 * @Description:smartcommunity==
 * @Date 2018/5/28
 **/
@Entity
@Table(name = "user")
public class User extends BaseBean {



    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "sendUser")
    List<Message> messages =new ArrayList<>() ;


    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY,mappedBy = "user")
    Admin admin ;

    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY,mappedBy = "user")
    Teacher teacher;


    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY,mappedBy = "user")
    SchoolMaster schoolMaster;

    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY,mappedBy = "user")
    Agents agents;


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private List<AppUserBindSchool> appUserBindSchools =new ArrayList<>() ;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private List<ErrorRecovery> errorRecoveries =new ArrayList<>() ;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "createUser")
    private List<HelpToUser> helpToUsers =new ArrayList<>() ;


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "upLoadUser")
    private List<RedeemCode> redeemCodes = new ArrayList<>() ;


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "addUser")
    private List<ClassInSchool> classInSchoolList =new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;






    @Transient
    List<Roles> roles =new ArrayList<>();

    private String name ;

    private String passWord;

    @Column(unique = true)
    private String loginName;



@Column(length = 300)
    String rolesIds ;


    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createTiem ;

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

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Date getCreateTiem() {
        return createTiem;
    }

    public void setCreateTiem(Date createTiem) {
        this.createTiem = createTiem;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public List<ClassInSchool> getClassInSchoolList() {
        return classInSchoolList;
    }

    public void setClassInSchoolList(List<ClassInSchool> classInSchoolList) {
        this.classInSchoolList = classInSchoolList;
    }

    public List<RedeemCode> getRedeemCodes() {
        return redeemCodes;
    }

    public void setRedeemCodes(List<RedeemCode> redeemCodes) {
        this.redeemCodes = redeemCodes;
    }


    public List<HelpToUser> getHelpToUsers() {
        return helpToUsers;
    }

    public void setHelpToUsers(List<HelpToUser> helpToUsers) {
        this.helpToUsers = helpToUsers;
    }

    public List<ErrorRecovery> getErrorRecoveries() {
        return errorRecoveries;
    }

    public void setErrorRecoveries(List<ErrorRecovery> errorRecoveries) {
        this.errorRecoveries = errorRecoveries;
    }

    public List<AppUserBindSchool> getAppUserBindSchools() {
        return appUserBindSchools;
    }

    public void setAppUserBindSchools(List<AppUserBindSchool> appUserBindSchools) {
        this.appUserBindSchools = appUserBindSchools;
    }

    public SchoolMaster getSchoolMaster() {
        return schoolMaster;
    }

    public void setSchoolMaster(SchoolMaster schoolMaster) {
        this.schoolMaster = schoolMaster;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }


    public String getRolesIds() {
        return rolesIds;
    }

    public void setRolesIds(String rolesIds) {
        this.rolesIds = rolesIds;
    }

    public Agents getAgents() {
        return agents;
    }

    public void setAgents(Agents agents) {
        this.agents = agents;
    }
}
