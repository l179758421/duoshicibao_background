package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.SchoolMaster;
import com.runer.cibao.domain.repository.SchoolMasterRepository;
import org.springframework.data.domain.Page;

import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/6
 **/
public interface SchoolMasterService extends BaseService<SchoolMaster,SchoolMasterRepository>{

    /**
     * 查询校长
     * @param schoolName
     * @param masterName
     * @param address
     * @return
     */
    Page<SchoolMaster> findSchoolMaster(String schoolName, String masterName, String address, Integer page, Integer limit);
    /*姓名 学校 电话 邮箱 性别 生日 地址 添加时间 头像*/


    /**
     *  添加或者更新school；
     * @param id
     * @param name
     * @param schoolId
     * @param provinceId
     * @param cityId
     * @param areaId
     * @param phone
     * @param email
     * @param sex
     * @param address
     * @param headerFile
     * @return
     */
    ApiResult addOrUpdateSchoolMaster(Long id, String name, Long schoolId, Long provinceId
            , Long cityId, Long areaId, String phone, String email, Integer sex, String address, Date birthDay, String headerFile
    );


    ApiResult changePass(Long userId, String pass) ;

    SchoolMaster findSchoolMasterBySchoolId(Long schoolId);








}
