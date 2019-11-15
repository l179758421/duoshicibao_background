package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.NewBookWord;
import com.runer.cibao.domain.repository.NewBookWordRepository;
import org.springframework.data.domain.Page;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/29
 **/
public interface NewBookWordService extends BaseService<NewBookWord, NewBookWordRepository> {

    /**
     * 统计个人的生词个数；
     * @param userId
     * @return
     */
    long countOnePersonalNewWord(Long userId) ;

     /**
     * 添加生词
     * @param userId
     * @param bookWordId
     * @return
     */
     ApiResult addNewBookWords(Long userId, Long bookWordId);
    /**
     * 批量添加单词；
     * @param userId
     * @param bookWords
     * @return
     */
     @Deprecated
     ApiResult addNewBookWordsBacth(Long userId, String bookWords);
    /**
     * 批量添加单词；
     * @param userId
     * @param bookWords
     * @return
     */
    ApiResult addNewBookWordsBacthNoLimit(Long userId, String bookWords);
    /**
     * 获得生词
     * @param page
     * @param limit
     * @param userId
     * @param isNow
     * @return
     */
     Page<NewBookWord> findNewWords(Integer page, Integer limit, Long userId, Long bookWordId, Integer isNow);

    /**
     * 获得当前的生词
     * @param userId
     * @return
     */
     ApiResult findUserNewWordsNew(Long userId);


    /**
     * 获得历史的生词
     * @param userId
     * @param page
     * @param limit
     * @return
     */
     Page<NewBookWord> findUserAllNewWordsHistory(Long userId, Integer page, Integer limit) ;


    /**
     * 删除生词
     * @param newWordsId
     * @return
     */
     ApiResult deleteNewWords(Long newWordsId) ;

     ApiResult deleteNewWord(Long userId, Long wordId);


    /**
     * 设置生词为巩固的状态
     */
    ApiResult setOneWordConsolidated(Long newWordId) ;


    /**
     *
     */
//    ApiResult getOne(Long appUserId ,Long wordId) ;


    ApiResult isAdd(Long appUserId, Long wordId);



}
