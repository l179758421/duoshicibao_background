package com.runer.cibao.domain;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/27
 *
 * 课本的通用列表  搜索的显示；
 **/
public class BookBeanForApi {

    LearnBook learnBook ;

    boolean isActive ;

    Long currentWord ;

    Long totalWord ;

    Long personalBookId ;

    Integer activePrice ;

    String imgUrl ;

    int order ;

    boolean isBuy ;

    int currentNum;

    public boolean isBuy() {
        return isBuy;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
    }

    public LearnBook getLearnBook() {
        return learnBook;
    }

    public void setLearnBook(LearnBook learnBook) {
        this.learnBook = learnBook;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getCurrentWord() {
        return currentWord;
    }

    public void setCurrentWord(Long currentWord) {
        this.currentWord = currentWord;
    }

    public Long getPersonalBookId() {
        return personalBookId;
    }

    public void setPersonalBookId(Long personalBookId) {
        this.personalBookId = personalBookId;
    }



    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getActivePrice() {
        return activePrice;
    }

    public void setActivePrice(Integer activePrice) {
        this.activePrice = activePrice;
    }

    public Long getTotalWord() {
        return totalWord;
    }

    public void setTotalWord(Long totalWord) {
        this.totalWord = totalWord;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
