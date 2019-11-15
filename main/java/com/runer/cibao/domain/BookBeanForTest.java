package com.runer.cibao.domain;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/28
 **/
public class BookBeanForTest {


    PersonalLearnBook personalLearnBook ;

    int currentScore ;

    int preLearnScore ;

    String bookName ;

    String imgUrl ;



    public PersonalLearnBook getPersonalLearnBook() {
        return personalLearnBook;
    }

    public void setPersonalLearnBook(PersonalLearnBook personalLearnBook) {
        this.personalLearnBook = personalLearnBook;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getPreLearnScore() {
        return preLearnScore;
    }

    public void setPreLearnScore(int preLearnScore) {
        this.preLearnScore = preLearnScore;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
