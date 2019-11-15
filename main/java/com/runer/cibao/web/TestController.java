package com.runer.cibao.web;

import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.dao.PersonalTestForBookDao;
import com.runer.cibao.domain.*;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import com.runer.cibao.util.Arith;
import com.runer.cibao.util.NormalUtil;
import com.runer.cibao.util.PowerUtil;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.runer.cibao.exception.ResultMsg.SUCCESS;

@RestController
@RequestMapping(value = "test")
public class TestController {

    @Autowired
    UserLoginService userLoginService;
    @Autowired
    AppUserService appUserService;
    @Autowired
    SchoolServivce schoolServivce;
    @Autowired
    TeacherService teacherService;
    @Autowired
    BookUnitService bookUnitService;
    @Autowired
    PersonalLearnBookService personalLearnBookService;
    @Autowired
    PersonalTestForBookService personalTestForBookService;
    @Autowired
    PersonalTestForUnitService personalTestForUnitService;
    @Autowired
    PersonalTestApiService personalTestApiService;
    @Autowired
    ClassInSchoolService classInSchoolService;
    @Autowired
    WordLearnService wordLearnService;
    @Autowired
    CommentService commentService;
    @Autowired
    IdsMachine idsMachine;
    @Autowired
    UserReviewService userReviewService;
    @Autowired
    MessageService beanService;
    @Autowired
    PersonalTestForBookDao personalTestForBookDao;
    @Autowired
    PersonalLearnInfoService personalLearnInfoService;
    @Autowired
    MedalsService medalsService;
    @Autowired
    LearnBookService learnBookService;




    @RequestMapping(value = "data_list")
    public LayPageResult<TestScoreBean> getData(Long teacherId, Long schoolId, String className, Integer page, Integer limit) {
        if (teacherId != null) {
            try {
                Teacher teacher = teacherService.findById(teacherId);
                if (teacher.getSchool() != null) {
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
            if (appUserList != null) {
                Integer list1 = 0;
                Integer list2 = 0;
                for (AppUser au : appUserList) {
                    ApiResult apiResult1 = personalLearnInfoService.findOneLearnInfoNew(au.getId(), startTime1, endTime);
                    ApiResult apiResult2 = personalLearnInfoService.findOneLearnInfoNew(au.getId(), startTime2, endTime);
                    List<PersonlLearnInfoBean> lp1 = (List<PersonlLearnInfoBean>) apiResult1.getData();
                    List<PersonlLearnInfoBean> lp2 = (List<PersonlLearnInfoBean>) apiResult2.getData();
                    if (lp1 != null) {
                        list1 += 1;
                    }
                    if (lp2 != null) {
                        list2 += 1;
                    }
                }
                classInSchool.setWithInTwoNum(list1);
                classInSchool.setWithInSevenNUm(list2);
            } else {
                classInSchool.setWithInTwoNum(0);
                classInSchool.setWithInSevenNUm(0);
            }
        }
        return NormalUtil.createLayPageReuslt(classInSchools);
    }



    @RequestMapping(value = "student_data_list")
    public LayPageResult<AppUser> getDataList(Long classId, String userName, Integer page, Integer limit) {

        Page<AppUser> users = appUserService.findAppUsers(null, null, classId, userName, null, null, null, page, limit);
        for (AppUser appUser:users){
//            if(!TextUtils.isBlank(appUser.getRealNameForInfo())){
//                appUser.setName(appUser.getRealNameForInfo());
//            }
            appUser.setName(appUser.getRealNameForInfo()==null?"":appUser.getRealNameForInfo());
        }
        return NormalUtil.createLayPageReuslt(users);
    }




    @RequestMapping(value = "getUnitDetailData")
    public LayPageResult<BookUnit> getUnitDetailData(Long bookId, Long userId, String unitName, Integer page, Integer limit) {
        Page<BookUnit> bookUnitPage = bookUnitService.findByBookId(bookId, unitName, page, limit);
        List<BookUnit> units = bookUnitPage.getContent();
        for (BookUnit unit : units) {
            Page<PersonalTestForUnit> personalTestForUnitPage = personalTestForUnitService.findUnitTestOrderByDate(userId, unit.getId(), page, limit);
            List<PersonalTestForUnit> personalTestForUnits = personalTestForUnitPage.getContent();
            if (personalTestForUnits.size() > 0) {
                PersonalTestForUnit testUnit = personalTestForUnits.get(0);
                unit.setTestScore(testUnit.getScore());
                Long test = testUnit.getTotalTestTime();
                Long pass = testUnit.getTestTime();
                unit.setTestTime(Arith.dayHousMinS(test));
                unit.setPassTime(Arith.dayHousMinS(pass));
            } else {
                unit.setTestScore(0);
                unit.setTestTime("");
                unit.setPassTime("");
            }

            //课本每单元的语音复习次数
            ApiResult apiResult1 = userReviewService.getone(userId, bookId, unit.getId(), 1);
            UserRevivews lu1 = (UserRevivews) apiResult1.getData();
            if (lu1 != null) {
                unit.setUnitTypeNumber1(lu1.getTimes());
            }
            //智能默写次数
            ApiResult apiResult4 = userReviewService.getone(userId, bookId, unit.getId(), 4);
            UserRevivews lu4 = (UserRevivews) apiResult4.getData();
            if (lu4 != null) {
                unit.setUnitTypeNumber4(lu4.getTimes());
            }
            //句子填空次数
            ApiResult apiResult5 = userReviewService.getone(userId, bookId, unit.getId(), 5);
            UserRevivews lu5 = (UserRevivews) apiResult5.getData();
            if (lu5 != null) {
                unit.setUnitTypeNumber5(lu5.getTimes());
            }
            //句式练习次数
            ApiResult apiResult6 = userReviewService.getone(userId, bookId, unit.getId(), 6);
            UserRevivews lu6 = (UserRevivews) apiResult6.getData();
            if (lu6 != null) {
                unit.setUnitTypeNumber6(lu6.getTimes());
            }
        }
        return NormalUtil.createLayPageReuslt(bookUnitPage);
    }


    @RequestMapping(value = "exportExcel")
    public void exportsExcle(Long schoolId, Long classId, HttpServletResponse response) {
        List<PersonalTestForBook> list = new ArrayList<>();
        Page<AppUser> appUsers = appUserService.findAppUsers(null, schoolId, classId, null, null, null, null, 1, Integer.MAX_VALUE);
        for (AppUser appUser : appUsers.getContent()) {
            appUser.setName(appUser.getRealNameForInfo() == null ? "" : appUser.getRealNameForInfo());
            ApiResult apiResult = personalTestForBookService.findTestByUserIdAndBookId(appUser.getId(), null);
            if (apiResult.isSuccess()) {
                List<PersonalTestForBook> personalTestForBooks = (List<PersonalTestForBook>) apiResult.getData();
                if (personalTestForBooks.size() > 0) {
                    list.add(personalTestForBooks.get(0));
                }
            }
        }
        personalTestForBookService.export2Excel(list, "BookTest", response);
    }



    @RequestMapping(value = "exportPersonalExcel")
    public void exportPersonalExcel(Long userId, HttpServletResponse response) {
        Page<PersonalTestForUnit> personalTestForUnitPage = personalTestForUnitService.findUnitTests(userId, null, null, null, null, null, 1, Integer.MAX_VALUE);
        personalTestForUnitService.export2PersonalExcel(personalTestForUnitPage.getContent(), "unitTest", response);
    }



    @RequestMapping(value = "learnBookData")
    public LayPageResult<PersonalLearnBook> learnBookData(Long userId, Integer page, Integer limit) {
        Page<PersonalLearnBook> personalLearnBookPage = personalLearnBookService.findPersonalLearnBooks(userId, null, null, null, page, limit);
        // 最后一次测试时间
        for (PersonalLearnBook personalLearnBook : personalLearnBookPage.getContent()) {
            ApiResult apiResult = personalTestForBookService.findTestByUserIdAndBookId(userId, personalLearnBook.getLearnBook().getId());
            if (apiResult.isSuccess()) {
                List<PersonalTestForBook> personalTestForBooks = (List<PersonalTestForBook>) apiResult.getData();
                if (personalTestForBooks.size() > 0) {
                    for (PersonalTestForBook ptfb : personalTestForBooks) {
                        //学前测试成绩  学后测试成绩
                        if (ptfb.getIsPreLearnTest() == 1) {
                            personalLearnBook.setLearnBeforeScore(ptfb.getScore().toString());
                            break;
                        }
                    }
                    for (PersonalTestForBook ptfb : personalTestForBooks) {
                        if (ptfb.getIsPreLearnTest() == 0) {
                            if (ptfb.getScore() != null && ptfb.getScore() != 0) {
                                personalLearnBook.setLearnAfterScore(ptfb.getScore().toString());
                                break;
                            }
                        }
                    }
                }
            }
            if (StringUtils.isEmpty(personalLearnBook.getLearnAfterScore())) {
                personalLearnBook.setLearnAfterScore("0");
            }
            if (StringUtils.isEmpty(personalLearnBook.getLearnBeforeScore())) {
                personalLearnBook.setLearnBeforeScore("0");
            }
            //TODO 老师点评？？
        }
        return NormalUtil.createLayPageReuslt(personalLearnBookPage);
    }



    @RequestMapping(value = "saveComment")
    public ApiResult save(HttpServletRequest req,String comment, Long userId, Long unitId) {

        Comment comment1 = (Comment) commentService.saveComment(comment, userId, unitId).getData();
        Long commentId = comment1.getId();
        User user = (User) userLoginService.getCurrentUser(req);
        try {
            AppUser appUser = appUserService.findById(userId);
            if (user != null && appUser.getClassInSchool() != null) {
                Long classInSchoolId = appUser.getClassInSchool().getId();
                if (commentId != null) {
                    beanService.generateStudentMessage(comment, "教师点评", null, classInSchoolId, Config.CLASS_MSG, commentId, user.getId());
                } else {
                    return new ApiResult("提交失败!");
                }
            } else {
                return new ApiResult("用户id不能为空,学员所在班级也不能为空!");
            }
            return new ApiResult(SUCCESS, null);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return new ApiResult(e.getResultMsg(), null);
        }
    }

}