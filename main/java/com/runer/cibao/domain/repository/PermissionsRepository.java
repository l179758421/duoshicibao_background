package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/1
 **/
public interface  PermissionsRepository extends JpaRepository<Permission,Long> ,JpaSpecificationExecutor<Permission> {
}
