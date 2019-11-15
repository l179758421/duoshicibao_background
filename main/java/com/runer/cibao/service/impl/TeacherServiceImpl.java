package com.runer.cibao.service.impl;

import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.TeacherDao;
import com.runer.cibao.domain.*;
import com.runer.cibao.domain.repository.TeacherRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import com.runer.cibao.util.Encoder;
import com.runer.cibao.util.PowerUtil;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.util.ListUtils;

import java.util.*;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/7/3
 **/
@Service
public class TeacherServiceImpl extends BaseServiceImp<Teacher, TeacherRepository> implements TeacherService {


    @Autowired
    TeacherDao teacherDao;

    @Autowired
    UserService userService;

    @Autowired
    AreaService areaService ;

    @Autowired
    Encoder encoder;

    @Autowired
    ClassInSchoolService classInSchoolService;

    @Autowired
    SchoolServivce schoolServivce;


    @Autowired
    RolesService rolesService;


    @Override
    public Page<Teacher> findTeachers(Long schoolId, String teacherName, Long classInSchoolId, String schoolUid, Integer page, Integer limit) {
        return teacherDao.findTeachers(schoolId, teacherName, schoolUid, classInSchoolId, PageableUtil.basicPage(page, limit));
    }

    @Override
    public ApiResult addOrUpdateTeacher(Long id, String name, Long schoolId, Long provinceId
            , Long cityId , Long areaId, String phone , String email , Integer sex, String address , Date birthDay , String headerFile) {
        Teacher teacher = new Teacher();

        if (schoolId == null) {
            return new ApiResult(ResultMsg.SCHOOL_ID_IS_NULL, null);
        }

        if (id != null) {
            ApiResult teacherResult = findByIdWithApiResult(id);
            if (teacherResult.getMsgCode() != ResultMsg.SUCCESS.getMsgCode()) {
                return teacherResult;
            }
            teacher = (Teacher) teacherResult.getData();
        }
        if (StringUtils.isEmpty(phone)) {
            return new ApiResult("手机号不能为空!");
        }

        //设置User
        if (id == null) {
            ApiResult rolesResult = PowerUtil.generateRolesUser(userService, rolesService,
                    phone,name, Config.ROLES_TEACHER, encoder);
            if (rolesResult.isFailed()) {
                if (rolesResult.getMsgCode()== ResultMsg.LOGIN_NAME_REPEATED.getMsgCode()){
                    return new ApiResult("该手机号已存在!");
                }
                return rolesResult;
            }
            teacher.setUser((User) rolesResult.getData());
        }

        teacher.setName(name);
        teacher.setAddress(address);
        teacher.setName(name);
        teacher.setPhone(phone);
        teacher.setEmail(email);
        teacher.setSex(sex);
        teacher.setBirthday(birthDay);

        if (!StringUtils.isEmpty(headerFile)){
            teacher.setHeaderImgUrl(headerFile);
        }

        if (id == null) {
            teacher.setCreateDate(new Date());
        }

        if (schoolId != null) {
            ApiResult schoolResult = schoolServivce.findByIdWithApiResult(schoolId);
            if (schoolResult.isFailed()) {
                return schoolResult;
            }
            School school = (School) schoolResult.getData();
            teacher.setSchool(school);
        }

        teacher.setAreaId(areaId);
        teacher.setProvinceId(provinceId);
        teacher.setCityId(cityId);

        String addreeDetail ="";

        if (provinceId!=null){
            Province province =areaService.getProvince(provinceId);
            if (province!=null){
                addreeDetail +=province.getName();
            }
        }

        if (cityId!=null){
            City city =areaService.findCityById(cityId);
            if (city!=null){
                addreeDetail+=city.getName() ;
            }
        }
        if (areaId!=null){
            Area area= areaService.findAreaById(areaId);
            if (area!=null){
                addreeDetail+=area.getName();
            }
        }
        addreeDetail+=":"+teacher.getAddress();
        teacher.setDetailAddress(addreeDetail);

        teacher = r.saveAndFlush(teacher);
        return new ApiResult(ResultMsg.SUCCESS, teacher);
    }

    @Override
    public ApiResult authorizationTeacherClass(Long teacherId, String ids) {

        ApiResult teacherResult = findByIdWithApiResult(teacherId);
        if (teacherResult.getMsgCode() != ResultMsg.SUCCESS.getMsgCode()) {
            return teacherResult;
        }
        Teacher teacher = (Teacher) teacherResult.getData();

        if (StringUtils.isEmpty(ids)) {
            return new ApiResult(ResultMsg.IDS_IS_NOT_ILLEGAL, null);
        }

        teacher.setClassIds(ids);

        teacher = r.saveAndFlush(teacher);

        return new ApiResult(ResultMsg.SUCCESS, teacher);
    }

    @Override
    public List<ClassInSchool> findClassesByTeacher(Long teacherId) {
        ApiResult teacherResult = findByIdWithApiResult(teacherId);
        if (teacherResult.getMsgCode() != ResultMsg.SUCCESS.getMsgCode()) {
            return new ArrayList<>();
        }
        Teacher teacher = (Teacher) teacherResult.getData();

        String ids = teacher.getClassIds();

        try {
            List<ClassInSchool> datas = classInSchoolService.findByIds(ids);
            return datas;
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<ClassInSchool> findClassesByTeacherAndName(Long teacherId, String className) {

        ApiResult teacherResult = findByIdWithApiResult(teacherId);
        if (teacherResult.getMsgCode() != ResultMsg.SUCCESS.getMsgCode()) {
            return new ArrayList<>();
        }
        Teacher teacher = (Teacher) teacherResult.getData();

        String ids = teacher.getClassIds();

        try {
            List<ClassInSchool> datas = classInSchoolService.findByIds(ids);
            List<ClassInSchool> list=new ArrayList<>();
            list.addAll(datas);
            if (className != null) {

                for (int i = 0; i < list.size(); i++) {
                    ClassInSchool inSchool = list.get(i);
                    if (!inSchool.getName().equals(className)) {
                        datas.remove(inSchool);
                    }
                }

            }

            return datas;
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }


    @Override
    public List<ClassInSchool> markClasses(Long teacherId, Long schoolId) {


        if (teacherId == null || schoolId == null) {
            return new ArrayList<>();
        }
        List<ClassInSchool> classInSchools_teacher = findClassesByTeacher(teacherId);
        Map<Long, ClassInSchool> classInSchoolMap = new HashMap<>();

        classInSchools_teacher.forEach(classInSchool -> {
            classInSchoolMap.put(classInSchool.getId(), classInSchool);
        });


        List<ClassInSchool> classInSchools = classInSchoolService.findClassInSchool(schoolId, null, null, 1, Integer.MAX_VALUE).getContent();

        if (!ListUtils.isEmpty(classInSchools)) {
            classInSchools.forEach(classInSchool -> {
                if (classInSchoolMap.containsKey(classInSchool.getId())) {
                    classInSchool.setLAY_CHECKED(true);
                } else {
                    classInSchool.setLAY_CHECKED(false);
                }
            });
        }
        return classInSchools;
    }
}
