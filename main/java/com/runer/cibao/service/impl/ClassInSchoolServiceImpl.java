package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.ClassInSchoolDao;
import com.runer.cibao.domain.ClassInSchool;
import com.runer.cibao.domain.School;
import com.runer.cibao.domain.repository.ClassInSchoolRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.ClassInSchoolService;
import com.runer.cibao.service.TeacherService;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/7
 **/

@Service
public class ClassInSchoolServiceImpl extends BaseServiceImp<ClassInSchool, ClassInSchoolRepository> implements ClassInSchoolService {


    @Autowired
    ClassInSchoolDao classInSchoolDao ;

    @Autowired
    TeacherService teacherService ;



    @Override
    public Page<ClassInSchool> findClassInSchool(Long schoolId, String schoolName, String className, Integer page, Integer limit) {
      return  classInSchoolDao.findClassInSchool(schoolId,schoolName,className,PageableUtil.basicPage(page,limit));
    }

    @Override
    public ApiResult addOrUpdateClassInSchool(Long id, Long schoolId, String name, String classInfo, Long addUserId) {

        ClassInSchool classInSchool =new ClassInSchool() ;

        if (id!=null){
            try {
                classInSchool =findById(id);
            } catch (SmartCommunityException e) {
                e.printStackTrace();
                return  new ApiResult(e.getResultMsg(),null);
            }
        }

        School school =new School() ;
        school.setId(schoolId);

        classInSchool.setSchool(school);
        classInSchool.setName(name);
        classInSchool.setClassInfo(classInfo);

        try{
            if (id==null){
            classInSchool.setCreateTime(new Date());
            classInSchool =   save(classInSchool);
            }else{
                classInSchool =update(classInSchool);
            }
            return  new ApiResult(ResultMsg.SUCCESS,classInSchool);
        }catch (SmartCommunityException s){
            return  new ApiResult(s.getResultMsg(),null);
        }


    }

    @Override
    public ApiResult findByTeacherId(Long teacherId,String className) {
        ApiResult teacherResult =teacherService.findByIdWithApiResult(teacherId) ;
        if (teacherResult.isFailed()){
            return  teacherResult ;
        }
        List<ClassInSchool> datas = teacherService.findClassesByTeacherAndName(teacherId,className);
        return  new ApiResult(ResultMsg.SUCCESS,datas) ;
    }

    @Override
    public List<ClassInSchool> findBySchoolId(Long schoolId) {

        return r.findClassInSchoolBySchoolId(schoolId);
    }
}
