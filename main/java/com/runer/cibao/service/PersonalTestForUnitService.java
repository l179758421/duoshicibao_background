package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.PersonalTestForUnit;
import com.runer.cibao.domain.TestUnitScoreExcel;
import com.runer.cibao.domain.repository.PersonalTestForUnitRepository;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/25
 **/
public interface PersonalTestForUnitService extends BaseService<PersonalTestForUnit,PersonalTestForUnitRepository> {


    /**
     * 获得unit的测试
     * @param userId
     * @param bTime
     * @param eTime
     * @param page
     * @param limit
     * @param personalLearnUnitId
     * @return
     */
    Page<PersonalTestForUnit> findUnitTests(Long userId, Long unitId, Long personalLearnUnitId, Date bTime, Date eTime, Integer isPre, Integer page, Integer limit) ;


    /**
     * 增加单元的测试
     * @param id
     * @param personalLearnUnitId
     * @param totalTestTime
     * @param totalWordsNum
     * @param testTime
     * @param testWordsNum
     * @param rightWordsNum
     * @param ids
     * @param errorIds
     * @param testDate
     * @return
     */
    ApiResult addOrUpdatePersonalUnitTest(Long id, Long personalLearnUnitId, Long totalTestTime,
                                          Long totalWordsNum, Long testTime, Long testWordsNum,
                                          Long rightWordsNum,
                                          String ids,
                                          String errorIds, Date testDate, Integer score, Integer isPassed, String testRecords);


    /**
     * 获得某一天的测试
     * @param userId
     * @param unitId
     * @param date
     * @param personalLearnUnitId
     * @return
     */
    ApiResult findOneDayTests(Long userId, Long unitId, Long personalLearnUnitId, Date date);


    /**
     * 获得test的详情
     * @param testId
     * @return
     */
    ApiResult findTestDetail(Long testId) ;


    /**
     * 获得题目的列表（错误正确===）
     * @param testId
     * @param isWrongOnly
     * @return
     */
    ApiResult findWordsListByTestId(Long testId, Integer isWrongOnly) ;




    ApiResult findLatestOne(Long personalUnitId) ;



    ApiResult findPreOne(Long presonalUnitId) ;


    ApiResult finaAllByLearnUnitId(Long learnUnitId) ;

    Page<PersonalTestForUnit> findUnitTestOrderByDate(Long userId, Long unitId, Integer page, Integer limit);

    List<TestUnitScoreExcel> scoresToExcels(List<PersonalTestForUnit> testForBooks);

    ApiResult export2PersonalExcel(List<PersonalTestForUnit> list, String fileName, HttpServletResponse response);

    TestUnitScoreExcel scoreToExcel(PersonalTestForUnit testForBook);


}
