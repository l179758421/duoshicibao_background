package com.runer.cibao.service.impl;

import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.AgentsDao;
import com.runer.cibao.domain.*;
import com.runer.cibao.domain.repository.AgentsRepostory;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import com.runer.cibao.util.Encoder;
import com.runer.cibao.util.PowerUtil;
import com.runer.cibao.util.machine.IdsMachine;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/9
 **/

@Service
public class AgentServiceImpl extends BaseServiceImp<Agents, AgentsRepostory>implements AgentsService {

    @Autowired
    AreaService areaService ;


    @Autowired
    SchoolServivce schoolServivce;


    @Autowired
    RolesService rolesService ;

    @Autowired
    private AgentsDao agentsDao ;

    @Autowired
    private UserService userService ;

    @Autowired
    Encoder encoder ;


    @Override
    public Page<Agents> findAgents(String agentName, String phone, Integer page, Integer rows) {
        return agentsDao.findAgents(agentName,phone,PageableUtil.basicPage(page,rows));
    }

    @Override
    public ApiResult addOrUpdateAgents(Long id, String name, Long provinceId, Long cityId,
                                       Long areaId, String phone, String email, Integer sex,
                                       String address, Date birthDay, String  headerImg) {
        Agents agents =new Agents() ;

        if (id!=null){
            try {
                agents = findById(id);
            } catch (SmartCommunityException e) {
                e.printStackTrace();
                return  new ApiResult(e.getResultMsg(),null) ;
            }
        }

//        if (StringUtils.isEmpty(name)){
//            return  new ApiResult(ResultMsg.USERNAME_IS_NULL,null) ;
//        }
//        if (id==null||!name.equals(agents.getName())){
//            if (r.findAgentsByName(name)!=null){
//                return  new ApiResult(ResultMsg.USERNAME_IS_EXISTED,null);
//            }
//        }

        agents.setId(id);
        agents.setName(name);
        if(StringUtils.isEmpty(phone)){
            return new ApiResult("手机号不能为空!");
        }
        agents.setPhone(phone);
        agents.setEmail(email);
        agents.setSex(sex);
        agents.setAddress(address);
        agents.setBirthDay(birthDay);
        agents.setProvinceId(provinceId);
        agents.setCityId(cityId);
        agents.setAreaId(areaId);
        if (id==null) {
            agents.setRegisterDate(new Date());
        }
       // agents.setHeaderImgUrl("");

        String addreeDetail ="";

        if (provinceId!=null){
            Province province =areaService.getProvince(provinceId);
            if (province!=null){
                addreeDetail +=province.getName();
            }
        }

        if (cityId!=null){
            City city =areaService.findCityById(cityId);
            if (city!=null){
                addreeDetail+=city.getName() ;
            }
        }
        if (areaId!=null){
            Area area= areaService.findAreaById(areaId);
            if (area!=null){
                addreeDetail+=area.getName();
            }
        }
        addreeDetail+=":"+agents.getAddress();

        agents.setDetailAddress(addreeDetail);

        if (!StringUtils.isEmpty(headerImg)){
            agents.setHeaderImgUrl(headerImg);
        }

        //设置基本的用户和权限
        if (id==null){
           ApiResult rolesResult = PowerUtil.generateRolesUser(userService,rolesService,
                   phone,name, Config.ROLES_AGENTS,encoder) ;
           if (rolesResult.isFailed()){
               if (rolesResult.getMsgCode()== ResultMsg.LOGIN_NAME_REPEATED.getMsgCode()){
                   return new ApiResult("该手机号已存在!");
               }
               return  rolesResult ;
           }
           agents.setUser((User) rolesResult.getData());
        }

        try {
            agents =saveOrUpdate(agents);
            return  new ApiResult(agents,true) ;
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null) ;
        }
    }


    @Override
    public Page<School> findAgentsSchoolsByAgentsId(Long id , Integer page , Integer limit) {

        ApiResult agentsResult =findByIdWithApiResult(id);
        if (agentsResult.isFailed()){
            return   null ;
        }
        Agents agents = (Agents) agentsResult.getData();
        List<School> schoolList=  agents.getSchools() ;

        Map<Long, School> schoolMap =new HashMap<>() ;

        schoolList.forEach(school -> {
            schoolMap.put(school.getId(),school);
        });

        Page<School> schooldatas = schoolServivce.findSchool(null, null,
                null, null, null, null, null,
                null, null, page, limit);
        schooldatas.getContent().forEach(school -> {
            if (schoolMap.containsKey(school.getId())){
                school.setLAY_CHECKED(true);
            }
        });

        return schooldatas;
    }

    @Override
    public ApiResult distribeAgentsSchool(Long id, String schoolIds) {

        ApiResult agnetsResult =findByIdWithApiResult(id);
        if (agnetsResult.isFailed()){
            return  agnetsResult ;
        }
        Agents agents = (Agents) agnetsResult.getData();
        List<Long> oldSchoolIds = new ArrayList<Long>();
        List<Long> newSchoolIds= new IdsMachine().deparseIds(schoolIds);
        //原先分配了此代理的学校
        List<School> oldSchoolList= schoolServivce.findByAgentId(agents.getId());
        for(School oldSchool:oldSchoolList){
            oldSchoolIds.add(oldSchool.getId());
        }

      //将新分配的学校设置此代理商
        for(Long newSchoolId:newSchoolIds){
            if(!oldSchoolIds.contains(newSchoolId)){
                ApiResult schoolResult  = schoolServivce.findByIdWithApiResult(newSchoolId);
                if(schoolResult.isFailed()){
                  return schoolResult;
                }
                School school=(School) schoolResult.getData();
                school.setAgents(agents);
                try {
                    schoolServivce.saveOrUpdate(school);
                } catch (SmartCommunityException e) {
                    e.printStackTrace();
                }
            }
        }

        for(Long oldSchoolId:oldSchoolIds){
            if(!newSchoolIds.contains(oldSchoolId)){
                ApiResult schoolResult  = schoolServivce.findByIdWithApiResult(oldSchoolId);
                if(schoolResult.isFailed()){
                    return schoolResult;
                }
                School school=(School) schoolResult.getData();
                school.setAgents(null);
                try {
                    schoolServivce.saveOrUpdate(school);
                } catch (SmartCommunityException e) {
                    e.printStackTrace();
                }
            }
        }


        return new ApiResult(ResultMsg.SUCCESS,null);
    }

    @Value("${web.upload-cibaoPath}")
    private String rePath ;

    @Value("${web.upload-cibao}")
    private String abPath ;


}
