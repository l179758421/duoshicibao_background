package com.runer.cibao.service.impl;

import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.NewBookWordDao;
import com.runer.cibao.domain.AppUser;
import com.runer.cibao.domain.BookWord;
import com.runer.cibao.domain.NewBookWord;
import com.runer.cibao.domain.repository.NewBookWordRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.AppUserService;
import com.runer.cibao.service.BookWordService;
import com.runer.cibao.service.NewBookWordService;
import com.runer.cibao.util.machine.IdsMachine;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;
import org.thymeleaf.util.StringUtils;

import java.util.*;

import static com.runer.cibao.exception.ResultMsg.*;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/29
 **/
@Service
public class NewBookWordServiceImpl extends BaseServiceImp<NewBookWord, NewBookWordRepository> implements NewBookWordService {

    @Autowired
    NewBookWordDao newBookWordDao ;

    @Autowired
    AppUserService appUserService ;

    @Autowired
    BookWordService bookWordService ;

    @Autowired
    IdsMachine idsMachine ;

    @Override
    public long countOnePersonalNewWord(Long userId) {
        return newBookWordDao.countNewBookWords(userId, Config.IS_NOW_WORDS);
    }

    @Override
    public ApiResult addNewBookWords(Long userId, Long bookWordId) {

        ApiResult userResult =appUserService.findByIdWithApiResult(userId);
        if (userResult.getMsgCode()!=SUCCESS.getMsgCode()){
            return  userResult ;
        }
        AppUser appUser = (AppUser) userResult.getData();

        ApiResult wordResult =bookWordService.findByIdWithApiResult(bookWordId);
       if (wordResult.isFailed()){
             return  wordResult ; }

       BookWord bookWord = (BookWord) wordResult.getData();

       ApiResult newWordsResult= findUserNewWordsNew(userId);

       List<NewBookWord> newBookWords = (List<NewBookWord>) newWordsResult.getData();
       if (!ListUtils.isEmpty(newBookWords)){
           if (newBookWords.size()>= Config.NEW_WORDS_PERMIT_NUM){
               return  new ApiResult(ResultMsg.NEW_WORDS_LIMIT_OUT,null) ;
           }
           List<NewBookWord> words = findNewWords(1, 10, userId, bookWordId, null).getContent();
           if (!ListUtils.isEmpty(words)){
               r.deleteInBatch(words);
           }
       }

       NewBookWord newBookWord =new NewBookWord() ;
       newBookWord.setAppUser(appUser);
       newBookWord.setBookWord(bookWord);
       newBookWord.setCreateDate(new Date());
       newBookWord.setDeleteState(Config.DELETED_NOT);
       newBookWord.setIsNowWord(Config.IS_NOW_WORDS);
       newBookWord=  r.save(newBookWord) ;
      return new ApiResult( SUCCESS,newBookWord);
    }





    @Override
    @Deprecated
    public ApiResult addNewBookWordsBacth(Long userId, String bookWords) {
        ApiResult userResult =appUserService.findByIdWithApiResult(userId);
        if (userResult.isFailed()){
            return  new ApiResult("用户不存在");
        }
        List<NewBookWord> newBookWords = (List<NewBookWord>) findUserNewWordsNew(userId).getData();
        List<Long> insertIds =idsMachine.deparseIds(bookWords);
        if ((newBookWords.size()+insertIds.size())> Config.NEW_WORDS_PERMIT_NUM){
            return  new ApiResult(NEW_WORDS_LIMIT_OUT,null) ;
        }
          return addNewBookWordsBacthNoLimit(userId,bookWords) ;
    }

    @Override
    public ApiResult addNewBookWordsBacthNoLimit(Long userId, String bookWords) {

        if (StringUtils.isEmpty(bookWords)){
            return  ApiResult.ok();
        }


        ApiResult userResult =appUserService.findByIdWithApiResult(userId);
        if (userResult.isFailed()){
            return  new ApiResult("用户不存在");
        }
        AppUser appUser = (AppUser) userResult.getData();
        //批量的添加；
        List<NewBookWord> words =new ArrayList<>();

        List<Long> insertIds = idsMachine.deparseIds(bookWords);
        idsMachine.removeDuplicate(insertIds);
        try {
            List<BookWord> bookWordsReuslt = bookWordService.findByIds(idsMachine.toIds(insertIds));
            for (BookWord bookWord : bookWordsReuslt) {
                List<NewBookWord> wordsForCheck = findNewWords(1, 10, userId, bookWord.getId(), null).getContent();
                if (!ListUtils.isEmpty(wordsForCheck)){
                    r.deleteInBatch(wordsForCheck);
                }
                NewBookWord newBookWord = new NewBookWord();
                newBookWord.setAppUser(appUser);
                newBookWord.setBookWord(bookWord);
                newBookWord.setCreateDate(new Date());
                newBookWord.setDeleteState(Config.DELETED_NOT);
                newBookWord.setIsNowWord(Config.IS_NOW_WORDS);
                words.add(newBookWord);

            }
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        r.saveAll(words);


        List<NewBookWord> newBookWords = (List<NewBookWord>) findUserNewWordsNew(userId).getData();
        if (newBookWords.size()> Config.NEW_WORDS_PERMIT_NUM){
            return   new ApiResult(SUCCESS,-1);
        }else{
            return  new ApiResult(SUCCESS,1);
        }
    }

    @Override
    public Page<NewBookWord> findNewWords(Integer page, Integer limit, Long userId, Long bookWordId , Integer isNow) {
        return newBookWordDao.findNewbookWords(userId,bookWordId,isNow,PageableUtil.basicPage(page,limit));
    }



    @Override
    public ApiResult findUserNewWordsNew(Long userId) {

        Page<NewBookWord> wordsPage = findNewWords(1, Integer.MAX_VALUE, userId, null , Config.IS_NOW_WORDS);

        return new ApiResult(SUCCESS,wordsPage.getContent());
    }

    @Override
    public Page<NewBookWord> findUserAllNewWordsHistory(Long userId, Integer page, Integer limit) {
        Page<NewBookWord> wordsPage = findNewWords(page, limit, userId,null , Config.NOT_NOW_WORDS);
        List<NewBookWord> list =new ArrayList();
        for(NewBookWord newBookWord:wordsPage.getContent()){
            if(newBookWord.getBookWord() != null){
                newBookWord.setAppUser(null);
                list.add(newBookWord);
            }
        }
        return wordsPage;
    }

    @Override
    public ApiResult deleteNewWords(Long newWordsId) {
        try {
            NewBookWord newBookWord =findById(newWordsId) ;
            /**
             * 判断是否是当前的生词
             */
//            if (newBookWord.getIsNowWord()==Config.IS_NOW_WORDS){
//                return  new ApiResult(NOW_WORD_CAN_NOT_DELETE,null) ;
//            }
            deleteById(newWordsId);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null) ;
        }

        Map<String,String > datas =new HashMap<>();
        datas.put("id", String.valueOf(newWordsId)) ;

        return new ApiResult(SUCCESS,datas);
    }

    @Override
    public ApiResult deleteNewWord(Long userId, Long wordId) {
        List<NewBookWord> words = findNewWords(1, 10, userId, wordId, null).getContent();

        if(ListUtils.isEmpty(words)){
            return new ApiResult("单词不存在");
        }
        NewBookWord newBookWord=words.get(0);
//        if (newBookWord.getIsNowWord()==Config.IS_NOW_WORDS){
//            return  new ApiResult(NOW_WORD_CAN_NOT_DELETE,null) ;
//        }
        try {
            deleteById(newBookWord.getId());
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null) ;
        }

        Map<String,String > datas =new HashMap<>();
        datas.put("id", String.valueOf(wordId)) ;

        return new ApiResult(SUCCESS,datas);
    }

    @Override
    public ApiResult setOneWordConsolidated(Long newWordId) {

        ApiResult wordApiResult = findByIdWithApiResult(newWordId);
        if (wordApiResult.isFailed()){
        return  wordApiResult ;
        }
        NewBookWord newBookWord = (NewBookWord) wordApiResult.getData();

        if (newBookWord.getIsNowWord()== Config.NOT_NOW_WORDS){
            return  new ApiResult( WORD_IS_CONSOLIDATED,null) ;
        }
        newBookWord.setIsNowWord(Config.NOT_NOW_WORDS);
        newBookWord = r.saveAndFlush(newBookWord);
        return new ApiResult(SUCCESS,newBookWord);
    }

//    @Override
//    public ApiResult getOne(Long appUserId, Long wordId) {
//        List<NewBookWord> words = findNewWords(1, 10, appUserId, wordId, null).getContent();
//        if (!ListUtils.isEmpty(words)){
//            return  new ApiResult("单词已存在") ;
//        }
//        return new ApiResult("");
//    }

    @Override
    public ApiResult isAdd(Long appUserId, Long wordId) {
        Boolean isAdd=true;
        List<NewBookWord> words = findNewWords(1, 10, appUserId, wordId, Config.IS_NOW_WORDS).getContent();
        if (ListUtils.isEmpty(words)){
            isAdd=false;
            return  new ApiResult(SUCCESS,isAdd) ;
        }
        return new ApiResult(SUCCESS,isAdd);
    }

}
