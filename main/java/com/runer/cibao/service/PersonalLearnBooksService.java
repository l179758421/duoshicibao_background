package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.PersonalLearnBooks;
import com.runer.cibao.domain.repository.PersonalLearnBooksRepository;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/26
 **/
public interface PersonalLearnBooksService extends BaseService<PersonalLearnBooks,PersonalLearnBooksRepository> {

    ApiResult findbyUsrId(Long userId);

    ApiResult addPersonalBooks(Long userId) ;

}
