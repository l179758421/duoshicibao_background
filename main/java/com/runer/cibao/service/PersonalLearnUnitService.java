package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.PersonalLearnUnit;
import com.runer.cibao.domain.repository.PersonalLearnUnitReposiory;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/26
 **/
public interface PersonalLearnUnitService extends BaseService<PersonalLearnUnit, PersonalLearnUnitReposiory> {



       Page<PersonalLearnUnit> findUnits(Long userId, Long bookId, Long personalLearnBookId
               , Integer isPassed, Integer isCurrentLearnedUnit, Integer isFinished, Integer reviewTestState
               , Integer page, Integer limit);

    /**
     * 增加或者更新个人单元
     * @param personalLearnBookId
     * @param bookUnitId
     * @param score
     * @param isPassed
     * @param isCurrentLearnedUnit
     * @param totalWordNum
     * @param currentWordNum
     * @return
     */
    ApiResult addOrUpdatePersonalLearnUnit(Long id, Long personalLearnBookId, Long bookUnitId,
                                           Integer score, Integer isPassed,
                                           Integer isCurrentLearnedUnit);

    /**
     * 根据个人的书本自动的生成个人学习的单元；
     * @param personalLearnBookId
     * @return
     */
    ApiResult generatePersonalLearnUnitByBook(Long personalLearnBookId) ;


    ApiResult findByLearnBookId(Long learnBookId) ;

    /**
     * 获得个人的学习单元；
     * @param unitId
     * @param userId
     * @param personlBookId
     * @return
     */
    ApiResult findOneByUserIdAndUnitId(Long unitId, Long userId, Long personlBookId) ;

    Map<String, Object> numsInfo(Long wordId, Long bookId) ;

    List<PersonalLearnUnit> findByIds(Long appUser, String unitIds);

    /**
     * 获得需要复习的单元；
     * @param appUserId
     * @return
     */
    List<PersonalLearnUnit>  findReviewTestUnits(Long appUserId);
    /**
     * 更新复习测试的状态
     * @return
     */
    ApiResult updateUnitReviewState(String leftWords, Date date, Integer state, Long unitId, Long userId);





}
