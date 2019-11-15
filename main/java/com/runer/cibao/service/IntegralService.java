package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.Integral;
import com.runer.cibao.domain.IntegralDetail;
import com.runer.cibao.domain.repository.IntegralRepository;
import org.springframework.data.domain.Page;

import java.util.Date;

/**
 *
 * @Author  szhua ；
 *
 * 积分相关的业务逻辑；
 */
public interface IntegralService extends BaseService<Integral, IntegralRepository> {

    ApiResult findUserIntegral(Long appUserId);



    /**
     * 单次打卡获得积分
     * @param appUserId
     * @return
     */
    ApiResult addDaySignIntegral(Long appUserId, boolean isFive);

    /**
     * 分享获得积分
     * @param appUserId
     * @return
     */
    ApiResult addShareIntegral(Long appUserId);

    /**
     * 充值获得积分
     * @param appUserId
     * @return
     */
    ApiResult addRechargeIntegral(Long appUserId);

    /**
     * 学习获得积分
     * @param appUserId
     * @param studyTime
     * @return
     */
    ApiResult addStudyIntegral(Long appUserId, Long studyTime);


    ApiResult addTestIntegral(Long appUserId);


    /**
     * 学习单词获得的积分
     * @param appUserId
     * @param wordNum
     * @return
     */
    ApiResult addStudyWordIntegral(Long appUserId, Long wordNum);

    /**
     * 更新总积分
     * @param appUserId
     * @param number  消费的积分数
     * @return
     */
    ApiResult updateTotalIntegral(Long appUserId, Long number);

    /**
     * 分页获得个人积分明细
     * @param appUserId
     * @return
     */
    Page<IntegralDetail> getPersonalIntegralDetail(Long appUserId, Integer page, Integer limit);
    /**
     * 获得当天获得的积分
     * @param appUserId
     * @param date
     * @return
     */
    long findOneDayIntegrals(Long appUserId, int type, Date date) ;





}
