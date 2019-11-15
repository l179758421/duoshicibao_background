package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.ClassInSchool;
import com.runer.cibao.domain.Teacher;
import com.runer.cibao.domain.repository.TeacherRepository;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/7/3
 **/
public interface TeacherService extends BaseService<Teacher,TeacherRepository> {


    /**
     * 获得教师列表
     * @param schoolId
     * @param  teacherName
     * @param classInSchoolId
     * @param schoolUid
     * @param page
     * @param limit
     * @return
     */
    Page<Teacher> findTeachers(Long schoolId, String teacherName, Long classInSchoolId, String schoolUid, Integer page, Integer limit);

    /**
     * 增加或者更新教师
     * @param id
     * @param name
     * @return
     */
    ApiResult addOrUpdateTeacher(Long id, String name, Long schoolId, Long provinceId
            , Long cityId, Long areaId, String phone, String email, Integer sex, String address, Date birthDay, String headerFile);


    /**
     * 授权教师
     * @param teacherId
     * @param ids
     * @return
     */
    ApiResult authorizationTeacherClass(Long teacherId, String ids);


    /**
     * 通过老师查找班级
     * @param teacherId
     * @return
     */
    List<ClassInSchool>  findClassesByTeacher(Long teacherId) ;


    /**
     * 通过老师和班级名称查找班级
     * @param teacherId
     * @return
     */
    List<ClassInSchool>  findClassesByTeacherAndName(Long teacherId, String className) ;

    /**
     * 设置选中未选中；
     * @param teacherId
     * @param schoolId
     * @return
     */
     List<ClassInSchool> markClasses(Long teacherId, Long schoolId);





}
