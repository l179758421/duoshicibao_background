package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.TestRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TestRuleRepository extends JpaRepository<TestRule,Long>,JpaSpecificationExecutor <TestRule>{
}
