package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.LearnTime;
import com.runer.cibao.domain.repository.LearnTimeRepository;

import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/11/3
 **/
public interface LearnTimeService extends BaseService<LearnTime, LearnTimeRepository>{

    /**
     * 上传有效学习的时间
     * @param --lerarnTime
     * @param --appUserId
     * @param --date
     * @return
     */
    ApiResult uploadLearnTime(Long lerarnTime, Long appUserId, Date date);


    /**
     * 获得有效学习的时；
     * @param appUserId
     * @param date
     * @return
     */
    ApiResult getUploadLearnTime(Long appUserId, Date date) ;


    /**
     * 获得有效学习的时；
     * @param appUserId
     * @return
     */
    List<LearnTime> getUploadLearnTime(Long appUserId) ;


    /**
     * 获得有效学习的时；
     * @param appUserId
     * @return
     */
    List<LearnTime> getLearnTime(Long appUserId) ;


    List<LearnTime> getLearnTimeDate(Date startdate, Date enddate, Long appUserId);


}
