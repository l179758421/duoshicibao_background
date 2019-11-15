package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/26
 *
 *
 * 因需要进行业务统计故写此service；
 *
 *
 * 个人学习的api相关service
 **/
public interface PersonalBookApiService {


    /**
     * 获得除去当前课本的列表
     * @param userId
     * @return
     */
    ApiResult findLearnBooksWithOutCurrent(Long userId);


    /**
     * 获得当前的课本
     * @param userId
     * @return
     */
    ApiResult findCurrentLearnBook(Long userId) ;


    /**
     * 获得所有的个人课本列表
     * @param userId
     * @param isWithOutCurrent
     * @return
     */
    ApiResult findAllPersonalBooks(Long userId, boolean isWithOutCurrent);


    /**
     * 获得所有的课本，并且表明当前的学习进度，激活状态；（）
     */
     ApiResult findAllbooks(String version, String stage, Long userId) ;


    /**
     * 获得所有的阶段
     */

    ApiResult findAllStage() ;

    /**
     * 获得某个阶段的所有版本
     */
    ApiResult findVersions(String stage);




}
