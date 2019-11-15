package com.runer.cibao.domain;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public  class ExcelPropertyIndexModel extends BaseRowModel {

    /*序号	单词	音标	美式发音	英式发音	释义	英文例句1	例句翻译1	英文例句2	例句翻译2	助记法	词根词缀	相关词	备用1	备用2*/
    @ExcelProperty(value = "序号", index = 0)
    private String orderNum;

    @ExcelProperty(value="单词划分",index = 1)
    private String syllabification;

    @ExcelProperty(value="单词",index = 2)
    private String word ;

    @ExcelProperty(value="音标",index = 3 )
    private String phonetic_symbol ;

    @ExcelProperty(value = "美式发音",index = 4)
    private String americanPronunciation ;

    @ExcelProperty(value="英式发音",index = 5)
    private String englishPronunciation ;

    /*释义*/
    @ExcelProperty(value="释义",index = 6)
    private String interpretation ;

    @ExcelProperty(value="英文例句1",index = 7)
    private String englishExample1 ;

    @ExcelProperty(value = "例句翻译1",index = 8)
    private String exampleTranslation1 ;

    @ExcelProperty(value="英文例句2",index = 9)
    private String englishExample2 ;

    @ExcelProperty(value = "例句翻译2",index = 10)
    private String exampleTranslation2 ;

    /**
     * 助记法
     */
    @ExcelProperty(value = "助记法",index = 11)
    private String assistantNotation ;

    /**
     * 词根词缀
     */
    @ExcelProperty(value = "词根词缀",index = 12)
    private String rootAffixes ;

    @ExcelProperty(value="相关词",index = 13)
    private  String aboutWords ;
    /**
     * 备用1
     */
    @ExcelProperty(value="备用1",index = 14)
    private  String spare1;
    /**
     * 备用2
     */
    @ExcelProperty(value="备用2",index = 15)
    private String spare2 ;

    @ExcelProperty(value="音频缺失",index = 16)
    private String audioUrl;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getSyllabification() {
        return syllabification;
    }

    public void setSyllabification(String syllabification) {
        this.syllabification = syllabification;
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

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    @Override
    public String toString() {
        return "ExcelPropertyIndexModel{" +
                "orderNum='" + orderNum + '\'' +
                ", syllabification='" + syllabification + '\'' +
                ", word='" + word + '\'' +
                ", phonetic_symbol='" + phonetic_symbol + '\'' +
                ", americanPronunciation='" + americanPronunciation + '\'' +
                ", englishPronunciation='" + englishPronunciation + '\'' +
                ", interpretation='" + interpretation + '\'' +
                ", englishExample1='" + englishExample1 + '\'' +
                ", exampleTranslation1='" + exampleTranslation1 + '\'' +
                ", englishExample2='" + englishExample2 + '\'' +
                ", exampleTranslation2='" + exampleTranslation2 + '\'' +
                ", assistantNotation='" + assistantNotation + '\'' +
                ", rootAffixes='" + rootAffixes + '\'' +
                ", aboutWords='" + aboutWords + '\'' +
                ", spare1='" + spare1 + '\'' +
                ", spare2='" + spare2 + '\'' +
                ", audioUrl='" + audioUrl + '\'' +
                '}';
    }
}
