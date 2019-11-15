package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.IntegralDes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IntegralDesRepository extends JpaRepository<IntegralDes,Long>,JpaSpecificationExecutor<IntegralDes> {

}
