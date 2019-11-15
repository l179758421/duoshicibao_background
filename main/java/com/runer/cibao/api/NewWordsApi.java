package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.PageApiResult;
import com.runer.cibao.domain.NewBookWord;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.NewBookWordService;
import com.runer.cibao.util.NormalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/29
 **/


@RestController
@RequestMapping(value = "api/newWordsApi")
@Api(value = "生词本",description = "生词本")
public class NewWordsApi {

    @Autowired
    NewBookWordService newBookWordService ;

    @ApiOperation(value = "获得当前的生词个数",notes = "获得当前的生词个数")
    @RequestMapping(value = "getNewWordNum",method = RequestMethod.POST)
    public ApiResult  getNewWordNum(Long userId ){
         long newWordCount =  newBookWordService.countOnePersonalNewWord(userId) ;
         return  new ApiResult(ResultMsg.SUCCESS,newWordCount);
    }

    @ApiOperation(value = "添加生词",notes = "添加生词")
    @RequestMapping(value = "addNewWord",method = RequestMethod.POST)
    public ApiResult  addNewWord(Long userId ,Long wordId){
     return  newBookWordService.addNewBookWords(userId,wordId) ;
    }


    @ApiOperation(value = "获得生词表",notes = "获得生词表")
    @RequestMapping(value = "findNewWords",method = RequestMethod.POST)
    public ApiResult findNewWords(Long userId){
        if (userId==null){
            return  new ApiResult(ResultMsg.USER_ID_IS_NOT_ALLOWED_NULL,null) ;
        }
        return  newBookWordService.findUserNewWordsNew(userId) ;
    }

    @ApiOperation(value = "获得生词表的历史",notes = "获得生词表的历史")
    @RequestMapping(value = "getNewWordsHistory",method = RequestMethod.POST)
    public PageApiResult  getNewWordsHistory(Long userId ,Integer page ,Integer limit){
        if (userId==null){
            return  new PageApiResult("用户id不能为 i 空");
        }

        Page<NewBookWord> pages = newBookWordService.findUserAllNewWordsHistory(userId, page, limit);
        return  NormalUtil.createPageResult(pages) ;
    }

    @ApiOperation(value = "设置当前的单词为巩固的单词",notes = "设置当前的单词为巩固的单词")
    @RequestMapping(value = "setNowToHistory",method = RequestMethod.POST)
    public ApiResult  setNowToHistory(Long newWordId){
        return newBookWordService.setOneWordConsolidated(newWordId);
    }

    @ApiOperation(value = "删除",notes = "删除")
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public ApiResult  delete(Long newWordId){
        return newBookWordService.deleteNewWords(newWordId);
    }

    @ApiOperation(value = "单词是否加入生词库",notes = "单词是否加入生词库")
    @RequestMapping(value = "isAddToWords",method = RequestMethod.POST)
    public ApiResult isAddToWords(Long userId,Long bookWordId){
        return newBookWordService.isAdd(userId,bookWordId);
    }

    @ApiOperation(value = "根据用户id删除生词库单词",notes = "根据用户id删除生词库单词")
    @RequestMapping(value = "deleteNewWord",method = RequestMethod.POST)
    public ApiResult deleteNewWord(Long userId,Long bookWordId){
        return newBookWordService.deleteNewWord(userId,bookWordId);
    }


}
