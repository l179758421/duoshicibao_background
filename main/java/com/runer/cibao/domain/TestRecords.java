package com.runer.cibao.domain;

import com.runer.cibao.base.BaseBean;

import javax.persistence.*;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/19
 *
 * 课本测试 含有三种题型
 *
 * 一 听音选择英语单词
 * 二 看汉语选择英语
 * 三 看英语选择汉语
 **/
@Entity
@Table(name = "testRecord")
public class TestRecords extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;

    String optionA ;
    String optionB ;
    String optionC ;
    String optionD ;


    /**
     *
     * 1 英-选汉语 2 汉-选英语 3 读音
     */
    Integer type ;

    String title ;

    String rightAnswer;

    String selectItem ;

    String word;

    /**
     */
    String phoneticSymbol;

    Long wordId ;

    String  createTime ;
    /**
     * 课本测试的id；
     */
    Long bookTestId;
    /**
     * 单元测试的ID；
     */
    Long unitTestId ;

    String bookId ;

    /**
     * 测试的类型   -------------' 课本 1 ，单元测试 2 ， 学前测试 3 ，
     */
    Integer testType ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getBookTestId() {
        return bookTestId;
    }

    public void setBookTestId(Long bookTestId) {
        this.bookTestId = bookTestId;
    }

    public Long getUnitTestId() {
        return unitTestId;
    }

    public void setUnitTestId(Long unitTestId) {
        this.unitTestId = unitTestId;
    }

    public Integer getTestType() {
        return testType;
    }

    public void setTestType(Integer testType) {
        this.testType = testType;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getPhoneticSymbol() {
        return phoneticSymbol;
    }

    public void setPhoneticSymbol(String phoneticSymbol) {
        this.phoneticSymbol = phoneticSymbol;
    }

    public String getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(String selectItem) {
        this.selectItem = selectItem;
    }

}
