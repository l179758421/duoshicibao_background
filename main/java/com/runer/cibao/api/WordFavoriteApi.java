package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.service.WordFavoriteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "单词收藏相关的Api",description = "单词收藏相关的Api")
@RequestMapping(value = "api/wordFavor")
public class WordFavoriteApi {

    @Autowired
    WordFavoriteService wordFavoriteService;

    @ApiOperation(value = "单词收藏",notes = "收藏单词")
    @RequestMapping(value = "addWordFavor",method = RequestMethod.POST)
    public ApiResult addWordFavor(Long userId, Long wordId){
        return wordFavoriteService.addWordFavor(userId,wordId);
    }

    @ApiOperation(value = "查询收藏的单词",notes = "查询用户收藏的单词")
    @RequestMapping(value = "getWordFavor",method = RequestMethod.POST)
    public ApiResult getWordFavor(Long userId){
        return wordFavoriteService.findByUserId(userId);
    }

    @ApiOperation(value = "移除收藏的单词",notes = "移除用户收藏的单词")
    @RequestMapping(value = "delWordFavor",method = RequestMethod.POST)
    public ApiResult delWordFavor(Long userId,Long wordId){
        return wordFavoriteService.deleteWord(userId,wordId);
    }

    @ApiOperation(value = "单词是否收藏",notes = "单词是否收藏")
    @RequestMapping(value = "isFav",method = RequestMethod.POST)
    public ApiResult isFav(Long userId,Long wordId){
        return wordFavoriteService.isFav(userId,wordId);
    }



}
