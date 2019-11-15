package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.PunchCard;
import com.runer.cibao.domain.repository.PunshCardRepository;

import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/29
 * 打卡相关；
 **/
public interface PunshCardService extends BaseService<PunchCard, PunshCardRepository>   {

    /**
     * 打卡
     * @param userId
     * @param date
     * @return
     */
    ApiResult punchCard(Long userId) ;


    /**
     * 获得打卡的记录
     * @param userId
     * @param bDate
     * @param eDate
     * @return
     */
    ApiResult findCards(Long userId, Date bDate, Date eDate) ;


    /**
     * 前七天的打卡情况
     * @param userId
     * @return
     */
    ApiResult findBeforeSevenDays(Long userId);

    /**
     * 获得总的打卡天数
     *
     * @return
     */
    ApiResult getPunchCardDays(Long userId);


    ApiResult findSevenDays(Long userId);


}
