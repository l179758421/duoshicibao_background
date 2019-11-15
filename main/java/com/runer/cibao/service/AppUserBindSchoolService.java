package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.AppUserBindSchool;
import com.runer.cibao.domain.repository.AppUserBindSchoolRepository;
import org.springframework.data.domain.Page;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/7/2
 **/
public interface AppUserBindSchoolService extends BaseService<AppUserBindSchool, AppUserBindSchoolRepository> {


    /**
     * 绑定school
     * @param userId
     * @param schoolUid
     * @return
     */
    ApiResult bindSchool(Long userId, String schoolUid) ;


    /**
     * 绑定user
     * @param schoolId
     * @param userUid
     * @return
     */
    ApiResult bindAppUser(Long schoolId, String userUid);
    /**
     * 统一绑定
     * @param bindId
     * @param userId
     * @return
     */
    ApiResult agreeBindSchool(Long bindId, Long userId);



    ApiResult agreeBinds(String ids, Long userId) ;


    /**
     * 获得绑定改的信息
     * @param schoolUid
     * @return
     */
    ApiResult getBindInfo(String schoolUid) ;


    /**
     * 获得绑定
     * @param userId
     * @param adminUserId
     * @param schoolId
     * @param state
     * @param page
     * @param limit
     * @return
     */
    Page<AppUserBindSchool> findSchoolBinds(Long userId, Long adminUserId, String studentName,
                                            Long schoolId, Integer state, Integer page, Integer limit);




}
