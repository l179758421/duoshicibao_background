package com.runer.cibao.service;

import com.runer.cibao.domain.WordsAudio;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/31
 **/
public interface WordAudioRedisService  {

    boolean checkWordExist(WordsAudio word) ;

    void deleteWordName(WordsAudio word) ;


    void saveAllWord(List<WordsAudio> bookWordList) ;

}
