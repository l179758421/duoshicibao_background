package com.runer.cibao.domain.repository;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.IntegralDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IntegralDetailRepository extends JpaRepository<IntegralDetail,Long>,JpaSpecificationExecutor<IntegralDetail> {
    ApiResult findByIntegral_Id(Long integralId);
}
