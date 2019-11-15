package com.runer.cibao.dao;

import com.runer.cibao.domain.Permission;
import com.runer.cibao.domain.Roles;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.PermisionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/1
 **/
@Repository
public class RolesDao {

    @Autowired
    PermisionsService permisionsService ;


    public Roles generateRoles(Roles roles){
        String permissioinIds = roles.getPermissionIds();
        if (!StringUtils.isEmpty(permissioinIds)){
            try {
                List<Permission> permissions = permisionsService.findByIds(permissioinIds);
                roles.setPermissionIds(permissioinIds);
                roles.setPermissions(permissions);
            } catch (SmartCommunityException e) {
                e.printStackTrace();
            }
        }
        return  roles ;
    }



}
