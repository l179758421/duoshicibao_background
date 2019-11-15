package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/28
 *
 * 关于测试app
 *
 *
 **/
public interface PersonalTestApiService  {


    /***
     * 获得个人的课本测试
     * @param userId
     * @return
     */
    ApiResult  findPersonalLearnBookTest(Long userId) ;


    /**
     * 获得个人的单元测试
     * @param personalLearnBookId
     * @return
     */
    ApiResult findPersonalLearnBookUnitTest(Long personalLearnBookId);


    /**
     * 获得课本的测试记录
     * @param personalLearnBookId
     * @return
     */
    ApiResult findPeronalLearnBookTestRecords(Long personalLearnBookId) ;


    /**
     * 获得单元的测试记录；
     * @param personalLearnTestId
     * @return
     */
    ApiResult findPersonalLearnUnitsTestRecords(Long userId, Long personalLearnTestId);





}
