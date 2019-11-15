package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.HelpToUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/12
 **/
public interface HelpToUserRepository extends JpaRepository<HelpToUser,Long> ,JpaSpecificationExecutor<HelpToUser> {

}
