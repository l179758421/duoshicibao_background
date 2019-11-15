package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.PersonalTestForUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/25
 **/
public interface PersonalTestForUnitRepository extends JpaRepository<PersonalTestForUnit,Long>,JpaSpecificationExecutor<PersonalTestForUnit> {

    @Query(value = "select * from presonal_test_for_unit where user_id = ?1 and test_date between DATE_ADD(CURDATE() ,interval 0 HOUR) and DATE_ADD(CURDATE() ,interval 24 HOUR)",nativeQuery = true)
    PersonalTestForUnit findTodayTestForUnit(Long appUserId);
}
