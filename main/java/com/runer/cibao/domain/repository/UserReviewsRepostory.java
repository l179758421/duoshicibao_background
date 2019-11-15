package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.UserRevivews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/30
 **/
public interface UserReviewsRepostory  extends JpaRepository<UserRevivews,Long> ,JpaSpecificationExecutor<UserRevivews> {

}
