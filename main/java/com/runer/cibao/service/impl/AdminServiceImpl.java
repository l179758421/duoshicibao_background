package com.runer.cibao.service.impl;

import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.AdminDao;
import com.runer.cibao.domain.Admin;
import com.runer.cibao.domain.User;
import com.runer.cibao.domain.repository.AdminRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.AdminService;
import com.runer.cibao.service.RolesService;
import com.runer.cibao.service.UserService;
import com.runer.cibao.util.Encoder;
import com.runer.cibao.util.PowerUtil;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.util.ListUtils;

import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/7/4
 **/
@Service
public class AdminServiceImpl  extends BaseServiceImp<Admin,AdminRepository> implements AdminService {


    @Autowired
    AdminDao adminDao ;

    @Autowired
    Encoder encoder ;

    @Autowired
    UserService userService ;

    @Autowired
    RolesService rolesService ;


    @Override
    public ApiResult generateAMasterDefault() {
        Page<Admin> datas = findAdmins(Config.IS_SUPER_MASTER, 1, 10);;
        if (!ListUtils.isEmpty(datas.getContent())){
            return  new ApiResult(ResultMsg.SUCCESS,null);
        }
        Admin admin =new Admin() ;
        admin.setCreateTime(new Date());
        admin.setName(Config.ADMIN_NAME);
        admin.setIsMaster(Config.IS_SUPER_MASTER);

        ApiResult rolesResult = PowerUtil.generateRolesUser(userService,rolesService,
                "Admin","Admin", Config.ROLES_ADMIN,encoder) ;
        if (rolesResult.isFailed()){
            return  rolesResult ;
        }
        admin.setUser((User) rolesResult.getData());

       admin= r.saveAndFlush(admin) ;

        return new ApiResult(ResultMsg.SUCCESS,admin);
    }
    @Override
    public ApiResult changePassWord(Long adminID, String pass, String loginName) {
        ApiResult adminResult =findByIdWithApiResult(adminID) ;
        if (adminResult.isFailed()){
            return  adminResult ;
        }
        Admin admin  = (Admin) adminResult.getData();
        if (StringUtils.isEmpty(loginName)){
            return  new ApiResult(ResultMsg.LOGIN_NAME_IS_NULL,null) ;
        }
        User user =userService.findUserByLoginName(loginName);
        if (!loginName.equals(admin.getUser().getLoginName())&&user!=null){
            return  new ApiResult(ResultMsg.LOGIN_NAME_REPEATED,null);
        }
        user =admin.getUser() ;
        if(!StringUtils.isEmpty(pass)) {
            user.setPassWord(encoder.passwordEncoderByMd5(pass));
        }
        user.setLoginName(loginName);
        try {
            userService.saveOrUpdate(user) ;
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        admin.setUser(user);
        admin= r.saveAndFlush(admin);
        return new ApiResult(ResultMsg.SUCCESS,admin);
    }

    @Override
    public Page<Admin> findAdmins(Integer isMaster, Integer page, Integer limit) {
        return adminDao.findAdmins(isMaster,PageableUtil.basicPage(page,limit));
    }
}
