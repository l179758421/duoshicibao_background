package com.runer.cibao.service;

import com.runer.cibao.domain.User;
import com.runer.cibao.domain.repository.UserRepository;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/12
 **/
public interface UserService extends BaseService<User,UserRepository> {

    User findUserByLoginName(String loginName) ;

}
