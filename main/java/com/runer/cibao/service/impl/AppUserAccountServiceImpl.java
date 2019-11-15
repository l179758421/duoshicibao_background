package com.runer.cibao.service.impl;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.AppUserAccountDao;
import com.runer.cibao.domain.AppUser;
import com.runer.cibao.domain.AppUserAccount;
import com.runer.cibao.domain.repository.AppUserAccountRepository;
import com.runer.cibao.service.AppUserAccountService;
import com.runer.cibao.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.runer.cibao.exception.ResultMsg.SUCCESS;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/27
 **/
@Service
public class AppUserAccountServiceImpl extends BaseServiceImp<AppUserAccount, AppUserAccountRepository> implements AppUserAccountService {


    @Autowired
    AppUserAccountDao appUserAccountDao ;

    @Autowired
    AppUserService appUserService ;


    @Override
    public ApiResult generateByUserId(Long userId) {

        ApiResult userApiRes =appUserService.findByIdWithApiResult(userId);

        if (userApiRes.getMsgCode()!=SUCCESS.getMsgCode()){
            return  userApiRes ;
        }


        AppUserAccount appUserAccount =null ;
        ApiResult accountResult = findAccountByUserId(userId);

            if (accountResult.getMsgCode()==SUCCESS.getMsgCode()){
                appUserAccount = (AppUserAccount) accountResult.getData();
            }else{
                appUserAccount =new AppUserAccount() ;
                appUserAccount.setAppUser((AppUser) userApiRes.getData());
                appUserAccount.setGoldCoins(0);
                appUserAccount.setAccumulatePoints(0L);
                appUserAccount = r.save(appUserAccount) ;
            }
        return new ApiResult(SUCCESS,appUserAccount);
    }

    @Override
    public ApiResult findAccountByUserId(Long userId) {

        ApiResult userResult = appUserService.findByIdWithApiResult(userId) ;
        if (userResult.getMsgCode()!=SUCCESS.getMsgCode()){
            return  userResult ;
        }
            AppUserAccount appUserAccount =  appUserAccountDao.findByUserId(userId);
            if (appUserAccount==null){
                appUserAccount =new AppUserAccount() ;
                appUserAccount.setAppUser((AppUser) userResult.getData());
                appUserAccount.setAccumulatePoints(0L);
                appUserAccount.setGoldCoins(0);
                appUserAccount =  r.saveAndFlush(appUserAccount) ;
            }
            return  new ApiResult(SUCCESS,appUserAccount) ;

    }

    @Override
    public ApiResult rechargeByRedeemCode(String redeemCode, Long schoolId, Long userId) {
        return null;
    }
}
