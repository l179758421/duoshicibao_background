package com.runer.cibao.service.impl;

import com.runer.cibao.base.IRedisService;
import com.runer.cibao.domain.WordsAudio;
import com.runer.cibao.service.WordAudioRedisService;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/31
 **/
@Service
public class WordAudioRedisServiceImpl  extends IRedisService implements WordAudioRedisService {
    @Override
    protected String getRedisKey() {
        return WordsAudio.class.getName();
    }


    @Override
    public boolean checkWordExist(WordsAudio word) {
       return isKeyExists(word.getName());
    }

    @Override
    public void deleteWordName(WordsAudio word) {
        remove(word.getName());
    }



    @Override
    public void saveAllWord(List<WordsAudio> bookWordList) {
        if (!ListUtils.isEmpty(bookWordList)){
            for (WordsAudio wordsAudio : bookWordList) {
                //保存一天；
                put(wordsAudio.getName(),wordsAudio.getName(),24*60*60);
            }
        }
    }
}
