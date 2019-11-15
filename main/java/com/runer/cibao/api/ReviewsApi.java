package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.service.PersonalLearnInfoService;
import com.runer.cibao.service.UserReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/31
 **/
@RestController
@RequestMapping(value = "api/ReviewsApi")
@Api(description = "复习相关的api")
public class ReviewsApi {

    @Autowired
    UserReviewService userReviewService;

    @Autowired
    PersonalLearnInfoService personalLearnInfoService;

    @RequestMapping(value = "updateReviewRecord",method = {RequestMethod.POST})
    @ApiOperation(notes = "更新review的记录",value = "更新review的记录")
    public ApiResult updateReviewRecord(Long appUserId ,Long bookId ,Long unitId ,Integer type ,  String times ,String date ){
        return    userReviewService.updateUnitReviews(appUserId,bookId,unitId,type,times,date) ;
    }

    @RequestMapping(value = "findUnitsReviews",method = {RequestMethod.POST})
    @ApiOperation(notes = "获得reviews的记录",value = "获得reviews的记录")
    public ApiResult findUnitsReviews(Long appUserId ,Long bookId ,Integer type ){
        return    userReviewService.findUnitsReviews(appUserId,bookId,type) ;
    }

    @RequestMapping(value = "getone",method = {RequestMethod.POST})
    @ApiOperation(notes = "获得一个复习的详情",value = "获得reviews的记录")
    public ApiResult getone(Long appUserid ,Long bookid ,Long unitId ,Integer type ){
        return    userReviewService.getone(appUserid,bookid,unitId,type) ;
    }


    @ApiOperation(notes = "获得用户学习时长",value = "获得用户学习时长")
    @RequestMapping(value = "getStudyTime",method = {RequestMethod.POST})
    public ApiResult getStudyTime(Long userId){
        return    personalLearnInfoService.findOneLearn(userId);
    }


}
