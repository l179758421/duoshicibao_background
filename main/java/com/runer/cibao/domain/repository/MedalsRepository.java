package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.Medals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/11/15
 **/
public interface MedalsRepository extends JpaRepository<Medals,Long> ,JpaSpecificationExecutor<Medals> {


    List<Medals> findAllByAppUserId(Long appUserId);


}
