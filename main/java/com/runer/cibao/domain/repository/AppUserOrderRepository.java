package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.AppUserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/27
 **/
public interface AppUserOrderRepository extends JpaRepository<AppUserOrder,Long> ,JpaSpecificationExecutor<AppUserOrder> {



}
