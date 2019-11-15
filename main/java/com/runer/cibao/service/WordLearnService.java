package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.person_word.WordLearn;
import com.runer.cibao.domain.repository.WordLearnRepostitory;
import com.runer.cibao.exception.SmartCommunityException;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/17
 **/
public interface WordLearnService extends BaseService<WordLearn,WordLearnRepostitory>  {

    Page<WordLearn> findWordLearn(Integer state, List<Integer> states, Date startTime, Date endTime, Long userId,
                                  Long bookId, Long unitId, Integer success, Integer page, Integer limit);

    /**
     * 对学习单词的数量统计；
     * @param state
     * @param states
     * @param startTime
     * @param endTime
     * @param userId
     * @param bookId
     * @param unitId
     * @return
     */
    long countWordLearn(Integer state, List<Integer> states, Date startTime, Date endTime, Long userId,
                        Long bookId, Long unitId, Integer success);

    /**
     * 学习单词的统计
     * @param appUserId
     * @param wordId
     * @param createDate
     * @param time
     * @param unitId
     * @param state
     * @return
     * @throws SmartCommunityException
     */
    ApiResult  addWordLearn(Long appUserId, Long wordId, Date createDate, Integer time
            , Long unitId, Integer state, Integer isSuccess) throws SmartCommunityException;

    /**
     * 批量的增加；
     * @param appUserId
     * @param wordIds
     * @param createDate
     * @param times
     * @param unitId
     * @param states
     * @return
     * @throws SmartCommunityException
     */
    ApiResult  addWordLearns(Long appUserId, String wordIds, Date createDate, String times, Long unitId, String states, String isSuccesses) throws SmartCommunityException;

    /**
     * 根据班级id查找学习的用户
     * @param classId
     * @return
     */
    List<WordLearn> findGroupByUserId(Long classId, Date startTime, Date endTime);




}
