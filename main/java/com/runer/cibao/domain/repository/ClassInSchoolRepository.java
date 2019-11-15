package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.ClassInSchool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/7
 **/
public interface ClassInSchoolRepository extends JpaRepository<ClassInSchool,Long>,JpaSpecificationExecutor<ClassInSchool> {
     List<ClassInSchool> findClassInSchoolBySchoolId(Long schoolId);
}
