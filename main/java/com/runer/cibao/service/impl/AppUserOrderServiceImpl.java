package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.AppUserOrderDao;
import com.runer.cibao.domain.AppUserAccount;
import com.runer.cibao.domain.AppUserOrder;
import com.runer.cibao.domain.repository.AppUserOrderRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.AppUserAccountService;
import com.runer.cibao.service.AppUserOrderService;
import com.runer.cibao.util.Arith;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.runer.cibao.exception.ResultMsg.*;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/27
 **/

@Service
public class AppUserOrderServiceImpl extends BaseServiceImp<AppUserOrder,AppUserOrderRepository> implements AppUserOrderService {


    @Autowired
    AppUserOrderDao appUserOrderDao ;


    @Autowired
    AppUserAccountService appUserAccountService ;


    @Override
    public ApiResult createOrder(Long userId, Integer type, String title, String des, Long relatedId ,Integer changeNum) {

        ApiResult accountResult = appUserAccountService.findAccountByUserId(userId);

        if (accountResult.getMsgCode()!=SUCCESS.getMsgCode()){
            return  accountResult ;
        }
        AppUserAccount appUserAccount = (AppUserAccount) accountResult.getData();


        if (changeNum==null){
            changeNum = 0;
        }
        /**
         * 判断账户余额够不够； 不够的话就进行返回；
         */
        if (Arith.add(appUserAccount.getGoldCoins(),changeNum)<0){
            return  new ApiResult(YOUR_ACCOUNT_IS_NOT_ENOUGH,null) ;
        }

          AppUserOrder appUserOrder =new AppUserOrder() ;
          appUserOrder.setAppUserAccount(appUserAccount);
          appUserOrder.setType(type);
          appUserOrder.setDes(des);
          appUserOrder.setRelatedId(relatedId);





          appUserOrder.setChangeNum(changeNum);
          appUserOrder.setCreateDate(new Date());
          appUserOrder.setTitle(title);
        //changeNum ; 对账户进行更改
        appUserAccount.setGoldCoins((int) Arith.add(appUserAccount.getGoldCoins(),changeNum));
        try {
            appUserAccountService.update(appUserAccount);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }


        appUserOrder =r.save(appUserOrder) ;



        return new ApiResult(SUCCESS,appUserOrder);
    }

    @Override
    public ApiResult updateOrder(Long id, String title, String des, Long relatedId  , Integer changeNum) {


        ApiResult orderResult =findByIdWithApiResult(id);

        if (orderResult.getMsgCode()!=ResultMsg.SUCCESS.getMsgCode()){
            return  orderResult ;
        }

        AppUserOrder appUserOrder = (AppUserOrder) orderResult.getData();

        appUserOrder.setTitle(title);

        appUserOrder.setDes(des);

        appUserOrder.setChangeNum(changeNum);

        appUserOrder.setRelatedId(relatedId);



        AppUserAccount appUserAccount =appUserOrder.getAppUserAccount();

        //差别
        double change = Arith.sub(appUserOrder.getChangeNum(), changeNum);


        /**
         * 判断账户余额够不够
         */
        if (Arith.add(appUserAccount.getGoldCoins(),change)<0){
            return  new ApiResult(YOUR_ACCOUNT_IS_NOT_ENOUGH,null) ;
        }

        //changeNum ; 对账户进行更改
        appUserAccount.setGoldCoins((int) Arith.add(appUserAccount.getGoldCoins(),change));
        try {
            appUserAccountService.update(appUserAccount);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }



       appUserOrder = r.save(appUserOrder) ;



        return new ApiResult(SUCCESS,appUserOrder);
    }

    @Override
    public Page<AppUserOrder> findOrders(String title, Integer type, Long userId,Integer page ,Integer limit) {
        return appUserOrderDao.findOrders(type,userId,title,PageableUtil.basicPage(page,limit));
    }

    @Override
    public ApiResult deleteOrder(Long orderId) {
        try {
            boolean result = deleteById(orderId);

            if (result){
                return  new ApiResult(SUCCESS,orderId) ;
            }else{
                return  new ApiResult(NOT_FOUND,null) ;
            }

        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null) ;
        }

    }
}
