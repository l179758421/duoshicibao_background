package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.ReviewTest;
import com.runer.cibao.domain.repository.ReviewTestRepository;

public interface ReviewTestService extends BaseService<ReviewTest, ReviewTestRepository> {
    /**
     * 记录测试记录
     * @param state
     * @param userId
     * @param unitId
     * @param totalTestTime
     * @param rightIds
     * @param errorIds
     * @return
     */
    ApiResult addReviewTest(String state, Long userId, Long unitId, Long testUseTime, Long totalTestTime, String rightIds, String errorIds);


    /**
     * 查找测试记录(最新一条)
     * @param userId
     * @param unitId
     * @return
     */
    ApiResult findReviewTest(Long userId, Long unitId);

    ApiResult findReviewTestList(Long userId, Long unitId);


    /**
     * 获得测试结果
     * @param userId
     * @param bookId
     * @return
     */
    ApiResult findReviewTestData(long userId, Long bookId);


    long countReviewTestCount(Long userId, Long unitId) ;



}
