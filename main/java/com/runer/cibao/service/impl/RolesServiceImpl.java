package com.runer.cibao.service.impl;

import com.runer.cibao.domain.Roles;
import com.runer.cibao.domain.repository.RolesRepository;
import com.runer.cibao.service.RolesService;
import org.springframework.stereotype.Service;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/1
 **/
@Service
public class RolesServiceImpl extends BaseServiceImp<Roles, RolesRepository> implements RolesService {

    @Override
    public Roles findByRolesName(String rolesName) {
        return r.findRolesByRoleName(rolesName);
    }
}
