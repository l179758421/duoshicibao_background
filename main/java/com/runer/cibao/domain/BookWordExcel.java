package com.runer.cibao.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/15
 *
 *
 * 课本的文本excel类
 *
 **/
public class BookWordExcel {


    /*序号	阶段	版本	课本名称	单元名称	单词	释义	英文例句1	例句翻译1	英文例句2	例句翻译2	助记法	词根词缀	相关词	备用1	备用2
1	初中	人教版	初一上	unit1	apple	n.苹果	I like eating apple.	我喜欢吃苹果	We have mang [apples].	我们有很多苹果	app+le（乐）我们玩游戏APP很快乐！		app：apply：		*/

    /*序号	单词	音标	美式发音	英式发音	释义	英文例句1	例句翻译1	英文例句2	例句翻译2	助记法	词根词缀	相关词	备用1	备用2*/
    @Excel(name = "序号", orderNum = "0")
    private String orderNum;

    @Excel(name = "阶段",orderNum = "1")
    private String stage ;

    @Excel(name="版本",orderNum = "2")
    private String verison ;

    @Excel(name = "课本名称",orderNum = "3")
    private String bookName ;

    @Excel(name="单元名称",orderNum = "4")
    private String unitName ;

    @Excel(name="单词",orderNum = "5")
    private String word ;

    /*释义*/
    @Excel(name="释义",orderNum = "6")
    String interpretation ;


    @Excel(name="英文例句1",orderNum = "7")
    String englishExample1 ;

    @Excel(name = "例句翻译1",orderNum = "8")
    String exampleTranslation1 ;


    @Excel(name="英文例句2",orderNum = "9")
    String englishExample2 ;

    @Excel(name = "例句翻译2",orderNum = "10")
    String exampleTranslation2 ;


    /**
     * 助记法
     */
    @Excel(name = "助记法",orderNum = "11")
    String assistantNotation ;

    /**
     * 词根词缀
     */
    @Excel(name = "词根词缀",orderNum = "12")
    String rootAffixes ;



    @Excel(name="相关词",orderNum = "13")
    String aboutWords ;
    /**
     * 备用1
     */
    @Excel(name="备用1",orderNum = "14")
    String spare1;
    /**
     * 备用2
     */
    @Excel(name="备用2",orderNum = "15")
    String spare2 ;


//    @Excel(name="音标",orderNum = "6")
//    private String phonetic_symbol ;
//
//    @Excel(name = "美式发音",orderNum = "7")
//    private String americanPronunciation ;
//
//    @Excel(name="英式发音",orderNum = "8")
//    private String englishPronunciation ;


    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getVerison() {
        return verison;
    }

    public void setVerison(String verison) {
        this.verison = verison;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getInterpretation() {
        return interpretation;
    }

    public void setInterpretation(String interpretation) {
        this.interpretation = interpretation;
    }

    public String getEnglishExample1() {
        return englishExample1;
    }

    public void setEnglishExample1(String englishExample1) {
        this.englishExample1 = englishExample1;
    }

    public String getExampleTranslation1() {
        return exampleTranslation1;
    }

    public void setExampleTranslation1(String exampleTranslation1) {
        this.exampleTranslation1 = exampleTranslation1;
    }

    public String getEnglishExample2() {
        return englishExample2;
    }

    public void setEnglishExample2(String englishExample2) {
        this.englishExample2 = englishExample2;
    }

    public String getExampleTranslation2() {
        return exampleTranslation2;
    }

    public void setExampleTranslation2(String exampleTranslation2) {
        this.exampleTranslation2 = exampleTranslation2;
    }

    public String getAssistantNotation() {
        return assistantNotation;
    }

    public void setAssistantNotation(String assistantNotation) {
        this.assistantNotation = assistantNotation;
    }

    public String getRootAffixes() {
        return rootAffixes;
    }

    public void setRootAffixes(String rootAffixes) {
        this.rootAffixes = rootAffixes;
    }

    public String getAboutWords() {
        return aboutWords;
    }

    public void setAboutWords(String aboutWords) {
        this.aboutWords = aboutWords;
    }

    public String getSpare1() {
        return spare1;
    }

    public void setSpare1(String spare1) {
        this.spare1 = spare1;
    }

    public String getSpare2() {
        return spare2;
    }

    public void setSpare2(String spare2) {
        this.spare2 = spare2;
    }



}
