package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.*;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import com.runer.cibao.util.Arith;
import com.runer.cibao.util.NormalUtil;
import com.runer.cibao.util.machine.DateMachine;
import com.runer.cibao.util.machine.IdsMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author sww
 * @Date 2019/9/25  00
 * 学校管理
 **/
@RestController
@RequestMapping(value = "school")
public class SchoolController {


    @Autowired
    SchoolServivce schoolServivce;


    @Autowired
    AppUserService appUserService;

    @Autowired
    ClassInSchoolService classInSchoolService;

    @Autowired
    ProvinceService  provinceService;

    @Autowired
    CityService cityService;

    @Autowired
    SchoolMasterService schoolMasterService;

    @Autowired
    SignTimeRecordService signTimeRecordService;

    @Autowired
    OnlineTimeService onlineTimeService;

    @Autowired
    PersonalLearnInfoService personalLearnInfoService;

    @Autowired
    ClassInSchoolService beanService ;

    @Autowired
    WordLearnService wordLearnService;

    @Autowired
    IdsMachine idsMachine;

    @RequestMapping(value = "school_test_list")
    public LayPageResult<AppUser> school_test_list(Long schoolId ,String userName,Integer page ,Integer limit) {
        String schoolUid = null;
        try {
            if(schoolId != null){
                School schoolll = schoolServivce.findById(schoolId);
                schoolUid = schoolll.getUid();
            }
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        if(!StringUtils.isEmpty(schoolUid)){
            Page<AppUser> pauser = appUserService.findAppUsers(schoolUid,null,null,userName,null,null,null,page,limit);
            if(pauser != null){
                for (AppUser appUser:pauser) {

                    //测试成绩
                    PersonalLearnBooks personalLearnBooks = appUser.getPersonalLearnBooks() ;
                    List<PersonalLearnBook> personalLearnBook = new ArrayList<PersonalLearnBook>();
                    if(personalLearnBooks != null){
                        personalLearnBook = personalLearnBooks.getPersonalLearnBooks();
                    }
                    Integer num1 = 0;Integer num2 = 0;
                    double book =0;double util = 0;
                    if(personalLearnBook.size()>0){
                        for (PersonalLearnBook plb:personalLearnBook) {
                            List<PersonalTestForBook> personalTestForBook = plb.getPersonalTestForBooks();
                            if(personalTestForBook != null){
                                for (int i = 0; i <personalTestForBook.size() ; i++) {
                                    Integer score = personalTestForBook.get(i).getScore();
                                    if(score != null){
                                        num1 += score;
                                    }
                                }
                                if(personalTestForBook.size()>0){
                                    book = num2/(personalTestForBook.size());
                                }
                            }
                            List<PersonalLearnUnit> personalLearnUnits = plb.getPersonalLearnUnits();
                            if(personalLearnUnits != null){
                                for (int i = 0; i < personalLearnUnits.size(); i++) {
                                    List<PersonalTestForUnit> personalTestForUnits = personalLearnUnits.get(i).getPersonalTestForUnits();
                                    if(personalTestForUnits != null){
                                        for (int j = 0; j <personalTestForUnits.size() ; j++) {
                                            Integer score = personalTestForUnits.get(j).getScore();
                                            if(score != null){
                                                num2 += score;
                                            }
                                        }
                                        if(personalTestForUnits.size()>0){
                                            util = num2/(personalTestForUnits.size());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    String aa = String.format("%.2f", book);
                    String bb = String.format("%.2f", util);
                    appUser.setNumber1(aa);//课本测试平均成绩
                    appUser.setNumber2(bb);//单元测试平均成绩
                    //学生学习报告
                    List<SignTimeRecord> signTimeRecords = signTimeRecordService.findByUserId(appUser.getId());
                    if (signTimeRecords.size() > 0) {
                        //最近登录时间
                        appUser.setLastSignTime(signTimeRecords.get(0).getSignDate().toString());
                    }
                    List<OnlineTime> onlineTimeList1 = onlineTimeService.findByUserAndDate(appUser.getId(), new Date());
                    if (onlineTimeList1.size() > 0) {
                        //今日学习时长
                        appUser.setTodayStudyTime(Arith.dayHousMinS(onlineTimeList1.get(0).getTime()));
                    }
                    List<OnlineTime> onlineTimeList = onlineTimeService.findByUserId(appUser.getId());
                    //累计学习时长
                    long totalTime = 0l;
                    for (OnlineTime onlineTime : onlineTimeList) {
                        totalTime += onlineTime.getTime();
                    }
                    appUser.setTotalStudyTime(Arith.dayHousMinS(totalTime));
                    ApiResult apiResult = personalLearnInfoService.findOneLearn(appUser.getId());
                    //PersonlLearnInfoBean personlLearnInfoBean = (PersonlLearnInfoBean) apiResult.getData();
                   // if (personlLearnInfoBean != null) {
                        //单词记忆总量
                       // appUser.setWordNum(personlLearnInfoBean.getAllWordsCount());
                //    }
                }
            }
            return NormalUtil.createLayPageReuslt(pauser) ;
        }else {
            return new LayPageResult<>("学校uid值不存在!");
        }
    }






    @RequestMapping(value = "school_list")
    public LayPageResult<School> getSchoolList(String schoolName , Long cityId , Long provinceId , Long areaId, String address,
                                               String schoolMasterName , Date startTime , Date endTime , Integer page , Integer limit){
      Page<School> schoolPage =  schoolServivce.findSchool(schoolName,cityId,provinceId,areaId,address,schoolMasterName,startTime,endTime,null,page,limit);
        for (School schools:schoolPage) {
            Long provinceid = schools.getProvinceId();
            Long cityid = schools.getCityId();
            Long shoolid = schools.getId();
            String uid = schools.getUid();
            if(provinceid != null && cityid != null && shoolid != null){
                try {
                    if(schoolMasterService.findSchoolMasterBySchoolId(shoolid) != null){
                        schools.setMastername(schoolMasterService.findSchoolMasterBySchoolId(shoolid).getName());
                    }
                    if(provinceService.findById(provinceid) != null){
                        schools.setProvince(provinceService.findById(provinceid).getName());
                    }
                    if(cityService.findById(cityid) != null){
                        schools.setCity(cityService.findById(cityid).getName());
                    }
                } catch (SmartCommunityException e) {
                    e.printStackTrace();
                }
            }
            List<AppUser> appUsers = new ArrayList<>();
            if(!StringUtils.isEmpty(uid)){
                ApiResult apiResult = appUserService.findAppUserBySchoolId(uid);
                appUsers = (List<AppUser>) apiResult.getData();
            }
            if( appUsers != null){
                schools.setUserNumber(appUsers.size());
            }else{
                schools.setUserNumber(0);
            }

            Integer add1 = 0; Integer add2 = 0;
            if(shoolid != null){
                Page<ClassInSchool> pcis = beanService.findClassInSchool(shoolid,null,null,1,10);
                for (ClassInSchool classInSchool : pcis.getContent()) {
                    List<AppUser> appUserList = appUserService.findAppUserByClassInSchool(classInSchool.getId());
                    Date endTime1=new Date();
                    Date startTime1 =new DateMachine().getDaysBefore(7);
                    Date startTime2 =new DateMachine().getDaysBefore(30);
                    for (AppUser au:appUserList) {
                        ApiResult apiResult1 = personalLearnInfoService.findOneLearnInfoNew(au.getId(),startTime1,endTime1);
                        ApiResult apiResult2 = personalLearnInfoService.findOneLearnInfoNew(au.getId(),startTime2,endTime1);
                        List<PersonlLearnInfoBean> lp1 = (List<PersonlLearnInfoBean>) apiResult1.getData();
                        List<PersonlLearnInfoBean> lp2 = (List<PersonlLearnInfoBean>) apiResult2.getData();
                        if(lp1 != null){
                            add1 += 1;
                        }
                        if(lp2 != null){
                            add2 += 1;
                        }
                    }
                }
            }
            schools.setSeven(add1);
            schools.setThirty(add2);
        }

      return NormalUtil.createLayPageReuslt(schoolPage) ;
    }


    @RequestMapping(value = "addOrUpdateSchool")
    ApiResult addOrUpdateSchool(Long id ,String name ,Long cityId ,Long provinceId ,Long areaId, String address ,String phone ,String des ){
        if(id == null){
            if(provinceId != null && cityId != null && areaId != null){
                return   schoolServivce.addSchool(id,name,cityId,provinceId,areaId,address,phone,des);
            }else{
                return new ApiResult("请完善学校地区信息!");
            }
        }else{
            return   schoolServivce.addOrUpdateSchool(id,name,cityId,provinceId,areaId,address,phone,des);
        }
    }

    @RequestMapping("deleteByIds")
    public ApiResult deleteByIds(String ids){
        for (Long id:idsMachine.deparseIds(ids)) {
            try {
                School school = schoolServivce.findById(id);
                ApiResult apiResult = appUserService.findAppUserBySchoolId(school.getUid());
                List<AppUser> appUsers = (List<AppUser>) apiResult.getData();
                if(appUsers != null){
                    return new ApiResult("所选学校含有存在学员学校,请重新选择!");
                }
            } catch (SmartCommunityException e) {
                e.printStackTrace();
            }
        }
        return  NormalUtil.deleteByIds(schoolServivce,ids) ;
    }


    @RequestMapping("deleteById")
    public ApiResult deleteById(Long id){
        return  NormalUtil.deleteById(schoolServivce,id);
    }



    @RequestMapping("exortSchool")
    public void exportSchool( HttpServletResponse httpResponse){
        schoolServivce.exportSchoolUid(httpResponse);
    }


    @RequestMapping("getAll")
    public ApiResult getAll(){
        return  schoolServivce.getAll();
    }

}
