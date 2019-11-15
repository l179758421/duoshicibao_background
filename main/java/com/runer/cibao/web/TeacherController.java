package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.ClassInSchool;
import com.runer.cibao.domain.Teacher;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.TeacherService;
import com.runer.cibao.service.UserLoginService;
import com.runer.cibao.service.UserService;
import com.runer.cibao.util.NormalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Author sww
 * @Date 2018/7/3
 * 教师Controller
 **/
@RestController
@RequestMapping(value = "teacher")
public class TeacherController {

    @Autowired
    TeacherService beanService ;


    @Autowired
    UserLoginService userLoginService ;

    @Autowired
    UserService userService;

    /**
     *
     * @param teacherId
     * @param schoolId
     * @return
     */
   @RequestMapping(value = "classesDistibute")
    public LayPageResult distibute(Long teacherId  ,Long schoolId){
        List<ClassInSchool> datas = beanService.markClasses(teacherId, schoolId);
        LayPageResult layPageResult = new LayPageResult<>();
        layPageResult.setCode(0);
        layPageResult.setCount(datas.size());
        layPageResult.setMsg("");
        layPageResult.setData(datas);
        return  layPageResult ;

    }


    @RequestMapping(value = "authorizationTeacherClass")
    public ApiResult authorizationTeacherClass(Long teacherId  ,String  ids){
        return  beanService.authorizationTeacherClass(teacherId,ids) ;
    }





    @RequestMapping(value = "data_list")
    public LayPageResult<Teacher> getDataList(Long schoolId , String  teacherName , Long classInSchoolId , String schoolUid , Integer page , Integer limit){

        Page<Teacher> pageResult = beanService.findTeachers(schoolId,teacherName , classInSchoolId, schoolUid, page,limit);

        return NormalUtil.createLayPageReuslt(pageResult) ;
    }

    @RequestMapping(value = "addOrUpdateData")
    public ApiResult addOrUpdateData(Long id ,String name ,Long schoolId,Long provinceId
            ,Long cityId ,Long areaId, String phone , String email , Integer sex, String address ,String birthDay , String headerFile){

        Date birthDayDate =null ;
        try {
            if (!StringUtils.isEmpty(birthDay)){
                birthDayDate =new SimpleDateFormat("yyyy-MM-dd").parse(birthDay);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date time = new Date();
                String now = formatter.format(time);
                time = new SimpleDateFormat("yyyy-MM-dd").parse(now);
                if(time.before(birthDayDate)){
                    return new  ApiResult("生日日期超出实际范围!");
                }
            }
            if(!StringUtils.isEmpty(email)){
                String zhengze = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
                Pattern p = Pattern.compile(zhengze);
                Matcher m =p.matcher(email);
                if(!m.matches()){
                    return new  ApiResult("邮箱格式不匹配!");
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return   beanService.addOrUpdateTeacher(id,name,schoolId,provinceId,cityId,areaId,phone,email,sex,address,birthDayDate,headerFile);
    }

    @RequestMapping("deleteByIds")
    public ApiResult deleteByIds(String ids){
        String[] id1 = ids.split(",");
        for (String id2:id1) {
            Long id3 = Long.parseLong(id2);
            try {
                Teacher teacher = beanService.findById(id3);
                if(teacher != null && teacher.getUser() != null){
                    Long userId = teacher.getUser().getId();
                    userService.deleteById(userId);
                }
            } catch (SmartCommunityException e) {
                e.printStackTrace();
            }
        }
        return  NormalUtil.deleteByIds(beanService,ids) ;
    }

    @RequestMapping("deleteById")
    public ApiResult deleteById(Long id){
        try {
            Teacher teacher = beanService.findById(id);
            if(teacher != null && teacher.getUser() != null){
                Long userId = teacher.getUser().getId();
                userService.deleteById(userId);
            }
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        return  NormalUtil.deleteById(beanService,id);
    }



}
