package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.TestRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/11/1
 **/
public interface TestRecordRepository extends JpaRepository<TestRecords,Long> ,JpaSpecificationExecutor<TestRecords> {
}
