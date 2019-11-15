package com.runer.cibao.domain;

/**
 * @author k
 */
public class PersonalReport {
    String imgUrl;//头像
    String userName;//姓名
    String sex;//性别
    String schoolName;//所在学校名称
    String userClass;//所在班级名称
    String classTeacher;//班主任
    String phoneNum;//手机号
    Integer booksCount;//激活课本数
    Integer goldCoins;//当前金币余额
    String startLearnTime;//开始学习时间
    String lastSignTime;//最近登录时间
    String totalStudyTime;//总在线时长
    String todayStudyTime;//总学习时长
    String clockDaysNumber;//打卡天数
    String standardDaysNumber;//达标天数(最近三十天的)
    String standardDaysNumberAll;//总达标天数
    String newStudyTime;//今天在线时长
    String newLearnTimeDate;//今天有效学习时长
    String learnTimeNumber;//累计有效学习时长
    String learnBookName;//目前学习课本
    Long learnBookNameId;//目前学习课本id
    String learnBookWords;//目前已学习单词数(学习进度)
    String userWordsNumber;//累计学习单词数
    String learnTestBefore;//学前测试成绩
    String learnTestAfter;//学后测试成绩


    public Long getLearnBookNameId() {
        return learnBookNameId;
    }

    public void setLearnBookNameId(Long learnBookNameId) {
        this.learnBookNameId = learnBookNameId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNewStudyTime() {
        return newStudyTime;
    }

    public void setNewStudyTime(String newStudyTime) {
        this.newStudyTime = newStudyTime;
    }

    public String getStandardDaysNumberAll() {
        return standardDaysNumberAll;
    }

    public void setStandardDaysNumberAll(String standardDaysNumberAll) {
        this.standardDaysNumberAll = standardDaysNumberAll;
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

    public String getLastSignTime() {
        return lastSignTime;
    }

    public void setLastSignTime(String lastSignTime) {
        this.lastSignTime = lastSignTime;
    }

    public Integer getGoldCoins() {
        return goldCoins;
    }

    public void setGoldCoins(Integer goldCoins) {
        this.goldCoins = goldCoins;
    }

    public Integer getBooksCount() {
        return booksCount;
    }

    public void setBooksCount(Integer booksCount) {
        this.booksCount = booksCount;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    public String getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(String classTeacher) {
        this.classTeacher = classTeacher;
    }

    public String getStartLearnTime() {
        return startLearnTime;
    }

    public void setStartLearnTime(String startLearnTime) {
        this.startLearnTime = startLearnTime;
    }

    public String getClockDaysNumber() {
        return clockDaysNumber;
    }

    public void setClockDaysNumber(String clockDaysNumber) {
        this.clockDaysNumber = clockDaysNumber;
    }

    public String getStandardDaysNumber() {
        return standardDaysNumber;
    }

    public void setStandardDaysNumber(String standardDaysNumber) {
        this.standardDaysNumber = standardDaysNumber;
    }

    public String getNewLearnTimeDate() {
        return newLearnTimeDate;
    }

    public void setNewLearnTimeDate(String newLearnTimeDate) {
        this.newLearnTimeDate = newLearnTimeDate;
    }

    public String getLearnTimeNumber() {
        return learnTimeNumber;
    }

    public void setLearnTimeNumber(String learnTimeNumber) {
        this.learnTimeNumber = learnTimeNumber;
    }

    public String getLearnBookName() {
        return learnBookName;
    }

    public void setLearnBookName(String learnBookName) {
        this.learnBookName = learnBookName;
    }

    public String getLearnBookWords() {
        return learnBookWords;
    }

    public void setLearnBookWords(String learnBookWords) {
        this.learnBookWords = learnBookWords;
    }

    public String getUserWordsNumber() {
        return userWordsNumber;
    }

    public void setUserWordsNumber(String userWordsNumber) {
        this.userWordsNumber = userWordsNumber;
    }

    public String getLearnTestBefore() {
        return learnTestBefore;
    }

    public void setLearnTestBefore(String learnTestBefore) {
        this.learnTestBefore = learnTestBefore;
    }

    public String getLearnTestAfter() {
        return learnTestAfter;
    }

    public void setLearnTestAfter(String learnTestAfter) {
        this.learnTestAfter = learnTestAfter;
    }
}
