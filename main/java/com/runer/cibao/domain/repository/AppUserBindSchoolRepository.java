package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.AppUserBindSchool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/7/2
 **/
public interface AppUserBindSchoolRepository extends JpaRepository<AppUserBindSchool,Long> ,JpaSpecificationExecutor<AppUserBindSchool> {
}
