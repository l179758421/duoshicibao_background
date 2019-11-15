package com.runer.cibao.service;

import com.runer.cibao.domain.WordCount;
import com.runer.cibao.domain.repository.WordCountRepository;

/**
 * @author k
 * @Date: Created in 16:17 2018/8/23
 * @Description:
 */
public interface WordCountService extends BaseService<WordCount, WordCountRepository>{

    /**
     * @Author: Kuang-YI
     * @Date: 2018/8/23 16:22
     * @param: [appUserId, unitId, status]
     * @result: java.lang.Integer
     * @Description: 获得学习单元次数
     */
    Long getUnitCount(Long appUserId, Long unitId, Integer status);

    void plusUnitCount(Long appUserId, Long unitId, Integer status);

    void plusNewWordsCount(Long appUserId);

    WordCount getTodayNewWordsCountEntity(Long appUserId);

    Long getTodayNewWordsCount(Long appUserId);

    Integer getTodayOldWordsCount(Long appUserId);
}
