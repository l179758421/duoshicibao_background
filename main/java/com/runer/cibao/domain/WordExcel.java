package com.runer.cibao.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/14
 **/
public class WordExcel {

    /*序号	单词	音标	美式发音	英式发音	释义	英文例句1	例句翻译1	英文例句2	例句翻译2	助记法	词根词缀	相关词	备用1	备用2*/
    @Excel(name = "序号", orderNum = "0")
    private String orderNum;

    @Excel(name="单词划分",orderNum = "1")
    private String syllabification;

    @Excel(name="单词",orderNum = "2")
    private String word ;

    @Excel(name="音标",orderNum = "3" )
    private String phonetic_symbol ;

    @Excel(name = "美式发音",orderNum = "4")
    private String americanPronunciation ;

    @Excel(name="英式发音",orderNum = "5")
    private String englishPronunciation ;

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

    @Excel(name="音频缺失",orderNum = "16")
    private String audioUrl;

    public WordExcel() {

    }


    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPhonetic_symbol() {
        return phonetic_symbol;
    }

    public void setPhonetic_symbol(String phonetic_symbol) {
        this.phonetic_symbol = phonetic_symbol;
    }

    public String getAmericanPronunciation() {
        return americanPronunciation;
    }

    public void setAmericanPronunciation(String americanPronunciation) {
        this.americanPronunciation = americanPronunciation;
    }

    public String getEnglishPronunciation() {
        return englishPronunciation;
    }

    public void setEnglishPronunciation(String englishPronunciation) {
        this.englishPronunciation = englishPronunciation;
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

    public String getSyllabification() {
        return syllabification;
    }

    public void setSyllabification(String syllabification) {
        this.syllabification = syllabification;
    }
}
