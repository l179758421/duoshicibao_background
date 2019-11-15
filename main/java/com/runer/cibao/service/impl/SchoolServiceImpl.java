package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.SchoolDao;
import com.runer.cibao.domain.*;
import com.runer.cibao.domain.repository.SchoolRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.AgentsService;
import com.runer.cibao.service.AreaService;
import com.runer.cibao.service.SchoolServivce;
import com.runer.cibao.util.ExcelUtil;
import com.runer.cibao.util.machine.IdMachine;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/6
 **/

@Service
public class SchoolServiceImpl extends BaseServiceImp<School,SchoolRepository> implements SchoolServivce {


    @Autowired
    SchoolRepository schoolRepository ;

    @Autowired
    SchoolDao schoolDao ;


    @Autowired
    AreaService areaService ;

    @Autowired
    AgentsService agentsService ;

    @Override
    public Page<School> findSchool
            (String schoolName, Long cityId, Long provinceId, Long areaId, String address, String schoolMasterName,
             Date startTiem, Date endTime,Long agentsId ,Integer page ,Integer limit) {
        return schoolDao.findSchool(schoolName,cityId,provinceId,areaId,address,schoolMasterName,startTiem,endTime,agentsId,PageableUtil.basicPage(page,limit));
    }

    @Override
    public ApiResult addOrUpdateSchool(Long id ,String name, Long cityId, Long provinceId, Long areaId, String address,String phone , String des) {

        School school =new School() ;
        if (id!=null){
            try {
               school = findById(id);
            } catch (SmartCommunityException e) {
                e.printStackTrace();
                return  new ApiResult(e.getResultMsg(),null) ;
            }
        }

        school.setId(id);
        school.setName(name);
        school.setShcoolDes(des);
        school.setPhone(phone);
        school.setAddress(address);

        if (id==null) {
            school.setCreateTime(new Date());
            try {
                school.setUid(String.valueOf(IdMachine.getFlowIdWorkerInstance().nextId()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        school.setAreaId(areaId);
        school.setProvinceId(provinceId);
        school.setCityId(cityId);

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
        addreeDetail+=":"+school.getAddress();

        school.setDetailAddress(addreeDetail);

        try {
                    if (id==null){
                            school = save(school) ;
                            return  new ApiResult(ResultMsg.SUCCESS,school) ;
                    }else{
                            school =update(school);
                            return  new ApiResult(ResultMsg.SUCCESS,school) ;

                    }
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null) ;
        }

    }

    /**
     * 生成11位uid
     *
     * @return
     */
    private String createUid() {
        String uid18 = null;
        try {
            uid18 = String.valueOf(IdMachine.getFlowIdWorkerInstance().nextId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String uid = uid18.substring(7);
        School appUser = schoolRepository.findSchoolByUid(uid);
        if (appUser == null) {
            return uid;
        } else {
            return createUid();
        }

    }

    @Override
    public ApiResult addSchool(Long id ,String name, Long cityId, Long provinceId, Long areaId, String address,String phone , String des) {

        School school = new School();
        if (id != null) {
            try {
                school = findById(id);
            } catch (SmartCommunityException e) {
                e.printStackTrace();
                return new ApiResult(e.getResultMsg(), null);
            }
        }
        Boolean bl = false;
        List<School> dd = schoolRepository.findAll();
        for (int i = 0; i < dd.size(); i++) {
            String na = dd.get(i).getName();
            if(na != null && na !=""){
                if(na.equals(name)){
                    bl = true;
                    break;
                }
            }
        }
                 if(bl){
               return new ApiResult("该学校已存在!");
           }else {
                     school.setId(id);
                     school.setName(name);
                     school.setShcoolDes(des);
                     school.setPhone(phone);
                     school.setAddress(address);

                     if (id == null) {
                         school.setCreateTime(new Date());
                         try {
                             //11位
                             school.setUid(this.createUid());
                             //19位
                             //school.setUid(String.valueOf(IdMachine.getFlowIdWorkerInstance().nextId()));
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                     }

                     school.setAreaId(areaId);
                     school.setProvinceId(provinceId);
                     school.setCityId(cityId);

                     String addreeDetail = "";

                     if (provinceId != null) {
                         Province province = areaService.getProvince(provinceId);
                         if (province != null) {
                             addreeDetail += province.getName();
                         }
                     }

                     if (cityId != null) {
                         City city = areaService.findCityById(cityId);
                         if (city != null) {
                             addreeDetail += city.getName();
                         }
                     }
                     if (areaId != null) {
                         Area area = areaService.findAreaById(areaId);
                         if (area != null) {
                             addreeDetail += area.getName();
                         }
                     }
                     addreeDetail += ":" + school.getAddress();

                     school.setDetailAddress(addreeDetail);

                     try {
                             school = save(school);
                             return new ApiResult(ResultMsg.SUCCESS, school);
                     } catch (SmartCommunityException e) {
                         e.printStackTrace();
                         return new ApiResult(e.getResultMsg(), null);
                     }
                 }

    }

    @Override
    public ApiResult findSchoolByUID(String uid) {
       School school =  r.findSchoolByUid(uid) ;
       if (school==null){
           return  new ApiResult(ResultMsg.NOT_FOUND ,null) ;
       }
       return  new ApiResult(ResultMsg.SUCCESS,school) ;
    }

    @Override
    public List<School> schoolsCanAdd() {
      return   schoolDao.findSchoolsCanAdd();
    }

    @Override
    public List<School> findByAgentId(Long id) {

        return r.findByAgents_Id(id);
    }

    @Override
    public ApiResult exportSchoolUid(HttpServletResponse response) {

        List<School> schools = r.findAll();

        if (ListUtils.isEmpty(schools)){
            return  new ApiResult("数据为空");
        }

        List<SchoolUidForExcel> schoolUidForExcels =new ArrayList<>();
        for(School school : schools){
            SchoolUidForExcel excel =new SchoolUidForExcel() ;
            excel.setAddress(school.getAddress());
            excel.setPhone(school.getPhone());
            excel.setSchoolName(school.getName());
            excel.setUID(school.getUid());
            excel.setCreateTime(school.getCreateTime());
            schoolUidForExcels.add(excel) ;
        }
        ExcelUtil.exportExcel(schoolUidForExcels, "uid文档", "uid", SchoolUidForExcel.class, "uid.xls", response);
        return new ApiResult(ResultMsg.SUCCESS,null);

    }

    @Override
    public Page<School> findSchoolByAgentId(Long agentId, String schoolName, Integer page, Integer limit) {

        return schoolDao.findSchool(schoolName,null,null,
                null,null,null,null,null,agentId,PageableUtil.basicPage(page,limit));
    }

    @Override
    public ApiResult getAll() {
        List<School> all = schoolDao.getAll();
        return new ApiResult(ResultMsg.SUCCESS,all);
    }


}
