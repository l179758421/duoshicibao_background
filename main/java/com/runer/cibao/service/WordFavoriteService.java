package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.WordFavorite;
import com.runer.cibao.domain.repository.WordFavoriteRepository;

public interface WordFavoriteService extends BaseService<WordFavorite,WordFavoriteRepository> {

    /**
     * 添加收藏的单词，查找是否收藏过，没有就新建
     * @param userId
     * @param wordId
     * @return
     */
    ApiResult addWordFavor(Long userId, Long wordId);

    /**
     * 查询某个用户收藏的单词
     * @param userId
     * @return
     */
    ApiResult findByUserId(Long userId);

    /**
     * 将单词从收藏移除
     * @param userId
     * @param wordId
     * @return
     */
    ApiResult deleteWord(Long userId, Long wordId);

    /**
     * 单词是否收藏
     * @param userId
     * @param wordId
     * @return
     */
    ApiResult isFav(Long userId, Long wordId);
}
