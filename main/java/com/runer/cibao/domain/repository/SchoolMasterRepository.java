package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.SchoolMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/6
 **/
public interface SchoolMasterRepository  extends JpaRepository<SchoolMaster,Long> ,JpaSpecificationExecutor<SchoolMaster> {

    SchoolMaster findSchoolMasterBySchoolId(Long schoolId);
}
