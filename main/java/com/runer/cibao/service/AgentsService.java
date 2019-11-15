package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.Agents;
import com.runer.cibao.domain.School;
import com.runer.cibao.domain.repository.AgentsRepostory;
import org.springframework.data.domain.Page;

import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/9
 **/
public interface AgentsService extends BaseService<Agents,AgentsRepostory> {


   Page<Agents> findAgents(String agentName, String phone, Integer page, Integer rows);


   ApiResult addOrUpdateAgents(Long id, String name, Long provinceId,
                               Long cityId, Long areaId, String phone, String email,
                               Integer sex, String address, Date birthDay, String headerFile);

   Page<School> findAgentsSchoolsByAgentsId(Long id, Integer page, Integer limit);


   ApiResult distribeAgentsSchool(Long id, String schoolIds) ;





}
