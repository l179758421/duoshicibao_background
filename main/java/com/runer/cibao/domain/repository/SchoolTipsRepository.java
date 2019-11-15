package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.SchoolTips;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SchoolTipsRepository extends JpaRepository<SchoolTips,Long>,JpaSpecificationExecutor<SchoolTips> {
      List<SchoolTips> findSchoolTipsBySchool_Id(Long schoolId);
}
