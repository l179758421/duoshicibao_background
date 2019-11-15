package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.ReviewTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReviewTestRepository extends JpaRepository<ReviewTest,Long> ,JpaSpecificationExecutor<ReviewTest> {
}
