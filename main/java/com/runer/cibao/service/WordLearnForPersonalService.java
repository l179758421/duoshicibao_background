package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.WordLearnForPersonal;
import com.runer.cibao.domain.repository.WordLearnForPersonalRepostory;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/27
 **/
public interface WordLearnForPersonalService  extends BaseService<WordLearnForPersonal, WordLearnForPersonalRepostory> {
    ApiResult findOne(Long userId, Long bookId);
}
