package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.ClassInSchool;
import com.runer.cibao.domain.repository.ClassInSchoolRepository;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/7
 **/
public interface ClassInSchoolService extends BaseService<ClassInSchool,ClassInSchoolRepository>{


    Page<ClassInSchool> findClassInSchool(Long schoolId, String schoolName, String className, Integer page, Integer limit);

    ApiResult addOrUpdateClassInSchool(Long id, Long schoolId, String name, String classInfo, Long addUserId);


    /**
     *通过teacher的id获得classes ；
     * @param teacherId
     * @return
     */
    ApiResult findByTeacherId(Long teacherId, String className) ;

    List<ClassInSchool> findBySchoolId(Long schoolId);



}
