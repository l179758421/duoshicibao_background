package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.ErrorRecovery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/29
 **/
public interface ErrorRecoveryRepository  extends JpaRepository<ErrorRecovery,Long> ,JpaSpecificationExecutor<ErrorRecovery> {


        List<ErrorRecovery> findByAppUser_IdOrderByCreateTimeDesc(Long userId);

}
