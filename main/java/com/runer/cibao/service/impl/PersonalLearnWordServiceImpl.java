package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.PersonalLearnWrodDao;
import com.runer.cibao.domain.person_word.PersonalLearnWord;
import com.runer.cibao.domain.repository.PersonalLearnWordRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.PersonalLearnWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/17
 **/
@Service
public class PersonalLearnWordServiceImpl extends BaseServiceImp<PersonalLearnWord,PersonalLearnWordRepository> implements PersonalLearnWordService {

    @Autowired
    PersonalLearnWrodDao personalLearnWrodDao ;

    @Override
    public ApiResult findByWordId(Long appUserID, Long wordId) {
        PersonalLearnWord word = personalLearnWrodDao.findByWordId(appUserID, wordId);;
        if (word==null){
            return  new ApiResult("不存在") ;
        }
        return  new ApiResult(ResultMsg.SUCCESS,word) ;
    }
}
