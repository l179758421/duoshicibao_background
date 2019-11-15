package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.person_word.NewReviewRecord;
import com.runer.cibao.domain.repository.NewReviewRecordRepository;

import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==新学复习相关的东西；
 * @Date 2018/9/3
 **/
public interface NewReviceRecordService  extends BaseService<NewReviewRecord,NewReviewRecordRepository>{


    /**
     * 更新
     * @param appUserId
     * @param date
     * @param newIds
     * @param errorIds
     * @return
     */
    ApiResult uploadNewReviewRecord(Long appUserId, Date date, String newIds, String errorIds);

    /**
     * 获得详情--包含ids
     * @param appUserId
     * @param date
     * @return
     */
    ApiResult getReviewRecord(Long appUserId, Date date) ;

    /**
     * 获得详情，统计数量
     * @param appUserId
     * @param date
     * @return
     */
    ApiResult getReviewRecordNum(Long appUserId, Date date);

    /**
     * 获得指定日期的新学复习的个数；
     * @param appUserId
     * @param startTime
     * @param endTime
     * @return
     */
    List<NewReviewRecord> findAllUsersReviewsRecords(Long appUserId, Date startTime, Date endTime) ;


    /**
     * 获得单词的学习个数；
     * @param appUserId
     * @param startTime
     * @param endTime
     * @return
     */
    ApiResult getReviewCounts(Long appUserId, Date startTime, Date endTime) ;



}
