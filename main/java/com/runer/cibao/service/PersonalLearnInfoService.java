package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.PersonlLearnInfoBean;
import com.runer.cibao.domain.repository.PersonlLearnInfoRepository;

import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/9/5
 * 个人学习相关的数据；
 **/
public interface PersonalLearnInfoService extends BaseService<PersonlLearnInfoBean,PersonlLearnInfoRepository>{


    /**
     * 查找个人学习详情；没有的话创建一个；
     * @param userId
     * @param date
     * @return
     */
    ApiResult generateOneLearnInfo(Long userId, Date date) ;

    ApiResult findOneLearnInfo(Long userId, Date startDate, Date endDate);
    ApiResult findOneLearnInfoNew(Long userId, Date startDate, Date endDate);

    ApiResult findOneLearnInfo30(Long userId, Date startDate, Date endDate);

    ApiResult findOneLearn(Long userId);

    List<PersonlLearnInfoBean> findByClassUserLearn(Long classId, Date startTime, Date endTime);

    /**
     * 获得个人的排名
     * @param appUserId  userId
     * @param type  school country province ;
     * @param date  date ;
     * @return
     */
    ApiResult getOnePersonalRanking(Long appUserId, Integer type, Date date) ;




    /**
     * 获得学习的排名
     * @param appUserId userId
     * @param topNum   排名多少
     * @param date     排名时间
     * @return
     */
    ApiResult getRankingInfo(Long appUserId, Integer topNum, Date date, Integer type);

    /**
     * 获得排名情况；荣誉榜；
     * @param topNum
     * @param date
     * @param type
     * @return
     */
    ApiResult getRankingALl(String schoolId, Long provinceId, Integer topNum, Date date, Integer type) ;


    ApiResult ditrubePersonsHornor();



}
