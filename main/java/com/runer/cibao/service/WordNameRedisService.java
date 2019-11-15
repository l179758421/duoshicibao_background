package com.runer.cibao.service;

import com.runer.cibao.domain.BookWord;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/14
 **/
public interface WordNameRedisService {

    boolean checkWordExist(BookWord word) ;

    void deleteWordName(BookWord word) ;

    String getWordKey(BookWord bookWord);

    void saveAllWord(List<BookWord> bookWordList) ;

    void saveOneWord(BookWord bookWord) ;

    long getWordId(BookWord bookWord) ;
}
