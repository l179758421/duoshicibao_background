package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.Permission;
import com.runer.cibao.domain.repository.PermissionsRepository;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/1
 **/
public interface PermisionsService extends BaseService<Permission, PermissionsRepository> {

    List<Permission>  findMenus(Long rolesId);
    List<Permission>  findAllMenus();

    Page<Permission> findByRolesId(Long rolesID, Integer page, Integer limit, boolean showall);

    ApiResult distrubePemissons(String ids, Long rolesId) ;



}
