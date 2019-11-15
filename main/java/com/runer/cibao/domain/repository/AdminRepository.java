package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/7/4
 **/
public interface AdminRepository  extends JpaRepository<Admin,Long> ,JpaSpecificationExecutor<Admin>{

}
