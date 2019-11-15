package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.Permission;
import com.runer.cibao.domain.Roles;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.PermisionsService;
import com.runer.cibao.service.RolesService;
import com.runer.cibao.util.NormalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author sww
 * @Date 2019/9/25
 **/

@RestController
@RequestMapping(value = "roles")
public class RolesController {

    @Autowired
    RolesService beanService ;

    @Autowired
    PermisionsService permisionsService ;


    @RequestMapping(value = "data_list")
    public LayPageResult<Roles> getDataList(Integer page , Integer limit){
        Page<Roles> pageResult = beanService.findByPage(page,limit);
        return NormalUtil.createLayPageReuslt(pageResult) ;
    }


    @RequestMapping(value = "shiro_data_list")
    public LayPageResult<Roles> getPemissions(Long rolesId){
        Page<Permission> data = permisionsService.findByRolesId(rolesId, 1, Integer.MAX_VALUE, true);
        return  NormalUtil.createLayPageReuslt(data);
    }

    @RequestMapping(value = "distrube")
    public ApiResult distrubePe(Long roleId ,String ids){
        return  permisionsService.distrubePemissons(ids,roleId) ;
    }


    @RequestMapping(value = "shiro_role_list")
    public LayPageResult<Roles> shiro_role_list(Long rolesId){
        Page<Permission> data = permisionsService.findByRolesId(rolesId, 1, Integer.MAX_VALUE, false);
        return  NormalUtil.createLayPageReuslt(data);
    }
}
