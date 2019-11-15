package com.runer.cibao.service;

import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.User;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/5
  账号管理相关
 **/
public interface AccountService {



    LayPageResult<User> getAccountList();



}
