package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.Admin;
import com.runer.cibao.domain.repository.AdminRepository;
import org.springframework.data.domain.Page;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/7/4
 **/
public interface AdminService  extends BaseService<Admin, AdminRepository>{


    /**
     * 创建超级管理员
     * @return
     */
    ApiResult generateAMasterDefault();

    /**
     * 修改密码
     * @param adminID
     * @param pass
     * @param loginName
     * @return
     */
    ApiResult changePassWord(Long adminID, String pass, String loginName);

    /**
     * 获得管理员
     * @param isMaster
     * @param page
     * @param limit
     * @return
     */
    Page<Admin> findAdmins(Integer isMaster, Integer page, Integer limit) ;



}
