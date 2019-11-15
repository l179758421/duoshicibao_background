package com.runer.cibao.service.impl;

import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.SchoolMasterDao;
import com.runer.cibao.domain.*;
import com.runer.cibao.domain.repository.SchoolMasterRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import com.runer.cibao.util.Encoder;
import com.runer.cibao.util.PowerUtil;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/6
 **/
@Service
public class SchoolMasterServiceImpl extends BaseServiceImp<SchoolMaster,SchoolMasterRepository> implements SchoolMasterService {


    @Autowired
    SchoolMasterRepository repository ;

    @Autowired
    SchoolMasterDao schoolMasterDao ;

    @Autowired
    AreaService areaService ;

    @Autowired
    Encoder encoder ;

    @Autowired
    UserService userService ;

    @Autowired
    RolesService rolesService ;


    @Autowired
    SchoolServivce schoolServivce ;



    @Override
    public Page<SchoolMaster> findSchoolMaster(String schoolName, String masterName, String address ,Integer page ,Integer limit) {
       return schoolMasterDao.findSchools(schoolName,masterName,address,PageableUtil.basicPage(page,limit));

    }


    @Value("${web.upload-cibaoPath}")
    private String rePath ;

    @Value("${web.upload-cibao}")
    private String abPath ;


    /**
     * 添加，更细，设置user ；
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
     * @param birthDay
     * @param headerFile
     * @return
     */
    @Override
    public ApiResult addOrUpdateSchoolMaster(Long id, String name, Long schoolId, Long provinceId,
                                             Long cityId, Long areaId, String phone, String email,
                                             Integer sex, String address, Date birthDay ,String headerFile) {

        if (schoolId==null){
            return  new ApiResult(ResultMsg.SCHOOL_ID_IS_NULL,null) ;
        }

        SchoolMaster schoolMaster =new SchoolMaster() ;
        if (id!=null) {
           ApiResult existResult =findByIdWithApiResult(id) ;
           if (existResult.isFailed()){
               return  existResult ;
           }
          schoolMaster = (SchoolMaster) existResult.getData();
        }

        schoolMaster.setAddress(address);
        schoolMaster.setId(id);
        schoolMaster.setName(name);
        if(StringUtils.isEmpty(phone)){
            return new ApiResult("手机号不能为空!");
        }
        schoolMaster.setPhone(phone);
        schoolMaster.setEmail(email);
        schoolMaster.setSex(sex);
        schoolMaster.setBirthday(birthDay);

        if (id==null) {
            schoolMaster.setAddTime(new Date());
        }




        School school =new School() ;
        school.setId(schoolId);
        schoolMaster.setSchool(school);


        schoolMaster.setAreaId(areaId);
        schoolMaster.setProvinceId(provinceId);
        schoolMaster.setCityId(cityId);

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
        addreeDetail+=":"+schoolMaster.getAddress();

        schoolMaster.setDetailAddress(addreeDetail);

        if (!StringUtils.isEmpty(headerFile)){
            schoolMaster.setHeaderImgUrl(headerFile);
        }

        //关联user 设置密码；
        if (id==null){
            ApiResult rolesResult = PowerUtil.generateRolesUser(userService,rolesService,
                    phone,name, Config.ROLES_SCHOOL_MASTER,encoder) ;
            if (rolesResult.isFailed()){
                if (rolesResult.getMsgCode()==ResultMsg.LOGIN_NAME_REPEATED.getMsgCode()){
                    return new ApiResult("该手机号已存在!");
                }
                return  rolesResult ;
            }
            schoolMaster.setUser((User) rolesResult.getData());
        }

        schoolMaster =r.saveAndFlush(schoolMaster);
        return  new ApiResult(ResultMsg.SUCCESS, schoolMaster) ;
    }

    @Override
    public ApiResult changePass(Long userId, String pass) {

      ApiResult masterResult =  findByIdWithApiResult(userId);

      if (masterResult.getMsgCode()!=ResultMsg.SUCCESS.getMsgCode()){
          return  masterResult ;
      }

      SchoolMaster schoolMaster = (SchoolMaster) masterResult.getData();

      User user =schoolMaster.getUser() ;
      user.setPassWord(encoder.passwordEncoderByMd5(pass));
        try {
            userService.saveOrUpdate(user);
            return  new ApiResult(ResultMsg.SUCCESS,schoolMaster) ;
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null) ;
        }
    }

    @Override
    public SchoolMaster findSchoolMasterBySchoolId(Long schoolId) {

        return r.findSchoolMasterBySchoolId(schoolId);
    }


}
