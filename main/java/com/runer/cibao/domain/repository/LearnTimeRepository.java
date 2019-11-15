package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.LearnTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/11/3
 **/
public interface LearnTimeRepository extends JpaRepository<LearnTime,Long> ,JpaSpecificationExecutor<LearnTime> {


    List<LearnTime> findAllByAppUserId(Long appuserId);


}
