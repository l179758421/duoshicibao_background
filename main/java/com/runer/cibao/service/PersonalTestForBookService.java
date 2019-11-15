package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.PersonalTestForBook;
import com.runer.cibao.domain.TestBookScoreExcel;
import com.runer.cibao.domain.repository.PersonalTestForBookRepository;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/25
 **/
public interface PersonalTestForBookService extends BaseService<PersonalTestForBook,PersonalTestForBookRepository> {


    /**
     * 获得书本的测试列表
     * @param userId
     * @param bookId
     * @param isPre
     * @param bTime
     * @param eTime
     * @param page
     * @param limit
     * @return
     */
    Page<PersonalTestForBook> findBooksTest(Long userId, Long bookId, Long personalLearnBookId, Integer isPre, Date bTime, Date eTime, Integer page, Integer limit) ;

    /**
     * 增加课本的测试
     * @param id
     * @param personalLearnBooId
     * @param totalTestTime
     * @param totalWordsNum
     * @param testTime
     * @param testWordsNum
     * @param rightWordsNum
     * @param isPreLearnTest
     * @param ids
     * @param errorIds
     * @param testDate
     * @param isPassed
     * @return
     */
    ApiResult addOrUpdatePersonalBookTest(Long id, Long personalLearnBooId, Long totalTestTime,
                                          Long totalWordsNum, Long testTime, Long testWordsNum,
                                          Long rightWordsNum,
                                          Integer isPreLearnTest, String ids,
                                          String errorIds, Date testDate, Integer score, Integer isPassed, String testRecords);

    /**
     * 获得某一天的测试
     * @param userId
     * @param bookId
     * @param date
     * @return
     */
    ApiResult findOneDayTests(Long userId, Long bookId, Long personalLearbBookId, Date date);
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


    /**
     * 获得最新的测试
     */
    ApiResult findTestLatest(Long personalBookId);


    /**
     * 获得学前测试
     */
    ApiResult findTestPreLearn(Long personalBookId) ;


    /**
     *  获得测试；
     * @param userId
     * @param bookId
     * @return
     */
    ApiResult findTestByUserIdAndBookId(Long userId, Long bookId);



    /**
     * 导出excel
     * @param
     * @param fileName
     * @param response
     * @return
     */
    ApiResult export2Excel(List<PersonalTestForBook> list, String fileName, HttpServletResponse response) ;




    /**
     * 测试成绩转换成excel （批量！）
     * @param testForBooks
     * @return
     */
    List<TestBookScoreExcel> scoresToExcels(List<PersonalTestForBook> testForBooks) ;



    /**
     * 测试成绩转换成excel
     * @param testForBook
     * @return
     */
    TestBookScoreExcel scoreToExcel(PersonalTestForBook testForBook) ;

}
