package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.AppUserAccount;
import com.runer.cibao.domain.repository.AppUserAccountRepository;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/27
 **/
public interface AppUserAccountService extends BaseService<AppUserAccount,AppUserAccountRepository>  {



     ApiResult generateByUserId(Long userId);

     ApiResult findAccountByUserId(Long userId) ;

     ApiResult rechargeByRedeemCode(String redeemCode, Long schoolId, Long userId) ;



}
