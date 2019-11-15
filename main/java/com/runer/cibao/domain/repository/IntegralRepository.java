package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.Integral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IntegralRepository extends JpaRepository<Integral,Long>,JpaSpecificationExecutor<Integral> {

    /**
     * 根据app用户id查找用户积分详情
     * @param appUserId
     * @return
     */


    Integral findIntegralByAppUser_Id(Long appUserId);
}
