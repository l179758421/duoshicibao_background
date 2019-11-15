package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/12
 **/
public interface FeedBackRepository extends JpaRepository<FeedBack,Long> ,JpaSpecificationExecutor<FeedBack> {

}
