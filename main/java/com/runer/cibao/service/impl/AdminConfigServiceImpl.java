package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.AdminConfig;
import com.runer.cibao.domain.repository.AdminConfigRpository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.AdminConfigService;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/22
 **/


@Service
public class AdminConfigServiceImpl extends BaseServiceImp<AdminConfig,AdminConfigRpository> implements AdminConfigService {
    @Override
    public ApiResult editAdminConfig(Integer freeExperienceTime, Integer cardLearningTime, Integer getScoreCardTime, Integer cardScroeForFullDay, Integer avrageCardScrore, Integer ad_height_ratio ,Integer ad_width_ratio,Integer share_score,Integer recharge_score) {

       AdminConfig adminConfig = (AdminConfig) forceGetAdminConfig().getData();

       if (freeExperienceTime!=null){
           adminConfig.setFreeExperienceTime(freeExperienceTime);
       }
       if (cardLearningTime!=null){
           adminConfig.setCardLearningTime(cardLearningTime);
       }
       if (getScoreCardTime!=null){
           adminConfig.setGetScoreCardTime(getScoreCardTime);
       }
       if (cardScroeForFullDay!=null){
           adminConfig.setCardScroeForFullDay(cardScroeForFullDay);
       }
       if (avrageCardScrore!=null){
           adminConfig.setAvrageCardScrore(avrageCardScrore);
       }
       if (ad_height_ratio!=null){
           adminConfig.setAd_height_ratio(ad_height_ratio);
       }
       if (ad_width_ratio!=null){
           adminConfig.setAd_width_ratio(ad_width_ratio);
       }
       if(share_score!=null){
           adminConfig.setShareScore(share_score);
       }
       if(recharge_score!=null){
           adminConfig.setRechargeScore(recharge_score);
       }
        adminConfig = r.saveAndFlush(adminConfig) ;
        return  new ApiResult(ResultMsg.SUCCESS,adminConfig);
    }



    @Override
    public ApiResult forceGetAdminConfig() {

        AdminConfig adminConfig = null ;
        if (ListUtils.isEmpty(findAll())){
            adminConfig =new AdminConfig() ;
            adminConfig = r.save(adminConfig);
        }else{
            adminConfig =findAll().get(0) ;
        }

        return new ApiResult(ResultMsg.SUCCESS,adminConfig);
    }
}
