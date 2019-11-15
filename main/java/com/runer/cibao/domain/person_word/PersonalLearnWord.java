package com.runer.cibao.domain.person_word;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.runer.cibao.base.BaseBean;
import com.runer.cibao.domain.AppUser;
import com.runer.cibao.domain.BookWord;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/16
 *
 *
 * 几个重要的阶段 ======  1。未学习，提取出来进行认知---
 *                      2。认知成功-》
 *                      3。强化成功-》    ---不再是生词--
 *                      4。效果检测-》 -> a,生词本  b,变为生词了
 *                      //复习；
 *                      5。  a:语音学习 b:听音辨意   c智能听写  d句子填空  e句式练习
 *
 **/
@Entity
@Table(name = "personal_learn_word")
public class PersonalLearnWord extends BaseBean {

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "personalLearnWord")
    private Set<WordLearn> wordLearns =new HashSet<>() ;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    AppUser appUser ;


    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "word_id")
    BookWord bookWord ;

    Integer currentSate;

    Long currentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public BookWord getBookWord() {
        return bookWord;
    }

    public void setBookWord(BookWord bookWord) {
        this.bookWord = bookWord;
    }

    public Integer getCurrentSate() {
        return currentSate;
    }

    public void setCurrentSate(Integer currentSate) {
        this.currentSate = currentSate;
    }

    public Long getCurrentId() {
        return currentId;
    }

    public void setCurrentId(Long currentId) {
        this.currentId = currentId;
    }

    public Set<WordLearn> getWordLearns() {
        return wordLearns;
    }

    public void setWordLearns(Set<WordLearn> wordLearns) {
        this.wordLearns = wordLearns;
    }
}
