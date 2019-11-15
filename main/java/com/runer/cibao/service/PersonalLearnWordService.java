package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.person_word.PersonalLearnWord;
import com.runer.cibao.domain.repository.PersonalLearnWordRepository;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/17
 **/
public interface  PersonalLearnWordService extends BaseService<PersonalLearnWord,PersonalLearnWordRepository> {

    ApiResult findByWordId(Long appUserID, Long wordId) ;

}
