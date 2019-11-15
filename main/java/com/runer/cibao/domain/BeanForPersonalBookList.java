package com.runer.cibao.domain;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/26
 *
 *个人的课本展示：：：：：---》》》》个人
 *
 **/
public class BeanForPersonalBookList {

    Integer currentScore ;

    boolean isPassed ;

    Long totalWordsNum ;

    Long currenNum ;

    Long bookid ;

    String bookCoverImg ;

    String stage ;

    String version ;

    String grade ;

    String privce ;

    Long personalLeanrBookId ;

    String bookName ;

    long  unitId ;

    String unitName ;

    String learnedWords ;

    boolean isFinished  ;

    boolean isPred ;

    private long boughtTime ;

    private long freeBoughtTime ;

    private  boolean  isFreeOutTime ;

    /**
     * 是否过期
     */
    boolean isValiable =true  ;
    /**
     * 是否购买
     * @return
     */
    boolean isBuy =false ;

    boolean currentUnitisFinished ;







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

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getLearnedWords() {
        return learnedWords;
    }

    public void setLearnedWords(String learnedWords) {
        this.learnedWords = learnedWords;
    }

    public Integer getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(Integer currentScore) {
        this.currentScore = currentScore;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }

    public Long getTotalWordsNum() {
        return totalWordsNum;
    }

    public void setTotalWordsNum(Long totalWordsNum) {
        this.totalWordsNum = totalWordsNum;
    }

    public Long getCurrenNum() {
        return currenNum;
    }

    public void setCurrenNum(Long currenNum) {
        this.currenNum = currenNum;
    }

    public Long getBookid() {
        return bookid;
    }

    public void setBookid(Long bookid) {
        this.bookid = bookid;
    }

    public String getBookCoverImg() {
        return bookCoverImg;
    }

    public void setBookCoverImg(String bookCoverImg) {
        this.bookCoverImg = bookCoverImg;
    }

    public Long getPersonalLeanrBookId() {
        return personalLeanrBookId;
    }

    public void setPersonalLeanrBookId(Long personalLeanrBookId) {
        this.personalLeanrBookId = personalLeanrBookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public boolean isPred() {
        return isPred;
    }

    public void setPred(boolean pred) {
        isPred = pred;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPrivce() {
        return privce;
    }

    public void setPrivce(String privce) {
        this.privce = privce;
    }

    public long getBoughtTime() {
        return boughtTime;
    }

    public void setBoughtTime(long boughtTime) {
        this.boughtTime = boughtTime;
    }

    public long getFreeBoughtTime() {
        return freeBoughtTime;
    }

    public void setFreeBoughtTime(long freeBoughtTime) {
        this.freeBoughtTime = freeBoughtTime;
    }

    public boolean isFreeOutTime() {
        return isFreeOutTime;
    }

    public void setFreeOutTime(boolean freeOutTime) {
        isFreeOutTime = freeOutTime;
    }

    public boolean isCurrentUnitisFinished() {
        return currentUnitisFinished;
    }

    public void setCurrentUnitisFinished(boolean currentUnitisFinished) {
        this.currentUnitisFinished = currentUnitisFinished;
    }
}
