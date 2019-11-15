package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/1
 **/
public interface RolesRepository extends JpaRepository<Roles,Long> , JpaSpecificationExecutor<Roles> {

    Roles findRolesByRoleName(String rolesName) ;

}
