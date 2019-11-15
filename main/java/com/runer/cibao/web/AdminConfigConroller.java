package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.service.AdminConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author sww
 * @Date 2019/9/25
 **/
@RestController
@RequestMapping(value = "adminConfig")
public class AdminConfigConroller {

    @Autowired
    AdminConfigService beanService ;

    @RequestMapping(value = "editAdminConfig")
    public ApiResult editAdminConfig(Integer freeExperienceTime,Integer cardLearningTime,Integer getScoreCardTime ,
                                     Integer  cardScroeForFullDay ,Integer avrageCardScrore , Integer ad_height_ratio ,Integer ad_width_ratio,Integer shareScore,Integer rechargeScore ){
        return    beanService.editAdminConfig(freeExperienceTime,cardLearningTime,getScoreCardTime,cardScroeForFullDay,avrageCardScrore,ad_height_ratio,ad_width_ratio,shareScore,rechargeScore);
    }

}
