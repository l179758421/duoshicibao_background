package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.AppUserShowInfoBean;
import com.runer.cibao.domain.ClassAppUsersInfoBean;
import com.runer.cibao.service.BookOrdersInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author sww
 * @Date 2019/9/26
 * 学习的详情;
 **/

@RestController
@RequestMapping(value = "classStduy")
public class ClassStudyController {

    @Autowired
    BookOrdersInfoService bookOrdersInfoService ;

    /**
     * 学员数 激活总数 小初高大学 30天内激活数
     * @return
     */
    @RequestMapping(value = "agentsSellsInfo")
    public ApiResult agentsSellsInfo(Integer monthInfo ,Long agentsId  ){
        return  bookOrdersInfoService.getAgentsSellsInfo(monthInfo,agentsId);
    }

    /**
     * 销售详情==schoolClass List
     * @param schoolId
     * @param page
     * @param limit
     * @return
     */

    @RequestMapping(value = "classListforBuying")
    public LayPageResult<ClassAppUsersInfoBean> classListforBuying(String className,Long schoolId,Integer page ,Integer limit ){
        return  bookOrdersInfoService.findClassInfos(className,schoolId,page ,limit) ;
    }

    //获得学生的销售详情；
    @RequestMapping(value = "userStudyInfoData")
    public LayPageResult<AppUserShowInfoBean> userStudyInfoDat(Long classId ,Long schoolId ,Integer page ,Integer limit ){
        return  bookOrdersInfoService.findAppUserInfos(schoolId,classId,page,limit) ;
    }
}
