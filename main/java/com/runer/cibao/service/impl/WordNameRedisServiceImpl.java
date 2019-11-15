package com.runer.cibao.service.impl;

import com.runer.cibao.base.IRedisService;
import com.runer.cibao.domain.BookWord;
import com.runer.cibao.service.WordNameRedisService;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/14
 **/
@Service
public class WordNameRedisServiceImpl  extends IRedisService implements WordNameRedisService {

    @Override
    protected String getRedisKey() {
        return BookWord.class.getName();
    }


    @Override
    public boolean checkWordExist(BookWord word) {
        return isKeyExists(getWordKey(word));
    }

    @Override
    public void deleteWordName(BookWord word){
         remove(getWordKey(word));
    }

    @Override
    public String getWordKey(BookWord bookWord) {
        String wordName =bookWord.getWordName() ;
        String book ="";
        if (bookWord.getLearnBook()!=null){
            book= String.valueOf(bookWord.getLearnBook().getId());
        }
        return wordName+","+book;
    }


    @Override
    public void saveAllWord(List<BookWord> bookWordList) {
       if (!ListUtils.isEmpty(bookWordList)){
           for (BookWord bookWord : bookWordList){
               put(getWordKey(bookWord),bookWord.getId(),-1);
           }
       }
    }

    @Override
    public void saveOneWord(BookWord bookWord){
        put(getWordKey(bookWord),bookWord.getId(),-1);
    }

    @Override
    public long getWordId(BookWord bookWord) {
        Object reuslt = get(getWordKey(bookWord));
        if (reuslt!=null){
             return (long) reuslt;
        }
        return  0 ;
    }

}
