package com.runer.cibao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.runer.cibao.base.BaseBean;
import com.runer.cibao.domain.person_word.PersonalLearnWord;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/11
 * 数据库中的课本文本；
 **/
@Entity
@Table(name = "bookWord")
public class BookWord  extends BaseBean {



    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "bookWord")
    Set<PersonalLearnWord> personalLearnWords =new HashSet<>() ;


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "bookWord")
    private List<ErrorRecovery> errorRecoveries =new ArrayList<>() ;



    @JsonIgnore
    @OneToMany(fetch =FetchType.LAZY,mappedBy = "bookWord")
    private List<NewBookWord> newBookWords =new ArrayList<>() ;



    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    BookUnit unit;

    @NotFound(action = NotFoundAction.IGNORE)
            @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "book_id")
    LearnBook learnBook ;


    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id",nullable = true)
    Word word;

    /**
     * 序号	阶段	版本	课本名称	单元名称	单词	释义	英文例句1	例句翻译1	英文例句2	例句翻译2	助记法	词根词缀	相关词	备用1	备用2
     * 1	初中	人教版	初一上	unit1	apple	n.苹果	I like eating apple.	我喜欢吃苹果	We have mang [apples].	我们有很多苹果	app+le（乐）我们玩游戏APP很快乐！		app：apply：
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;

    String  stage;

    String version ;

    String bookName ;

    String unitName ;

    private String wordName ;
    /*释义*/
    String interpretation ;

    String englishExample1 ;

    String exampleTranslation1 ;


    String englishExample2 ;

    String exampleTranslation2 ;
    /**
     * 助记法
     */
    String assistantNotation ;
    /**
     * 词根词缀
     */
    String rootAffixes ;
    String aboutWords ;
    /**
     * 备用1
     */
    String spare1;
    /**
     * 备用2
     */
    String spare2 ;

    int unitDes = Integer.MAX_VALUE;





    @Transient
    @JsonProperty("is_right")
    boolean isRight  =true;



    public BookWord() {
    }


    public BookWord(BookUnit unit, Word word, String stage, String version, String bookName,
                    String unitName, String wordName, String interpretation, String englishExample1,
                    String exampleTranslation1, String englishExample2, String exampleTranslation2,
                    String assistantNotation, String rootAffixes, String aboutWords, String spare1, String spare2) {
        this.unit = unit;
        this.word = word;
        this.stage = stage;
        this.version = version;
        this.bookName = bookName;
        this.unitName = unitName;
        this.wordName = wordName;
        this.interpretation = interpretation;
        this.englishExample1 = englishExample1;
        this.exampleTranslation1 = exampleTranslation1;
        this.englishExample2 = englishExample2;
        this.exampleTranslation2 = exampleTranslation2;
        this.assistantNotation = assistantNotation;
        this.rootAffixes = rootAffixes;
        this.aboutWords = aboutWords;
        this.spare1 = spare1;
        this.spare2 = spare2;
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


    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public BookUnit getUnit() {
        return unit;
    }

    public void setUnit(BookUnit unit) {
        this.unit = unit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }


    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public List<NewBookWord> getNewBookWords() {
        return newBookWords;
    }

    public void setNewBookWords(List<NewBookWord> newBookWords) {
        this.newBookWords = newBookWords;
    }

    public List<ErrorRecovery> getErrorRecoveries() {
        return errorRecoveries;
    }

    public void setErrorRecoveries(List<ErrorRecovery> errorRecoveries) {
        this.errorRecoveries = errorRecoveries;
    }

    public Set<PersonalLearnWord> getPersonalLearnWords() {
        return personalLearnWords;
    }

    public void setPersonalLearnWords(Set<PersonalLearnWord> personalLearnWords) {
        this.personalLearnWords = personalLearnWords;
    }

    public LearnBook getLearnBook() {
        return learnBook;
    }

    public void setLearnBook(LearnBook learnBook) {
        this.learnBook = learnBook;
    }

    public void setUnitDes(int unitDes) {
        this.unitDes = unitDes;
    }

     public JsonWordBean toJsonBean(long bookId){
         JsonWordBean bean =new JsonWordBean() ;
         bean.setId(getId());
         bean.setWord(getWordName());
         if (getWord()!=null) {
             bean.setPhonetic_symbol(getWord().getPhonetic_symbol());
             bean.setAmericanPronunciation(getWord().getAmericanPronunciation());
             bean.setEnglishPronunciation(getWord().getEnglishPronunciation());
             bean.setSyllabification(getWord().getSyllabification());
             bean.setWordAudioUrl(getWord().getWordAudioUrl());
             bean.setUsaAudioUrl(getWord().getUsaAudioUrl());
             bean.setInterpretation(getWord().getInterpretation());
             bean.setEnglishExample1(getWord().getEnglishExample1());
             bean.setEnglishExample2(getWord().getEnglishExample2());
             bean.setExampleTranslation1(getWord().getExampleTranslation1());
             bean.setExampleTranslation2(getWord().getExampleTranslation2());
             bean.setRootAffixes(getWord().getRootAffixes());
             bean.setAboutWords(getWord().getAboutWords());
             bean.setSpare1(getWord().getSpare1());
             bean.setSpare2(getWord().getSpare2());
             bean.setWordName(getWord().getWord());
             bean.setAssistantNotation(getWord().getAssistantNotation());
         }else{

         }
         bean.setStage(getStage());
         bean.setVersion(getVersion());
         bean.setBookName(getBookName());
         bean.setUnitName(getUnitName());
         bean.setWordName(getWordName());
         bean.setBookId(bookId);

         if (getLearnBook()!=null){
             bean.setBookId(getLearnBook().getId());
             bean.setBookName(getLearnBook().getBookName());
         }
         if (getUnit()!=null) {
             bean.setUnitId(getUnit().getId());
             bean.setUnitName(getUnit().getName());
         }

         return  bean ;
     }


}



