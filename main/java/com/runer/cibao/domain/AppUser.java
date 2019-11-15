package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.runer.cibao.Config;
import com.runer.cibao.base.BaseBean;
import com.runer.cibao.domain.person_word.PersonalLearnWord;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.*;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/7
 **/
@Entity
@Table(name = "AppUser")
public class AppUser extends BaseBean {


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appUser")
    private List<Medals> medals = new ArrayList<>();


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appUser")
    private Set<PersonalLearnWord> personalLearnWords = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appUser")
    private List<MessageRead> messageReads = new ArrayList<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appUser")
    private List<AppUserBindSchool> appUserBindSchools = new ArrayList<>();


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appUser")
    private List<PunchCard> punchCards = new ArrayList<>();


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appUser")
    private List<ErrorRecovery> errorRecoveries = new ArrayList<>();


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appUser")
    private List<NewBookWord> newBookWords = new ArrayList<>();

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "appUser")
    private AppUserAccount appUserAccount;


    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "appUser")
    private PersonalLearnBooks personalLearnBooks;


    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "appUser")
    private MailAddres mailAddres;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appUser")
    private List<PersonTest> personTests = new ArrayList<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<RedeemCode> redeemCodeList = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String password;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private ClassInSchool classInSchool;

    @Column(unique = true)
    private String phoneNum;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date registerDate;


    private String imgUrl;

    private String name;

    private Long provinceId;

    private Long cityId;

    private Long areaId;

    private String address;

    private String sign;


    private Integer sex = Config.FEMALE;

    /**
     * 微信openId
     */
    @Column(unique = true)
    String wechatOpenId;
    /**
     * 微信号
     */
    String wechatNum;

    String wechatNickName;
    /**
     * 微博openId
     */
    @Column(unique = true)
    String weiboOpenId;

    /**
     * 微博号
     */

    String weiboNumber;

    String weiboNickName;
    /**
     * qq OpenId
     */
    @Column(unique = true)
    String qqOpenId;
    /**
     * qq号
     */
    String qqNumber;

    String qqNickName;
    /**
     * 第三方的头像图片
     */
    String thirdImgUrl;

    /**
     * 第三方的昵称
     */
    String thirdNickName;

    @Column(unique = true)
    String uid;


    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date birthDay;

    /**
     * 绑定的schoolID ==School  uid ;
     */
    String schoolId;


    String schoolName;

    /**
     * 对应的班级信ID ；
     */
    Long classInSchoolId;

    String phoneForInfo;

    String realNameForInfo;

    @Transient
    int booksCount;

    //累计打卡的天数；
    int punchCardsDays;

    String level;//等级


    @Transient
    long learnTime;


    @Transient
    boolean hasCompeltedInfo;

    /**
     * 是否是批量的生成的账号；
     */
    Integer isBatchCreate;

    String pass64;
    @Transient
    String lastSignTime;//最近登录时间
    @Transient
    String totalStudyTime;//累计在线时长
    @Transient
    String todayStudyTime;//累计学习时长

    long wordNum;//单词记忆总量

    @Transient
    Long newWordNum;//今日学习单词数量
    @Transient
    Long wordsNumReview;    //复习单词数量
    @Transient
    String learnBookWords;  //学习进度
    @Transient
    String learnBookName;  //当前学习课本名称
    @Transient
    String number1;//课本测试平均成绩
    @Transient
    String number2;//单元测试平均成绩
    @Transient
    String teacher;//班主任
    @Transient
    Long standardDay;//达标天数
    @Transient
    String totalNewVolidTime;//最近一次有效学习时长
    @Transient
    String totalNewOnlineTime;//最近一次学习时长
    @Transient
    String benginStartTime; //开始学习时间


    public String getBenginStartTime() {
        return benginStartTime;
    }

    public void setBenginStartTime(String benginStartTime) {
        this.benginStartTime = benginStartTime;
    }

    public String getLearnBookWords() {
        return learnBookWords;
    }

    public void setLearnBookWords(String learnBookWords) {
        this.learnBookWords = learnBookWords;
    }

    public Long getNewWordNum() {
        return newWordNum;
    }

    public void setNewWordNum(Long newWordNum) {
        this.newWordNum = newWordNum;
    }

    public Long getWordsNumReview() {
        return wordsNumReview;
    }

    public void setWordsNumReview(Long wordsNumReview) {
        this.wordsNumReview = wordsNumReview;
    }

    public String getTotalNewVolidTime() {
        return totalNewVolidTime;
    }

    public void setTotalNewVolidTime(String totalNewVolidTime) {
        this.totalNewVolidTime = totalNewVolidTime;
    }

    public String getTotalNewOnlineTime() {
        return totalNewOnlineTime;
    }

    public void setTotalNewOnlineTime(String totalNewOnlineTime) {
        this.totalNewOnlineTime = totalNewOnlineTime;
    }

    public Long getStandardDay() {
        return standardDay;
    }

    public void setStandardDay(Long standardDay) {
        this.standardDay = standardDay;
    }

    public List<Medals> getMedals() {
        return medals;
    }

    public void setMedals(List<Medals> medals) {
        this.medals = medals;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getNumber1() {
        return number1;
    }

    public void setNumber1(String number1) {
        this.number1 = number1;
    }

    public String getNumber2() {
        return number2;
    }

    public void setNumber2(String number2) {
        this.number2 = number2;
    }

    public String getLastSignTime() {
        return lastSignTime;
    }

    public void setLastSignTime(String lastSignTime) {
        this.lastSignTime = lastSignTime;
    }

    public String getTotalStudyTime() {
        return totalStudyTime;
    }

    public void setTotalStudyTime(String totalStudyTime) {
        this.totalStudyTime = totalStudyTime;
    }

    public String getTodayStudyTime() {
        return todayStudyTime;
    }

    public void setTodayStudyTime(String todayStudyTime) {
        this.todayStudyTime = todayStudyTime;
    }

    public long getWordNum() {
        return wordNum;
    }

    public void setWordNum(long wordNum) {
        this.wordNum = wordNum;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }


    public String getPhoneForInfo() {
        return phoneForInfo;
    }

    public void setPhoneForInfo(String phoneForInfo) {
        this.phoneForInfo = phoneForInfo;
    }

    public String getRealNameForInfo() {
        return realNameForInfo;
    }

    public void setRealNameForInfo(String realNameForInfo) {
        this.realNameForInfo = realNameForInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<RedeemCode> getRedeemCodeList() {
        return redeemCodeList;
    }

    public void setRedeemCodeList(List<RedeemCode> redeemCodeList) {
        this.redeemCodeList = redeemCodeList;
    }

    public ClassInSchool getClassInSchool() {
        return classInSchool;
    }

    public void setClassInSchool(ClassInSchool classInSchool) {
        this.classInSchool = classInSchool;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PersonTest> getPersonTests() {
        return personTests;
    }

    public void setPersonTests(List<PersonTest> personTests) {
        this.personTests = personTests;
    }

    public String getWechatOpenId() {
        return wechatOpenId;
    }

    public void setWechatOpenId(String wechatOpenId) {
        this.wechatOpenId = wechatOpenId;
    }

    public String getWechatNum() {
        return wechatNum;
    }

    public void setWechatNum(String wechatNum) {
        this.wechatNum = wechatNum;
    }

    public String getWeiboOpenId() {
        return weiboOpenId;
    }

    public void setWeiboOpenId(String weiboOpenId) {
        this.weiboOpenId = weiboOpenId;
    }

    public String getWeiboNumber() {
        return weiboNumber;
    }

    public void setWeiboNumber(String weiboNumber) {
        this.weiboNumber = weiboNumber;
    }

    public String getQqOpenId() {
        return qqOpenId;
    }

    public void setQqOpenId(String qqOpenId) {
        this.qqOpenId = qqOpenId;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public String getThirdImgUrl() {
        return thirdImgUrl;
    }

    public void setThirdImgUrl(String thirdImgUrl) {
        this.thirdImgUrl = thirdImgUrl;
    }

    public String getThirdNickName() {
        return thirdNickName;
    }

    public void setThirdNickName(String thirdNickName) {
        this.thirdNickName = thirdNickName;
    }

    public MailAddres getMailAddres() {
        return mailAddres;
    }

    public void setMailAddres(MailAddres mailAddres) {
        this.mailAddres = mailAddres;
    }

    public PersonalLearnBooks getPersonalLearnBooks() {
        return personalLearnBooks;
    }

    public void setPersonalLearnBooks(PersonalLearnBooks personalLearnBooks) {
        this.personalLearnBooks = personalLearnBooks;
    }

    public AppUserAccount getAppUserAccount() {
        return appUserAccount;
    }

    public void setAppUserAccount(AppUserAccount appUserAccount) {
        this.appUserAccount = appUserAccount;
    }

    public List<NewBookWord> getNewBookWords() {
        return newBookWords;
    }

    public void setNewBookWords(List<NewBookWord> newBookWords) {
        this.newBookWords = newBookWords;
    }

    public List<ErrorRecovery> getErrorRecoveries() {
        return errorRecoveries;
    }

    public void setErrorRecoveries(List<ErrorRecovery> errorRecoveries) {
        this.errorRecoveries = errorRecoveries;
    }


    public List<PunchCard> getPunchCards() {
        return punchCards;
    }

    public void setPunchCards(List<PunchCard> punchCards) {
        this.punchCards = punchCards;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public Long getClassInSchoolId() {
        return classInSchoolId;
    }

    public void setClassInSchoolId(Long classInSchoolId) {
        this.classInSchoolId = classInSchoolId;
    }

    public List<AppUserBindSchool> getAppUserBindSchools() {
        return appUserBindSchools;
    }

    public void setAppUserBindSchools(List<AppUserBindSchool> appUserBindSchools) {
        this.appUserBindSchools = appUserBindSchools;
    }


    public int getPunchCardsDays() {
        return punchCardsDays;
    }

    public void setPunchCardsDays(int punchCardsDays) {
        this.punchCardsDays = punchCardsDays;
    }

    public List<MessageRead> getMessageReads() {
        return messageReads;
    }

    public void setMessageReads(List<MessageRead> messageReads) {
        this.messageReads = messageReads;
    }

    public int getBooksCount() {
        return booksCount;
    }

    public void setBooksCount(int booksCount) {
        this.booksCount = booksCount;
    }

    public Set<PersonalLearnWord> getPersonalLearnWords() {
        return personalLearnWords;
    }

    public void setPersonalLearnWords(Set<PersonalLearnWord> personalLearnWords) {
        this.personalLearnWords = personalLearnWords;
    }

    public Integer getIsBatchCreate() {
        return isBatchCreate;
    }

    public void setIsBatchCreate(Integer isBatchCreate) {
        this.isBatchCreate = isBatchCreate;
    }

    public String getPass64() {
        return pass64;
    }

    public void setPass64(String pass64) {
        this.pass64 = pass64;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public boolean isHasCompeltedInfo() {
        return hasCompeltedInfo;
    }

    public String getWechatNickName() {
        return wechatNickName;
    }

    public void setWechatNickName(String wechatNickName) {
        this.wechatNickName = wechatNickName;
    }

    public String getWeiboNickName() {
        return weiboNickName;
    }

    public void setWeiboNickName(String weiboNickName) {
        this.weiboNickName = weiboNickName;
    }

    public String getQqNickName() {
        return qqNickName;
    }

    public void setQqNickName(String qqNickName) {
        this.qqNickName = qqNickName;
    }

    public long getLearnTime() {
        return learnTime;
    }

    public void setLearnTime(long learnTime) {
        this.learnTime = learnTime;
    }

    public void setHasCompeltedInfo(boolean hasCompeltedInfo) {
        this.hasCompeltedInfo = hasCompeltedInfo;
    }

    public boolean hasCompeltedInfos() {
        if (StringUtils.isEmpty(name)) {
            return false;
        }
        if (birthDay == null) {
            return false;
        }
        if (provinceId == null || cityId == null || areaId == null) {
            return false;
        }
        return true;
    }

    public String getLearnBookName() {
        return learnBookName;
    }

    public void setLearnBookName(String learnBookName) {
        this.learnBookName = learnBookName;
    }
}
