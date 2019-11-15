package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.BookWord;
import com.runer.cibao.domain.WordFavorite;
import com.runer.cibao.domain.repository.WordFavoriteRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.BookWordService;
import com.runer.cibao.service.WordFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WordFavoriteServiceImpl extends BaseServiceImp<WordFavorite,WordFavoriteRepository> implements WordFavoriteService {
    @Autowired
    BookWordService bookWordService;

    @Override
    public ApiResult addWordFavor(Long userId, Long bookWordId) {
        WordFavorite wordFavorite=new WordFavorite();
          WordFavorite wordFavorite1 = r.findByUserIdAndAndWordId(userId,bookWordId);
          if(wordFavorite1 != null){
              return new ApiResult("已收藏过");
          }
        wordFavorite.setUserId(userId);
        wordFavorite.setCreatTime(new Date());
        wordFavorite.setWordId(bookWordId);
        try {
            BookWord bookWord =bookWordService.findById(bookWordId);
            wordFavorite.setWord(bookWord.getWordName());
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
         r.saveAndFlush(wordFavorite);

        return new ApiResult(ResultMsg.SUCCESS,wordFavorite);
    }

    @Override
    public ApiResult findByUserId(Long userId) {
        List<WordFavorite> favoriteList = r.findByUserId(userId);
        List<String> list=new ArrayList<>();
        for (WordFavorite wordFavorite : favoriteList) {
            list.add(wordFavorite.getWord());
        }
        return new ApiResult(ResultMsg.SUCCESS,list);
    }

    @Override
    public ApiResult deleteWord(Long userId, Long wordId) {
          WordFavorite word = r.findByUserIdAndAndWordId(userId,wordId);
          if(word != null){
              r.delete(word);
              return new ApiResult(ResultMsg.SUCCESS,word);
          }else{
            return  new ApiResult("没有收藏此单词");
          }
    }

    @Override
    public ApiResult isFav(Long userId, Long wordId) {
        Map<String,Integer> map=new HashMap<>();
        WordFavorite word = r.findByUserIdAndAndWordId(userId,wordId);
        if(word !=null){
            map.put("isFav",1);
        }else{
            map.put("isFav",0);
        }
        return new ApiResult(ResultMsg.SUCCESS,map);
    }
}
