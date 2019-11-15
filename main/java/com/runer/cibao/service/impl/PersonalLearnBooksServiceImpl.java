package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.PersonalLearnBooksDao;
import com.runer.cibao.domain.AppUser;
import com.runer.cibao.domain.PersonalLearnBooks;
import com.runer.cibao.domain.repository.PersonalLearnBooksRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.AppUserService;
import com.runer.cibao.service.PersonalLearnBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/26
 **/

@Service
public class PersonalLearnBooksServiceImpl extends BaseServiceImp<PersonalLearnBooks, PersonalLearnBooksRepository> implements PersonalLearnBooksService {


    @Autowired
    PersonalLearnBooksDao personalLearnBooksDao ;


    @Autowired
    AppUserService appUserService ;

    //todo
    @Override
    public ApiResult findbyUsrId(Long userId) {
        PersonalLearnBooks personalLearnBooks =personalLearnBooksDao.findByUserId(userId);
        if (personalLearnBooks==null){
            return  new ApiResult(ResultMsg.ENTITY_ID_NOT_EXISTS,null) ;
        }

        return new ApiResult(ResultMsg.SUCCESS,personalLearnBooks);
    }

    @Override
    public ApiResult addPersonalBooks(Long userId) {

        //查看是否存在
        ApiResult personalApiResult =findbyUsrId(userId);
        if (personalApiResult.getMsgCode()==ResultMsg.SUCCESS.getMsgCode()){
            return  new ApiResult(ResultMsg.ENTITY_IS_EXISTED,null);
        }

        ApiResult userApiResult = appUserService.findByIdWithApiResult(userId);

        //查看user是否存在
       if (userApiResult.getMsgCode()!=ResultMsg.SUCCESS.getMsgCode()){
           return  userApiResult ;
       }


        PersonalLearnBooks personalLearnBooks = new PersonalLearnBooks() ;
        personalLearnBooks.setAppUser((AppUser) userApiResult.getData());
        //创建新的实体类
        personalLearnBooks= r.saveAndFlush(personalLearnBooks) ;

        return new ApiResult(ResultMsg.SUCCESS,personalLearnBooks);
    }
}
