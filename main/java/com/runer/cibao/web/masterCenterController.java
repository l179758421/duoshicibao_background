package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.*;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.AppUserService;
import com.runer.cibao.service.PersonalLearnBookService;
import com.runer.cibao.service.SchoolServivce;
import com.runer.cibao.service.UserLoginService;
import com.runer.cibao.util.PowerUtil;
import com.runer.cibao.util.machine.DateMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "center")
public class masterCenterController {
    @Autowired
    UserLoginService  userLoginService;

    @Autowired
    SchoolServivce schoolServivce;

    @Autowired
    AppUserService appUserService;

    @Autowired
    PersonalLearnBookService personalLearnBookService;



    @RequestMapping(value = "bookCount")
    public LayPageResult<SchoolCountBean> getBookCount(Long schoolId){
        long xxCount=0l;
        long czCount=0;
        long gzCount=0;
        long dxCount=0;
        long zyCount=0;
        long qtCount=0;
        try {
            School school = schoolServivce.findById(schoolId);
           ApiResult apiResult = appUserService.findAppUserBySchoolId(school.getUid());
           if(apiResult.isSuccess()){
               List<AppUser> appUsers = (List<AppUser>)apiResult.getData();

               for (AppUser appUser : appUsers) {
                   xxCount +=  personalLearnBookService.findBooksByUserIdAndBookStage(appUser.getId(),"小学课本");
                   czCount +=  personalLearnBookService.findBooksByUserIdAndBookStage(appUser.getId(),"初中课本");
                   gzCount +=  personalLearnBookService.findBooksByUserIdAndBookStage(appUser.getId(),"高中课本");
                   dxCount +=  personalLearnBookService.findBooksByUserIdAndBookStage(appUser.getId(),"大学课本");
                   zyCount +=  personalLearnBookService.findBooksByUserIdAndBookStage(appUser.getId(),"专业英语");
                   qtCount +=  personalLearnBookService.findBooksByUserIdAndBookStage(appUser.getId(),"其他课本");
               }
           }

        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        List<SchoolCountBean> list =new ArrayList<>();
        long total=xxCount+czCount+gzCount+dxCount+zyCount+qtCount;
        SchoolCountBean schoolCountBean = new SchoolCountBean();
        schoolCountBean.setXxCount(xxCount);
        schoolCountBean.setCzCount(czCount);
        schoolCountBean.setGzCount(gzCount);
        schoolCountBean.setDxCount(dxCount);
        schoolCountBean.setZyCount(zyCount);
        schoolCountBean.setQtConut(qtCount);
        schoolCountBean.setTotal(total);
        list.add(schoolCountBean);
        return new LayPageResult(list);
    }

}
