package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.AdminConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/22
 **/
public interface AdminConfigRpository  extends JpaRepository<AdminConfig,Long> ,JpaSpecificationExecutor<AdminConfig>  {
}
