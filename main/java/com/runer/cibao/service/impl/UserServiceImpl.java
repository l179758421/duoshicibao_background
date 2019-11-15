package com.runer.cibao.service.impl;

import com.runer.cibao.domain.User;
import com.runer.cibao.domain.repository.UserRepository;
import com.runer.cibao.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/12
 **/
@Service
public class UserServiceImpl extends BaseServiceImp<User,UserRepository> implements UserService {
    @Override
    public User findUserByLoginName(String loginName) {
        return r.findUserByLoginName(loginName);
    }
}
