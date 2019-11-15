package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.BookTestQuetions;
import com.runer.cibao.domain.repository.BookTestQuestionsRepository;
import org.springframework.data.domain.Page;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/19
 **/
public interface BookTestQuestionsService extends BaseService<BookTestQuetions, BookTestQuestionsRepository> {


    /**
     * 获得测试套题的列表
     * @param bookId
     * @param unitId
     * @param testName
     * @param page
     * @param limit
     * @return
     */
    Page<BookTestQuetions> findBookTestQuestions(Long bookId, Long unitId, Long onlyBookId, String testName, Integer page, Integer limit);


    /**
     * 增加或者更新套题
     * @param id
     * @param bookId
     * @param unitId
     * @param bookTestIds
     * @param totalScore
     * @param totalTime
     * @param passScore
     * @param testName
     * @return
     */
    ApiResult addOrUpdateBookTestQuestions(Long id, Long bookId, Long unitId, String bookTestIds,
                                           Integer totalScore, Integer totalTime, Integer passScore, String testName);






}
