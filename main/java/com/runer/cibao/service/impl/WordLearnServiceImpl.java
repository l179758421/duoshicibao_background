package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.config.WordLearnState;
import com.runer.cibao.dao.WordLearnDao;
import com.runer.cibao.domain.AppUser;
import com.runer.cibao.domain.BookWord;
import com.runer.cibao.domain.person_word.PersonalLearnWord;
import com.runer.cibao.domain.person_word.WordLearn;
import com.runer.cibao.domain.repository.WordLearnRepostitory;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/17
 **/
@Service
public class WordLearnServiceImpl extends BaseServiceImp<WordLearn,WordLearnRepostitory> implements WordLearnService {

    @Autowired
    WordLearnDao wordLearnDao ;
    @Autowired
    PersonalLearnWordService personalLearnWordService ;
    @Autowired
    AppUserService appUserService ;
    @Autowired
    BookWordService bookWordService ;
    @Autowired
    private PersonalLearnInfoService personalLearnInfoService ;

    @Override
    public Page<WordLearn> findWordLearn(Integer state, List<Integer> states, Date startTime, Date endTime, Long userId, Long bookId, Long unitId, Integer success , Integer page, Integer limit) {
        return wordLearnDao.findWordLearn(state,states,startTime,endTime,userId,bookId,unitId,success,PageableUtil.basicPage(page,limit));
    }
    @Override
    public long countWordLearn(Integer state, List<Integer> states, Date startTime, Date endTime, Long userId, Long bookId, Long unitId ,Integer success) {
        return wordLearnDao.countWordLearn(state,states,startTime,endTime,userId,bookId,unitId,success);
    }
    /**
     *
     * @param appUserId
     * @param wordId
     * @param createDate
     * @param time
     * @param unitId
     * @param state
     * @param isSuccess
     * @return
     * @throws SmartCommunityException
     */
    @Override
    public ApiResult addWordLearn(Long appUserId, Long wordId, Date createDate, Integer time, Long unitId ,Integer state ,Integer isSuccess) throws SmartCommunityException {


         if (state==null){
            state =WordLearnState.learn.getStateCode();
         }
         if (appUserId==null){
            return  new ApiResult(ResultMsg.USER_ID_IS_NOT_ALLOWED_NULL,null) ;
         }
         if (wordId==null){
            return  new ApiResult("单词的id不能够为空");
         }
        /**
         * user的判断
         */
        ApiResult userReuslt = appUserService.findByIdWithApiResult(appUserId);
         if (userReuslt.isFailed()){
             return  userReuslt ;
         }
         //word的判断
         ApiResult boolWordResult  =bookWordService.findByIdWithApiResult(wordId) ;
         if (boolWordResult.isFailed()){
             return  boolWordResult;
         }

         AppUser appUser = (AppUser) userReuslt.getData();
         BookWord word = (BookWord) boolWordResult.getData();


         ApiResult wordResult =  personalLearnWordService.findByWordId(appUserId,wordId) ;
         PersonalLearnWord personalLearnWord = (PersonalLearnWord) wordResult.getData();

         if (personalLearnWord==null){
             personalLearnWord =new PersonalLearnWord() ;
             personalLearnWord.setAppUser(appUser);
             personalLearnWord.setBookWord(word);
             personalLearnWord.setCurrentSate(state);
             personalLearnWord = personalLearnWordService.save(personalLearnWord) ;
         }

         if (createDate==null){
             createDate =new Date() ;
         }
         if (time==null){
             time =0 ;
         }

         WordLearn wordLearn =new WordLearn();
         wordLearn.setLearnDate(createDate);
         wordLearn.setLearnTime(time);
         wordLearn.setIsSuccess(isSuccess);
         wordLearn.setState(state);
         wordLearn.setPersonalLearnWord(personalLearnWord);

         wordLearn = r.save(wordLearn) ;
         personalLearnWord.setCurrentSate(state);
         personalLearnWord.setCurrentId(wordLearn.getId());

         personalLearnWordService.saveOrUpdate(personalLearnWord) ;

         return  new ApiResult(ResultMsg.SUCCESS,wordLearn) ;
    }


    private void insertLists(String datas ,List<Integer> result ,int size ){
        if (StringUtils.isEmpty(datas)){
            for (int i = 0; i < size; i++) {
                result.add(0) ;
            }
        }else{
            for (String s : datas.split(",")) {
                result.add(Integer.valueOf(s));
            }
        }
    }

    /**
     *
     * @param appUserId
     * @param wordIds
     * @param createDate
     * @param times
     * @param unitId
     * @param states
     * @param successes
     * @return
     * @throws SmartCommunityException
     */
    @Override
    public ApiResult addWordLearns(Long appUserId, String wordIds, Date createDate, String times, Long unitId, String states ,String successes)  throws SmartCommunityException {
        if (appUserId==null){
            return  new ApiResult(ResultMsg.USER_ID_IS_NOT_ALLOWED_NULL,null) ;
        }
        if (StringUtils.isEmpty(wordIds)){
            return  new ApiResult("ids为空");
        }
        List<Long> wordId =new ArrayList<>() ;
        for (String s : wordIds.split(",")) {
            wordId.add(Long.valueOf(s));
        }
        //time
        List<Integer> time =new ArrayList<>() ;
        insertLists(times,time,wordId.size());

        //state
        List<Integer> state =new ArrayList<>() ;
        insertLists(states,state,wordId.size());

        //success
        List<Integer> success =new ArrayList<>() ;
        insertLists(successes,success,wordId.size());


        Integer currentState =null;
        if (ListUtils.isEmpty(state)){
            currentState =state.get(0) ;
        }
        if (currentState!=null){

        }





        if (wordId.size()!=time.size()||wordId.size()!=state.size()){
            return  new ApiResult("ids的格式不正确"); }
        if (createDate==null){
            createDate =new Date() ; }
        /**
         * user的判断
         */
        ApiResult userReuslt = appUserService.findByIdWithApiResult(appUserId);
        if (userReuslt.isFailed()){
            return  userReuslt ;
        }
        AppUser appUser = (AppUser) userReuslt.getData();

        for (int i = 0; i < wordId.size(); i++) {

            long id =wordId.get(i) ;

            int learnTime =time.get(i) ;
            int learnState =state.get(i) ;
            int successState =success.get(i);

            //word的判断
            ApiResult boolWordResult  =bookWordService.findByIdWithApiResult(id);

            if (boolWordResult.isFailed()){
                continue;
            }
            BookWord word = (BookWord) boolWordResult.getData();
            unitId = word.getUnit().getId();

            ApiResult wordResult =  personalLearnWordService.findByWordId(appUserId,id);
            PersonalLearnWord personalLearnWord = (PersonalLearnWord) wordResult.getData();
            if (personalLearnWord==null){
                personalLearnWord =new PersonalLearnWord() ;
                personalLearnWord.setAppUser(appUser);
                personalLearnWord.setBookWord(word);
                personalLearnWord.setCurrentSate(learnState);
                personalLearnWord = personalLearnWordService.save(personalLearnWord) ;
            }
            if (createDate!=null){
                createDate =new Date() ;
            }
            WordLearn wordLearn =new WordLearn();
            wordLearn.setLearnDate(createDate);
            wordLearn.setLearnTime(learnTime);
            wordLearn.setState(learnState);
            wordLearn.setPersonalLearnWord(personalLearnWord);
            wordLearn.setIsSuccess(successState);
            wordLearn = r.save(wordLearn) ;
            personalLearnWord.setCurrentSate(learnState);
            personalLearnWord.setCurrentId(wordLearn.getId());
            personalLearnWordService.saveOrUpdate(personalLearnWord) ;


        }





        return new ApiResult(ResultMsg.SUCCESS,null);
    }

    @Override
    public List<WordLearn> findGroupByUserId(Long classId,Date startTime , Date endTime) {

        return wordLearnDao.findGroupByUserId(classId,startTime,endTime);
    }



}
