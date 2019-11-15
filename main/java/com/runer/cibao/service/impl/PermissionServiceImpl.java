package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.PermissionsDao;
import com.runer.cibao.domain.Permission;
import com.runer.cibao.domain.Roles;
import com.runer.cibao.domain.repository.PermissionsRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.PermisionsService;
import com.runer.cibao.service.RolesService;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;
import org.thymeleaf.util.StringUtils;

import java.util.*;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/1
 **/
@Service
public class PermissionServiceImpl extends BaseServiceImp<Permission,PermissionsRepository> implements PermisionsService {


    @Autowired
    PermissionsDao permissionsDao ;


    @Autowired
    RolesService rolesService ;

    @Override
    public List<Permission> findMenus(Long rolesId )
    {

        //查询roles 的permisson ；
        List<Long> permisionIds =new ArrayList<>();

        if (rolesId!=null){
         ApiResult rolesResult  =   rolesService.findByIdWithApiResult(rolesId);
         if (rolesResult.isFailed()){
             return  null ;
         }
         Roles roles = (Roles) rolesResult.getData();
         if (!StringUtils.isEmpty(roles.getPermissionIds())) {
             Arrays.asList(roles.getPermissionIds().split(",")).forEach(s -> {
                 permisionIds.add(Long.parseLong(s)) ;
             });
         }
        }

        List<Permission> userMunus =new ArrayList<>();

        List<Permission> mainMenus = findAllMenus();

        for (Permission mainMenu : mainMenus){
            //有子元素的情况下；
            if (!ListUtils.isEmpty(mainMenu.getPowerList())){
                List<Permission> userChildPermissions = new ArrayList<>() ;
                for (Permission permission : mainMenu.getPowerList()) {
                     if (permisionIds.contains(permission.getId())){
                         userChildPermissions.add(permission);
                     }
                }
                //只有子元素不为空的情况下；
                if (!ListUtils.isEmpty(userChildPermissions)){
                    mainMenu.setPowerList(userChildPermissions);
                    userMunus.add(mainMenu) ;
                }
            //子元素的情况下；
            }else{
                if (permisionIds.contains(mainMenu.getId())){
                    userMunus.add(mainMenu);
                }
            }
        }
        return userMunus;
    }

    @Override
    public List<Permission> findAllMenus() {
        List<Permission> mainMenus = permissionsDao.findMenusPemissionsWithOutChild();

        for (Permission mainMenu : mainMenus) {
            List<Permission> childData =  permissionsDao.findMenusByPrantId(Long.valueOf(mainMenu.getMenuId()));
            String dataPath ="{url:'"+mainMenu.getPowerUrl()+"',title:'"+mainMenu.getPowerName()+"',icon:'',id:'"+mainMenu.getMenuId()+"'}" ;
            mainMenu.setDataPath(dataPath);
            if (!ListUtils.isEmpty(childData)){
                mainMenu.setHasChild(1);
                mainMenu.setPowerList(childData);
                for (Permission childDatum : childData) {
                    String dataPath1 ="{url:'"+childDatum.getPowerUrl()+"',title:'"+childDatum.getPowerName()+"',icon:'',id:'"+childDatum.getMenuId()+"'}" ;
                    childDatum.setDataPath(dataPath1);
                }
            }
        }
        return mainMenus;
    }

    @Override
    public Page<Permission> findByRolesId(Long rolesID ,Integer page ,Integer rows  ,boolean showAll ) {

     ApiResult rolesResult = rolesService.findByIdWithApiResult(rolesID) ;

     if (rolesResult.isFailed()){
         return  findByPage(page,rows);
     }
        Roles roles = (Roles) rolesResult.getData();
        String permissonsIds =roles.getPermissionIds() ;
        //显示所有
     if (showAll){
         Page<Permission> data = findByPage(page, rows);
         try {
             List<Permission> pIds = findByIds(permissonsIds);
             Map<Long ,Permission > selcted =new HashMap<>() ;
             pIds.forEach(permission -> {
                 selcted.put(permission.getId(),permission) ;
             });
             data.forEach(permission -> {
                 if (selcted.containsKey(permission.getId())){
                     permission.setLAY_CHECKED(true);
                 }
             });
             return data ;
         } catch (SmartCommunityException e) {
             //e.printStackTrace();
             return  findByPage(page,rows) ;
         }
     }else{
         try {
             List<Permission> pIds = findByIds(permissonsIds);
             Page<Permission> permissions =new PageImpl(pIds ,PageableUtil.basicPage(page,rows),pIds==null?0:pIds.size());
             return  permissions ;
         } catch (SmartCommunityException e) {
             //e.printStackTrace();
             return  null ;
         }
     }
    }

    @Override
    public ApiResult distrubePemissons(String ids, Long rolesId) {
        ApiResult rolesResult =rolesService.findByIdWithApiResult(rolesId) ;
        if (rolesResult.isFailed()){
            return   rolesResult ;
        }
        Roles roles = (Roles) rolesResult.getData();
        roles.setPermissionIds(ids);
        try {
            rolesService.update(roles);
            return  new ApiResult(ResultMsg.SUCCESS,null) ;
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null) ;
        }
    }
}
