package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.UserRevivews;
import com.runer.cibao.domain.repository.UserReviewsRepostory;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/30
 **/
public interface UserReviewService extends BaseService<UserRevivews, UserReviewsRepostory>  {

    /**
     * @param appUserid
     * @param bookid
     * @param unitId
     * @return
     */
    ApiResult getone(Long appUserid, Long bookid, Long unitId, Integer type);
    /**
     *
     * @param appUserId
     * @param bookId
     * @param unitId
     * @param times
     * @param date
     * @return
     */
    ApiResult updateUnitReviews(Long appUserId, Long bookId, Long unitId, Integer type, String times, String date) ;


    /**
     *
     * @param appUserId
     * @param bookId
     * @param type
     * @return
     */
    ApiResult findUnitsReviews(Long appUserId, Long bookId, Integer type);









}
