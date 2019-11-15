package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.runer.cibao.base.BaseBean;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/12
 *
 * 总词库的单词--／／todo
 **/
@Entity
@Table(name = "word")
public class Word  extends BaseBean {

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    List<BookWord> wordList = new ArrayList<>() ;
    /**
     * 序号	单词	音标	美式发音	英式发音	释义	英文例句1	例句翻译1	英文例句2	例句翻译2	助记法	词根词缀	相关词	备用1	备用2
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    @Column(unique = true)
    String word ;

    String phonetic_symbol ;

    String americanPronunciation ;

    String englishPronunciation ;

    /**
     * 单词划分'
     */
    String syllabification ;

    /*释义*/
    @Column(length = 300)
    String interpretation ;

    @Column(length = 300)
    String englishExample1 ;

    @Column(length = 300)
    String exampleTranslation1 ;

    @Column(length = 300)
    String englishExample2 ;

    @Column(length = 300)
    String exampleTranslation2 ;

    /**
     * 助记法
     */
    @Column(length = 300)
    String assistantNotation ;

    /**
     * 词根词缀
     */
    @Column(length = 300)
    String rootAffixes ;
    @Column(length = 300)
    String aboutWords ;
    /**
     * 备用1
     */
    @Column(length = 300)
    String spare1;
    /**
     * 备用2
     */
    @Column(length = 300)
    String spare2 ;

    /**
     * wordAudio 英式音频文件的路径
     */
    String wordAudioUrl;

    /**
     * 美式音频文件的路径
     */
    String usaAudioUrl;

    @Transient
    int audioState ;

    public int getAudioState() {
        return audioState;
    }

    public void setAudioState(int audioState) {
        this.audioState = audioState;
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


    public List<BookWord> getWordList() {
        return wordList;
    }

    public void setWordList(List<BookWord> wordList) {
        this.wordList = wordList;
    }


    public String getWordAudioUrl() {
        return wordAudioUrl;
    }

    public void setWordAudioUrl(String wordAudioUrl) {
        this.wordAudioUrl = wordAudioUrl;
    }

    public String getSyllabification() {
        return syllabification;
    }

    public void setSyllabification(String syllabification) {
        this.syllabification = syllabification;
    }

    public String getUsaAudioUrl() {
        return usaAudioUrl;
    }

    public void setUsaAudioUrl(String usaAudioUrl) {
        this.usaAudioUrl = usaAudioUrl;
    }


    @Override
    public String toString() {
        return "Word{" +
                "wordList=" + wordList +
                ", id=" + id +
                ", word='" + word + '\'' +
                ", phonetic_symbol='" + phonetic_symbol + '\'' +
                ", americanPronunciation='" + americanPronunciation + '\'' +
                ", englishPronunciation='" + englishPronunciation + '\'' +
                ", syllabification='" + syllabification + '\'' +
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
                ", wordAudioUrl='" + wordAudioUrl + '\'' +
                ", usaAudioUrl='" + usaAudioUrl + '\'' +
                ", audioState=" + audioState +
                '}';
    }
}
