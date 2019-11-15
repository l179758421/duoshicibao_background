package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.AdminConfig;
import com.runer.cibao.domain.repository.AdminConfigRpository;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/22
 **/
public interface AdminConfigService  extends BaseService<AdminConfig,AdminConfigRpository> {


    /**
     * 设置后台的一些数据设置
     * @param freeExperienceTime
     * @param cardLearningTime
     * @param getScoreCardTime
     * @param cardScroeForFullDay
     * @param avrageCardScrore
     * @return
     */
     ApiResult editAdminConfig(Integer freeExperienceTime, Integer cardLearningTime, Integer getScoreCardTime,
                               Integer cardScroeForFullDay, Integer avrageCardScrore,
                               Integer ad_height_ratio, Integer ad_width_ratio, Integer share_score, Integer recharge_score
     );


     ApiResult  forceGetAdminConfig() ;




}
