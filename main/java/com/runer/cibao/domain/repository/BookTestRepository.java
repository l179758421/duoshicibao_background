package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.TestRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/19
 **/
public interface BookTestRepository  extends JpaRepository<TestRecords,Long> ,JpaSpecificationExecutor<TestRecords> {
}
