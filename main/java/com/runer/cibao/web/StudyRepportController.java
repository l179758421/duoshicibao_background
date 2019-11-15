package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.*;
import com.runer.cibao.domain.person_word.NewReviewRecord;
import com.runer.cibao.domain.repository.PersonlLearnInfoRepository;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import com.runer.cibao.util.Arith;
import com.runer.cibao.util.NormalUtil;
import com.runer.cibao.util.PowerUtil;
import com.runer.cibao.util.machine.DateMachine;
import com.runer.cibao.util.machine.IdsMachine;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.ListUtils;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "report")
public class StudyRepportController {

    @Autowired
    UserLoginService userLoginService;
    @Autowired
    AppUserService appUserService;
    @Autowired
    NewReviceRecordService newReviceRecordService ;
    @Autowired
    SchoolServivce schoolServivce;
    @Autowired
    TeacherService teacherService;
    @Autowired
    ClassInSchoolService classInSchoolService;
    @Autowired
    WordLearnService wordLearnService;
    @Autowired
    SignTimeRecordService signTimeRecordService;
    @Autowired
    PersonalLearnInfoService personalLearnInfoService;
    @Autowired
    OnlineTimeService onlineTimeService;
    @Autowired
    PunshCardService punshCardService;
    @Autowired
    IdsMachine idsMachine ;
    @Autowired
    LearnTimeService learnTimeService;
    @Autowired
    PersonalTestForBookService personalTestForBookService;
    @Autowired
    UserReviewService userReviewService;
    @Autowired
    BookUnitService bookUnitService;
    @Autowired
    PersonlLearnInfoRepository personlLearnInfoRepository;






    @RequestMapping(value = "getUserReport")
    public LayPageResult<AppUserDetail> getUserDetail(Long teacherId, String userName, Integer page, Integer limit) {
        List<AppUserDetail> list = new ArrayList<>();
        Page<AppUser> appUserPage = appUserService.findAppUsers(null, null, null,
                userName, teacherId, null, null, page, limit);
        for (AppUser appUser : appUserPage.getContent()) {
            AppUserDetail appUserDetail = appUserService.findAppUserDetail(appUser.getId());
            list.add(appUserDetail);
        }
        LayPageResult<AppUserDetail> pageResult = new LayPageResult<>();
        pageResult.setCode(0);
        pageResult.setCount(list == null ? 0 : list.size());
        pageResult.setData(list);

        return pageResult;
    }

    @RequestMapping(value = "getMasterUserReport")
    public LayPageResult<AppUserDetail> getMasterUserReport(String schoolUid, String userName) {
        List<AppUserDetail> list = new ArrayList<>();

        Page<AppUser> appUserPage = appUserService.findAppUsers(schoolUid, null, null,
                userName, null, null, null, 1, Integer.MAX_VALUE);
        if (appUserPage != null) {
            List<AppUser> appUserList = appUserPage.getContent();
            for (AppUser appUser : appUserList) {
                AppUserDetail appUserDetail = appUserService.findAppUserDetail(appUser.getId());
                list.add(appUserDetail);
            }
        }
        LayPageResult<AppUserDetail> pageResult = new LayPageResult<>();
        pageResult.setCode(0);
        pageResult.setCount(list == null ? 0 : list.size());
        pageResult.setData(list);
        return pageResult;
    }


    @RequestMapping(value = "exportExcel")
    public void exportsExcle(Long classId, HttpServletResponse response) {
         appUserService.exportReportExcel(classId, "classReport", response);
    }



    @RequestMapping(value = "exportPersonalExcel")
    public void exportPersonalExcel(Long userId, HttpServletResponse response) {
        appUserService.personalReportExcel2(userId, "studyTest", response);
    }

    @RequestMapping(value = "class_data_list")
    public LayPageResult<TestScoreBean> getData(Long teacherId, Long schoolId, String className, Integer page, Integer limit) {
        if (teacherId != null) {
            try {
                Teacher teacher = teacherService.findById(teacherId);
                if(teacher.getSchool() != null) {
                    schoolId = teacher.getSchool().getId();
                }
            } catch (SmartCommunityException e) {
                e.printStackTrace();
            }
        }

        Page<ClassInSchool> classInSchools = classInSchoolService.findClassInSchool(schoolId, null, className, page, limit);
        for (ClassInSchool classInSchool : classInSchools.getContent()) {
            Page<Teacher> teachers = teacherService.findTeachers(schoolId, null, null, null, page, limit);
            for (Teacher teacher : teachers.getContent()) {
                String classIds = teacher.getClassIds();
                List<Long> ids = new IdsMachine().deparseIds(classIds);
                for (Long id : ids) {
                    if (id.equals(classInSchool.getId())) {
                        //查找班主任
                        classInSchool.setHeadMaster(teacher.getName());
                    }
                }
            }
            List<AppUser> appUsers = appUserService.findAppUserByClassInSchool(classInSchool.getId());
            classInSchool.setStudentNum(appUsers.size());

            Date endTime = new Date();
            Date startTime1 = new DateMachine().getDaysBefore(2);
            Date startTime2 = new DateMachine().getDaysBefore(7);
            // 查找最近两天，七天学习人数

            List<AppUser> appUserList = appUserService.findAppUserByClassInSchool(classInSchool.getId());
            if(!ListUtils.isEmpty(appUserList)){
                Integer list1 = 0;
                Integer list2 = 0;
                for (AppUser au: appUserList) {
                    ApiResult apiResult1 = personalLearnInfoService.findOneLearnInfoNew(au.getId(),startTime1,endTime);
                    ApiResult apiResult2 = personalLearnInfoService.findOneLearnInfoNew(au.getId(),startTime2,endTime);
                    List<PersonlLearnInfoBean> lp1 = (List<PersonlLearnInfoBean>) apiResult1.getData();
                    List<PersonlLearnInfoBean> lp2 = (List<PersonlLearnInfoBean>) apiResult2.getData();
                    if(!ListUtils.isEmpty(lp1)){
                        list1 += 1;
                    }
                    if(!ListUtils.isEmpty(lp2)){
                        list2 += 1;
                    }
                }
                classInSchool.setWithInTwoNum(list1);
                classInSchool.setWithInSevenNUm(list2);
            }else{
                classInSchool.setWithInTwoNum(0);
                classInSchool.setWithInSevenNUm(0);
            }
        }

        return NormalUtil.createLayPageReuslt(classInSchools);

    }



    @RequestMapping(value = "student_data_list")
    public LayPageResult<AppUser> studentData(Long classId, String userName, Integer page, Integer limit) {
        Page<AppUser> appUserPage = appUserService.findAppUsers(null, null, classId, userName, null, null, null, page, limit);
        for (AppUser appUser : appUserPage.getContent()) {
            appUser.setName(appUser.getRealNameForInfo()==null?"":appUser.getRealNameForInfo());
            ApiResult apiResult0 = schoolServivce.findSchoolByUID(appUser.getSchoolId());
            School school = (School) apiResult0.getData();
            if(school != null) {
                Page<Teacher> teachers = teacherService.findTeachers(school.getId(), null, null, null, 1, Integer.MAX_VALUE);
                for (Teacher teacher : teachers.getContent()) {
                    String classIds = teacher.getClassIds();
                    List<Long> ids = new IdsMachine().deparseIds(classIds);
                    for (Long id : ids) {
                        if (id.equals(appUser.getClassInSchool().getId())) {
                            //查找班主任
                            appUser.setTeacher(teacher.getName());
                        }
                    }
                }
            }else{
                appUser.setTeacher("");
            }
            List<SignTimeRecord> signTimeRecords = signTimeRecordService.findByUserId(appUser.getId());
            if (signTimeRecords.size() > 0) {
                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                appUser.setLastSignTime(dateFormat.format(signTimeRecords.get(0).getSignDate())); //最近登录时间
                SignTimeRecord signTimeRecord = signTimeRecords.get(signTimeRecords.size() - 1);
                //开始学习时间
                appUser.setBenginStartTime(dateFormat.format(signTimeRecord.getSignDate()));
                ApiResult apiResult1 = punshCardService.findCards(appUser.getId(), null, null);
                if (apiResult1 != null) {
                    List<PunchCard> punchCards = (List<PunchCard>) apiResult1.getData();
                    //打卡天数
                    appUser.setPunchCardsDays(punchCards.size());
                }
            }
            List<OnlineTime> onlineTimeList = onlineTimeService.findByUserId(appUser.getId());
            long totalTime = 0L;
            for (OnlineTime onlineTime : onlineTimeList) {
                totalTime += onlineTime.getTime();
            }
            appUser.setTotalStudyTime(Arith.dayHousMinS(totalTime));    //累计在线时长

            Long learnTime =0L;
            List<LearnTime> learnTimes = learnTimeService.getLearnTime(appUser.getId());
            for (LearnTime lt:learnTimes) {
                learnTime+=lt.getTime();
            }
            appUser.setTodayStudyTime(Arith.dayHousMinS(learnTime/1000));   //累计学习时长

            List<OnlineTime> onlineTimeList1 = onlineTimeService.findByUserAndDate(appUser.getId(), new Date());
            Long time1 = 0L;
            if (onlineTimeList1 != null) {
                for (OnlineTime ot:onlineTimeList1) {
                    time1 += ot.getTime();
                }
                appUser.setTotalNewOnlineTime(Arith.dayHousMinS(time1)); //今日在线时长
            }else{
                appUser.setTotalNewOnlineTime("0秒");
            }
            ApiResult apiResult2 = learnTimeService.getUploadLearnTime(appUser.getId(),new Date());
            List<LearnTime> learnTimes1 = (List<LearnTime>) apiResult2.getData();
            Long time2 = 0L;
            if(learnTimes1 != null){
                for (LearnTime lt:learnTimes1) {
                    time2 += lt.getTime();
                }
                appUser.setTotalNewVolidTime(Arith.dayHousMinS(time2/1000)); //今日有效学习时长
            }else{
                appUser.setTotalNewVolidTime("0秒");
            }

            ApiResult apiResult = personalLearnInfoService.findOneLearn(appUser.getId());
            List<PersonlLearnInfoBean> personlLearnInfoBean = (List<PersonlLearnInfoBean>) apiResult.getData();
            Long number = 0L;
            if (personlLearnInfoBean != null) {
                for (PersonlLearnInfoBean pl:personlLearnInfoBean) {
                    Long index = pl.getWordsNumNew();
                    number += index;
                }
            }
            appUser.setWordNum(number);//单词记忆总量
            ApiResult apiResult1 = personalLearnInfoService.generateOneLearnInfo(appUser.getId(),new Date());
            PersonlLearnInfoBean personlLearnInfoBean1 = (PersonlLearnInfoBean) apiResult1.getData();
            appUser.setNewWordNum(personlLearnInfoBean1.getWordsNumNew());  //今日学习单词数量

            ApiResult apiResult3 = personalLearnInfoService.findOneLearn(appUser.getId());
            List<PersonlLearnInfoBean> personlLearnInfoBeans = (List<PersonlLearnInfoBean>) apiResult3.getData();
            Long wordsNumReview = 0L;
            for (PersonlLearnInfoBean pp: personlLearnInfoBeans) {
                wordsNumReview += pp.getWordsNumReview();
            }
            appUser.setWordsNumReview(wordsNumReview);  //复习单词数量
            if(appUser.getPersonalLearnBooks() != null){
                List<PersonalLearnBook> personalLearnBooks = appUser.getPersonalLearnBooks().getPersonalLearnBooks();
                for (PersonalLearnBook pl:personalLearnBooks) {
                    if(pl.getIsCurrentBook() == 1 && pl.getLearnBook()!=null){
                        appUser.setLearnBookName(pl.getLearnBook().getBookName());
                        appUser.setLearnBookWords(String.valueOf(pl.getCurrentWordNum())+"/"+pl.getLearnBook().getWordsNum());//学习进度
                    }
                }
            }
        }
        return NormalUtil.createLayPageReuslt(appUserPage);
    }


    @RequestMapping(value = "personalReport")
    public LayPageResult<PersonalReportBean> getPersonalReport(Long userId) {
        List<PersonalReportBean> list = new ArrayList<>();
        PersonalReportBean personalReportBean = new PersonalReportBean();
        try {
            AppUser appUser = appUserService.findById(userId);
            personalReportBean.setName(appUser.getName());
            personalReportBean.setClassName(appUser.getClassInSchool().getName());
            personalReportBean.setPhone(appUser.getPhoneNum());
            ApiResult apiResult = schoolServivce.findSchoolByUID(appUser.getSchoolId());
            School school = (School) apiResult.getData();
            if(school != null){
                Page<Teacher> teachers = teacherService.findTeachers(school.getId(), null, null, null, 1, Integer.MAX_VALUE);
                for (Teacher teacher : teachers.getContent()) {
                    String classIds = teacher.getClassIds();
                    List<Long> ids = new IdsMachine().deparseIds(classIds);
                    for (Long id : ids) {
                        if (id.equals(appUser.getClassInSchool().getId())) {
                            //查找班主任
                            personalReportBean.setHeadMaster(teacher.getName());
                        }
                    }
                }
            }
            List<SignTimeRecord> signTimeRecords = signTimeRecordService.findByUserId(appUser.getId());
            if (signTimeRecords.size() > 0) {
                SignTimeRecord signTimeRecord = signTimeRecords.get(signTimeRecords.size() - 1);
                //开始学习时间
                personalReportBean.setBenginStartTime(signTimeRecord.getSignDate().toString());
                ApiResult apiResult1 = punshCardService.findCards(appUser.getId(), null, null);
                if (apiResult1 != null) {
                    List<PunchCard> punchCards = (List<PunchCard>) apiResult1.getData();
                    //打卡天数
                    personalReportBean.setCardDays(punchCards.size());
                }
            }
            if(appUser.getPersonalLearnBooks() != null){
                List<PersonalLearnBook> personalLearnBooks = appUser.getPersonalLearnBooks().getPersonalLearnBooks();
                for (PersonalLearnBook pl:personalLearnBooks) {
                    if(pl.getIsCurrentBook() == 1 && pl.getLearnBook()!=null){
                        personalReportBean.setLearnBookWords(String.valueOf(pl.getCurrentWordNum())+"/"+pl.getLearnBook().getWordsNum());//学习进度
                    }
                }
            }else{
                personalReportBean.setLearnBookWords("");
            }

            ApiResult apiResult2 = personalLearnInfoService.findOneLearn(appUser.getId());
            List<PersonlLearnInfoBean> personlLearnInfoBean = (List<PersonlLearnInfoBean>) apiResult2.getData();
            Long number = 0L;
            if (personlLearnInfoBean != null) {
                for (PersonlLearnInfoBean pl:personlLearnInfoBean) {
                    Long index = pl.getWordsNumNew();
                    number += index;
                }
            }
            personalReportBean.setWordNum(number); //累计单词数

        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        list.add(personalReportBean);

        return new LayPageResult(list);
    }

}
