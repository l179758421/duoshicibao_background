package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.WordLearnForPersonal;
import com.runer.cibao.domain.repository.WordLearnForPersonalRepostory;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.AppUserService;
import com.runer.cibao.service.LearnBookService;
import com.runer.cibao.service.PersonalLearnBookService;
import com.runer.cibao.service.WordLearnForPersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/27
 **/
@Service
public class WordLearnForPersonalServicewImpl extends BaseServiceImp<WordLearnForPersonal, WordLearnForPersonalRepostory> implements WordLearnForPersonalService {

   @Autowired
   LearnBookService learnBookService ;


   @Autowired
   AppUserService appUserService ;


   @Autowired
    PersonalLearnBookService personalLearnBookService ;

    /**
     * 获得一个个人课本的详情；
     * @param userId
     * @param bookId
     * @return
     */
    @Override
    public ApiResult findOne(Long userId, Long bookId) {
        WordLearnForPersonal wordLearnForPersonal = r.findWordLearnForPersonalByAppUserIdAndBookId(userId, bookId);
        if (appUserService.findByIdWithApiResult(userId).isFailed()) {
            return new ApiResult("用户不存在");
        }
        ApiResult bookResult =learnBookService.findByIdWithApiResult(bookId) ;
        if (bookResult.isFailed()){
            return  new ApiResult("课本不存在");
        }
        if (personalLearnBookService.getSinglePersonalLearnBook(userId,bookId).isFailed()){
            return  new ApiResult("您还未购买此课本");
        }
        if (wordLearnForPersonal==null){
             wordLearnForPersonal =new WordLearnForPersonal() ;
             wordLearnForPersonal.setAppUserId(userId);
             wordLearnForPersonal.setBookId(bookId);
            r.saveAndFlush(wordLearnForPersonal) ;
        }
        return new ApiResult(ResultMsg.SUCCESS,wordLearnForPersonal);
    }
}
