package com.runer.cibao.domain;

import com.runer.cibao.base.BaseBean;

import java.util.*;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/7
 **/
public class AppUserShowInfoBean extends BaseBean {



    long userId ;

    String userName ;

    String className ;
    long  classId ;

    long schoolId ;
    String schoolName ;

    //book的课本数
    long countBooks ;

    //最近的测试成绩；
    int theLatesdScore ;

    //当前课本的名称；
    String currentBookName ;

    //当前学习的单词数；
    String currentWord ;

    String currentUnit ;
    String currentWordName ;

    String progress ;


    Map<Long ,String> books =new HashMap<>();

    List<BookTestInfo> bookTestInfos =new ArrayList<>() ;

    List<UnitTestInfo> unitTestInfos =new ArrayList<>() ;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public long getCountBooks() {
        return countBooks;
    }

    public void setCountBooks(long countBooks) {
        this.countBooks = countBooks;
    }

    public int getTheLatesdScore() {
        return theLatesdScore;
    }

    public void setTheLatesdScore(int theLatesdScore) {
        this.theLatesdScore = theLatesdScore;
    }

    public String getCurrentBookName() {
        return currentBookName;
    }

    public void setCurrentBookName(String currentBookName) {
        this.currentBookName = currentBookName;
    }

    public String getCurrentWord() {
        return currentWord;
    }

    public void setCurrentWord(String currentWord) {
        this.currentWord = currentWord;
    }

    public Map<Long, String> getBooks() {
        return books;
    }

    public void setBooks(Map<Long, String> books) {
        this.books = books;
    }

    public List<BookTestInfo> getBookTestInfos() {
        return bookTestInfos;
    }

    public void setBookTestInfos(List<BookTestInfo> bookTestInfos) {
        this.bookTestInfos = bookTestInfos;
    }

    public List<UnitTestInfo> getUnitTestInfos() {
        return unitTestInfos;
    }

    public void setUnitTestInfos(List<UnitTestInfo> unitTestInfos) {
        this.unitTestInfos = unitTestInfos;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getCurrentWordName() {
        return currentWordName;
    }

    public void setCurrentWordName(String currentWordName) {
        this.currentWordName = currentWordName;
    }

    public String getCurrentUnit() {
        return currentUnit;
    }

    public void setCurrentUnit(String currentUnit) {
        this.currentUnit = currentUnit;
    }

    public static class  UnitTestInfo{
        long  unitid ;
        String  unitName ;
        int isPre;
        int  score ;
        Date testtime ;

        public long getUnitid() {
            return unitid;
        }

        public void setUnitid(long unitid) {
            this.unitid = unitid;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public int getIsPre() {
            return isPre;
        }

        public void setIsPre(int isPre) {
            this.isPre = isPre;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public Date getTesttime() {
            return testtime;
        }

        public void setTesttime(Date testtime) {
            this.testtime = testtime;
        }
    }


    public static class BookTestInfo{

        long bookId ;
        String  bookName ;
        int isPre ;
        int score ;
        Date testDate ;

        public long getBookId() {
            return bookId;
        }

        public void setBookId(long bookId) {
            this.bookId = bookId;
        }

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        public int getIsPre() {
            return isPre;
        }

        public void setIsPre(int isPre) {
            this.isPre = isPre;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public Date getTestDate() {
            return testDate;
        }

        public void setTestDate(Date testDate) {
            this.testDate = testDate;
        }
    }
}
