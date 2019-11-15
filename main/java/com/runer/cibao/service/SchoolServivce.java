package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.School;
import com.runer.cibao.domain.repository.SchoolRepository;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/6
 * 学校相关
 **/
public interface SchoolServivce extends BaseService<School,SchoolRepository> {

    /**
     * 查找学校---
     * @return
     */
    Page<School> findSchool(String schoolName, Long cityId, Long provinceId, Long areaId, String address, String schoolMasterName, Date startTiem, Date endTime, Long agentsId, Integer page, Integer limit);

    /**
     * 增加和更新school---
     * @param id
     * @param name
     * @param cityId
     * @param provinceId
     * @param areaId
     * @param address
     * @param des
     * @return
     */
    ApiResult  addOrUpdateSchool(Long id, String name, Long cityId, Long provinceId, Long areaId, String address, String phone, String des);
    ApiResult  addSchool(Long id, String name, Long cityId, Long provinceId, Long areaId, String address, String phone, String des);


    /**
     * 通过school的uid进行查询；
     * @param uid
     * @return
     */
    ApiResult findSchoolByUID(String uid) ;


    /**
     * 通过angets ID 进行查询
     */
    List<School> schoolsCanAdd();

    List<School> findByAgentId(Long id);


    ApiResult exportSchoolUid(HttpServletResponse response);

    Page<School> findSchoolByAgentId(Long id, String schoolName, Integer page, Integer limit);


    ApiResult getAll();
}
