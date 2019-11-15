package com.runer.cibao.domain;

import java.io.Serializable;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/9/7
 **/
public class JsonWordBean implements Serializable {

    private Long id;
    private String word;
    private String phonetic_symbol;
    private String americanPronunciation;
    private String englishPronunciation;
    private String syllabification;
    private String interpretation;
    private String englishExample1;
    private String exampleTranslation1;
    private String englishExample2;
    private String exampleTranslation2;
    private String assistantNotation;
    private String rootAffixes;
    private String aboutWords;
    private String spare1;
    private String spare2;
    //课本id
    private Long bookId;

    //单元id
    private Long unitId;

    private String wordAudioUrl;
    private String usaAudioUrl;
    //标记（显示测试的结果的）
    private int state;

    private String stage;
    private String version;
    private String bookName;
    private String unitName;
    private String wordName;
    //标记单词的学习进度(0:没有学习,1:已经学习)


    public String getUsaAudioUrl() {
        return usaAudioUrl;
    }

    public void setUsaAudioUrl(String usaAudioUrl) {
        this.usaAudioUrl = usaAudioUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSyllabification() {
        return syllabification;
    }

    public void setSyllabification(String syllabification) {
        this.syllabification = syllabification;
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

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getWordAudioUrl() {
        return wordAudioUrl;
    }

    public void setWordAudioUrl(String wordAudioUrl) {
        this.wordAudioUrl = wordAudioUrl;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }
}
