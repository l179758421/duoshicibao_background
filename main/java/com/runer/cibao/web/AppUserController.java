package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.*;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import com.runer.cibao.util.NormalUtil;
import com.runer.cibao.util.PowerUtil;
import com.runer.cibao.util.machine.IdsMachine;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author sww
 * @Date 2019/9/22
 * 学员列表
 **/
@RestController
@RequestMapping(value = "appUser")
public class AppUserController {

    @Autowired
    AppUserService beanService ;
    @Autowired
    UserLoginService userLoginService;
    @Autowired
    ClassInSchoolService classInSchoolService ;
    @Autowired
    SignTimeRecordService signTimeRecordService;
    @Autowired
    OnlineTimeService onlineTimeService;
    @Autowired
    PersonalLearnInfoService personalLearnInfoService;
    @Autowired
    PersonalLearnBooksService personalLearnBooksService;
    @Autowired
    PersonalLearnBookService personalLearnBookService;
    @Autowired
    SchoolServivce schoolServivce ;
    @Autowired
    IdsMachine idsMachine;




    @RequestMapping(value = "data_list")
    public LayPageResult<AppUser> getDataList(Long teacherId , Long classInSchoolId ,Long schoolId ,String schoolUID ,String userName  , Integer page ,Integer limit){
        if (StringUtils.isEmpty(schoolUID)){
            Member member = (Member) userLoginService.getCurrentMember().getData();
            if (member.getSchoolMaster()!=null){
                schoolUID =member.getSchoolMaster().getSchool().getUid();
            }
        }
        Page<AppUser> users = beanService.findAppUsers(schoolUID, null, classInSchoolId, userName ,teacherId, null,null , page, limit);
        for (AppUser appUser:users){
            if(!TextUtils.isBlank(appUser.getRealNameForInfo())){
                appUser.setName(appUser.getRealNameForInfo());
            }
        }
        return NormalUtil.createLayPageReuslt(users);
    }

    @RequestMapping(value = "data_list2")
    public LayPageResult<AppUser> getDataListC(Long teacherId , Long classInSchoolId ,Long schoolId ,String schoolUID ,String userName  , Integer page ,Integer limit){
        if (StringUtils.isEmpty(schoolUID)){
            Member member = (Member) userLoginService.getCurrentMember().getData();
            if (member.getSchoolMaster()!=null){
                schoolUID =member.getSchoolMaster().getSchool().getUid();
            }
        }
        Page<AppUser> users = beanService.findAppUsersC(schoolUID, null, classInSchoolId, userName ,null, null,null , page, limit);
        return NormalUtil.createLayPageReuslt(users);
    }

    @RequestMapping(value = "distributionUserApi")
    public ApiResult distributionUserApi(Long userId ,Long classInSchoolId){
        return  beanService.distributeClassInschool(userId,classInSchoolId);
    }

    @RequestMapping(value = "getUserDetail")
    public String getUserDetail(ModelMap map ,Long userId){
       //AppUserDetail appUserDetail = beanService.findAppUserDetail(userId);
        try {
            AppUser appUser =  beanService.findById(userId);
            map.put("data",appUser);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }

        return "app_user/detail_student";

    }

    @RequestMapping(value = "addUserApi")
    public ApiResult addUser(String uid ,Long schoolId){

        return  beanService.addUserToSChool(uid,schoolId) ;
    }


    @RequestMapping(value = "deleteUserByID")
    public ApiResult deleteUserByID(Long id){
         ApiResult apiResult = personalLearnBookService.findAllByUserId(id);
         List<PersonalLearnBook> personalLearnBooks = (List<PersonalLearnBook>) apiResult.getData();
         if(personalLearnBooks != null){
             return  beanService.uniteSchools(id) ;
         }
        return new ApiResult("该学员已学习!");
    }

    @RequestMapping(value = "deleteUserByIDs")
    public ApiResult deleteUserByIDs(String ids){
        for (Long id:idsMachine.deparseIds(ids)) {
            ApiResult apiResult = personalLearnBookService.findAllByUserId(id);
            List<PersonalLearnBook> personalLearnBooks = (List<PersonalLearnBook>) apiResult.getData();
            if(personalLearnBooks == null){
                return  new ApiResult("所选学员含有存在学习的学员,请重新选择!");
            }
            try {
                AppUser appUser = beanService.findById(id);
                int count = 0;
                PersonalLearnBooks books = appUser.getPersonalLearnBooks();
                if (books != null) {
                    count = books.getPersonalLearnBooks() == null ? 0 : books.getPersonalLearnBooks().size();
                }
                if(count != 0){
                    return new ApiResult("所选学员含有已开通课本的学员,请重新选择!");
                }
            } catch (SmartCommunityException e) {
                e.printStackTrace();
            }
        }
        return  beanService.uniteSchoolsWithIds(ids);
    }

    @RequestMapping(value = "perfectUserInfo")
    public ApiResult perfectUserInfo(Long id, String phoneNum, String name){
      return   beanService.perfectUserInfo(id,phoneNum,name) ;
    }
}
