package com.runer.cibao.service.impl;
import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.AppUserBindSchoolDao;
import com.runer.cibao.domain.AppUser;
import com.runer.cibao.domain.AppUserBindSchool;
import com.runer.cibao.domain.School;
import com.runer.cibao.domain.User;
import com.runer.cibao.domain.repository.AppUserBindSchoolRepository;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.AppUserBindSchoolService;
import com.runer.cibao.service.AppUserService;
import com.runer.cibao.service.SchoolServivce;
import com.runer.cibao.service.UserService;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.runer.cibao.exception.ResultMsg.SUCCESS;
import static com.runer.cibao.exception.ResultMsg.USER_IS_BINDED_SCHOOL;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/7/2
 **/
@Service
public class AppUserBindSchoolServiceImpl extends BaseServiceImp<AppUserBindSchool, AppUserBindSchoolRepository> implements AppUserBindSchoolService {

    @Autowired
    AppUserBindSchoolDao appUserBindSchoolDao ;

    @Autowired
    SchoolServivce schoolServivce ;


    @Autowired
    AppUserService appUserService ;


    @Autowired
    UserService userService;



    @Override
    public ApiResult bindSchool(Long userId, String schoolUid) {

       ApiResult schoolResult= getBindInfo(schoolUid);

       if (schoolResult.isFailed()){
       return  schoolResult ;
       }

        School school = (School) schoolResult.getData();
        ApiResult userResult =appUserService.findByIdWithApiResult(userId);


       if (userResult.isFailed()){
           return  userResult;
       }

       Page<AppUserBindSchool> binds = appUserBindSchoolDao.findAppUserBinds(null, school.getId(), userId, null, PageableUtil.basicPage(1, 10));

       if (!ListUtils.isEmpty(binds.getContent())){
           return  new ApiResult("您申请过学校绑定");
       }

       AppUser appUser = (AppUser) userResult.getData();
       AppUserBindSchool appUserBindSchool =new AppUserBindSchool() ;
       appUserBindSchool.setSchoolUid(schoolUid);
       appUserBindSchool.setUserUid(appUser.getUid());
       appUserBindSchool.setAppUser(appUser);
       appUserBindSchool.setSchool(school);
       appUserBindSchool.setState(Config.TO_PASSED_STATE);
       appUserBindSchool.setApplyDate(new Date());
       appUserBindSchool.setAgreeDate(null);
       appUserBindSchool.setUser(null);
       appUserBindSchool = r.save(appUserBindSchool);

       return new ApiResult(SUCCESS,appUserBindSchool);
    }

    @Override
    public ApiResult bindAppUser(Long schoolId, String userUid) {


      ApiResult userResult =  appUserService.findAppUserByUid(userUid);
      if (userResult.getMsgCode()!=SUCCESS.getMsgCode()){
          return  userResult ;
      }
      AppUser appUser = (AppUser) userResult.getData();
      if (!StringUtils.isEmpty(appUser.getSchoolId())){
          return  new ApiResult(USER_IS_BINDED_SCHOOL,null) ;
      }
      ApiResult schoolResult = schoolServivce.findByIdWithApiResult(schoolId);
      if (schoolResult.getMsgCode()!=SUCCESS.getMsgCode()){
          return  schoolResult ;
      }
      School school = (School) schoolResult.getData();

      appUser.setSchoolId(school.getUid());
      appUser.setSchoolName(school.getName());

      return new ApiResult(SUCCESS,appUser);
    }

    @Override
    public ApiResult agreeBindSchool(Long bindId, Long userId) {

        ApiResult userResult =userService.findByIdWithApiResult(userId);
        if (userResult.getMsgCode()!=SUCCESS.getMsgCode()){
            return  userResult ;
        }
        User user = (User) userResult.getData();

        ApiResult bindResult =findByIdWithApiResult(bindId) ;
        if (bindResult.getMsgCode()!=SUCCESS.getMsgCode()){
            return  bindResult ;
        }
        AppUserBindSchool appUserBindSchool = (AppUserBindSchool) bindResult.getData();


        appUserBindSchool.setState(Config.PASSED_STATE);

        appUserBindSchool.setUser(user);

        appUserBindSchool.setAgreeDate(new Date());
        appUserBindSchool =  r.saveAndFlush(appUserBindSchool);

        //对appUser的信息的更改
        AppUser appUser =appUserBindSchool.getAppUser();
        appUser.setSchoolId(appUserBindSchool.getSchoolUid());
        appUser.setSchoolName(appUserBindSchool.getSchool().getName());
        try {
            appUserService.saveOrUpdate(appUser);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        return new ApiResult(SUCCESS,appUserBindSchool);
    }

    @Override
    public ApiResult agreeBinds(String ids, Long userId) {

        ApiResult userResult =userService.findByIdWithApiResult(userId);
        if (userResult.getMsgCode()!=SUCCESS.getMsgCode()){
            return  userResult ;
        }
        User user = (User) userResult.getData();

        try {
            List<AppUserBindSchool> datas = findByIds(ids);
            datas.forEach(appUserBindSchool -> {
                appUserBindSchool.setUser(user);
                appUserBindSchool.setState(Config.PASSED_STATE);
                appUserBindSchool.setAgreeDate(new Date());
            });
            r.saveAll(datas) ;

            List<AppUser> appUsers =new ArrayList<>() ;
            datas.forEach(appUserBindSchool -> {
                AppUser appUser =appUserBindSchool.getAppUser() ;
                appUser.setSchoolId(appUserBindSchool.getSchoolUid());
            });
            appUserService.saveOrUpdate(appUsers);
            return  new ApiResult(SUCCESS,null) ;

        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null) ;
        }


    }

    @Override
    public ApiResult getBindInfo(String schoolUid) {
        ApiResult schoolResult= schoolServivce.findSchoolByUID(schoolUid);
        return  schoolResult ;
    }

    @Override
    public Page<AppUserBindSchool> findSchoolBinds(Long userId, Long adminUserId, String  studentName,Long schoolId  ,Integer state, Integer page, Integer limit) {
        return appUserBindSchoolDao.findAppUserBinds(studentName,schoolId,userId,state,PageableUtil.basicPage(page,limit));
    }
}
