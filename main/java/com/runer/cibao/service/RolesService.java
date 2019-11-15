package com.runer.cibao.service;

import com.runer.cibao.domain.Roles;
import com.runer.cibao.domain.repository.RolesRepository;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/1
 **/
public interface  RolesService  extends BaseService<Roles, RolesRepository> {

    Roles findByRolesName(String rolesName) ;

}
