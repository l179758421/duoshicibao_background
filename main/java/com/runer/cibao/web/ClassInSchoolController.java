package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.*;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
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
import org.thymeleaf.util.ListUtils;

import java.util.Date;
import java.util.List;

/**
 * @Author sww
 * @Date 2019/9/26
 **/

@RestController
@RequestMapping("classInSchool")
public class ClassInSchoolController {

    @Autowired
    ClassInSchoolService beanService ;
    @Autowired
    SchoolServivce schoolServivce ;
    @Autowired
    AppUserService appUserService;
    @Autowired
    UserLoginService userLoginService ;
    @Autowired
    TeacherService teacherService;
    @Autowired
    WordLearnService wordLearnService;
    @Autowired
    IdsMachine idsMachine;
    @Autowired
    PersonalLearnInfoService personalLearnInfoService;

    @RequestMapping(value = "data_list")
    public LayPageResult<ClassInSchool> getDataList(Long schoolId,
                                                    String schoolName ,
                                                    String className ,
                                                    Integer page ,
                                                    Integer limit){
        Page<ClassInSchool> pageResult = beanService.findClassInSchool(schoolId, schoolName, className, page, limit);
        for (ClassInSchool classInSchool : pageResult.getContent()) {
            Page<Teacher> teachers= teacherService.findTeachers(schoolId,null,null,null,page,limit);
            for (Teacher teacher : teachers.getContent()) {
                String classIds = teacher.getClassIds();
                List<Long> ids = new IdsMachine().deparseIds(classIds);
                for (Long id : ids) {
                    if(id.equals(classInSchool.getId())){
                        //查找班主任
                        classInSchool.setHeadMaster(teacher.getName());
                    }
                }
            }

            List<AppUser> appUsers = appUserService.findAppUserByClassInSchool(classInSchool.getId());
            classInSchool.setStudentNum(appUsers.size());

            Date endTime = new Date();
            Date startTime1 =new DateMachine().getDaysBefore(2);
            Date startTime2 =new DateMachine().getDaysBefore(7);
            // 查找最近两天，七天学习人数
            List<AppUser> appUserList = appUserService.findAppUserByClassInSchool(classInSchool.getId());
            if(appUserList!=null){
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



        return NormalUtil.createLayPageReuslt(pageResult) ;
    }

    @RequestMapping(value = "teacher_data_list")
    public LayPageResult<ClassInSchool> teacherDataList(Long teacherId, String className, Integer page, Integer limit){
        Teacher teacher0=null;
        try {
            teacher0= teacherService.findById(teacherId);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        ApiResult classes = beanService.findByTeacherId(teacherId,className);
       List<ClassInSchool> classInSchools=( List<ClassInSchool>) classes.getData();
        for (ClassInSchool classInSchool : classInSchools) {
            if(teacher0 != null){
                if(teacher0.getSchool()!=null){
                    Page<Teacher> teachers= teacherService.findTeachers(teacher0.getSchool().getId(),null,null,null,page,limit);
                    for (Teacher teacher : teachers.getContent()) {
                        String classIds = teacher.getClassIds();
                        List<Long> ids = new IdsMachine().deparseIds(classIds);
                        for (Long id : ids) {
                            if(id.equals(classInSchool.getId())){
                                //查找班主任
                                classInSchool.setHeadMaster(teacher.getName());
                            }
                        }
                    }
                }
                List<AppUser> appUsers = appUserService.findAppUserByClassInSchool(classInSchool.getId());
                if(appUsers != null ){
                    classInSchool.setStudentNum(appUsers.size());
                }else{
                    classInSchool.setStudentNum(0);
                }
                Date endTime=new Date();
                Date startTime1 =new DateMachine().getDaysBefore(2);
                Date startTime2 =new DateMachine().getDaysBefore(7);
                // 查找最近两天，七天学习人数
                List<AppUser> appUserList = appUserService.findAppUserByClassInSchool(classInSchool.getId());
                if(appUserList!=null){
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
        }
        LayPageResult layPageResult =new LayPageResult(classInSchools) ;
        return  layPageResult ;
    }

    @RequestMapping(value = "addOrUpdateData")
    public ApiResult addOrUpdateData(Long id , Long schoolId  , String name , String classInfo , Long addUserId){
        List<ClassInSchool> classInSchools = beanService.findBySchoolId(schoolId);
        if(classInSchools != null){
            for (ClassInSchool cis:classInSchools) {
                if(cis.getName().equals(name)){
                    return new ApiResult("本学校已存在该班级,请重新录入!");
                }
            }
        }
     return    beanService.addOrUpdateClassInSchool(id,schoolId,name,classInfo,addUserId);
    }

    @RequestMapping("deleteByIds")
    public ApiResult deleteByIds(String ids){
        for (Long id:idsMachine.deparseIds(ids)) {
            List<AppUser> appUsers = appUserService.findAppUserByClassInSchool(id);
            if(appUsers != null){
                return new ApiResult("所选班级含有存在学员班级,请重新选择!");
            }
        }
        return  NormalUtil.deleteByIds(beanService,ids) ;
    }


    @RequestMapping("deleteById")
    public ApiResult deleteById(Long id){
        return  NormalUtil.deleteById(beanService,id);
    }





}
