package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/6
 **/
public interface SchoolRepository extends JpaRepository<School,Long> ,JpaSpecificationExecutor<School> {

    School findSchoolByUid(String uid) ;

    /**
     * 根据代理id查找学校
     * @param id
     * @return
     */
    List<School> findByAgents_Id(Long id);

}
