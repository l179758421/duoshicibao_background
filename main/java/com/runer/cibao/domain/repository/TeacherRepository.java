package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/7/3
 **/
public interface TeacherRepository extends JpaRepository<Teacher,Long> ,JpaSpecificationExecutor<Teacher> {


}
