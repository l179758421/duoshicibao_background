package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.UserReviewDao;
import com.runer.cibao.domain.UserRevivews;
import com.runer.cibao.domain.repository.UserReviewsRepostory;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.UserReviewService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/30
 **/
@Service
public class UserReviceServiceImpl extends BaseServiceImp<UserRevivews, UserReviewsRepostory> implements UserReviewService {

    @Autowired
    UserReviewDao userReviewDao;
    @Override
    public ApiResult getone(Long appUserid, Long bookid, Long unitId, Integer type) {
        UserRevivews one = userReviewDao.findOne(bookid, unitId, appUserid, type);
        if (one==null) {
            one =new UserRevivews();
            one.setAppUserId(appUserid);
            one.setBookId(bookid);
            one.setUnitId(unitId);
            one.setType(type);
            r.saveAndFlush(one);
        }
        return new ApiResult(ResultMsg.SUCCESS,one);
    }

    @Override
    public ApiResult updateUnitReviews(Long appUserId, Long bookId, Long unitId, Integer type, String times, String date) {
        ApiResult apiResult =getone(appUserId,bookId,unitId,type);
        UserRevivews userRevivews = (UserRevivews) apiResult.getData();
        userRevivews.setTimes(times);
        try {
            userRevivews.setLastCommitDate(DateUtils.parseDate(date,"yyyy-MM-dd hh:mm:ss"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        r.saveAndFlush(userRevivews) ;
        return new ApiResult(ResultMsg.SUCCESS,userRevivews);
    }

    @Override
    public ApiResult findUnitsReviews(Long appUserId, Long bookId, Integer type) {
        return new ApiResult(ResultMsg.SUCCESS, userReviewDao.findUserReviews(bookId,null,appUserId,type));
    }
}
