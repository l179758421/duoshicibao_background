package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.School;
import com.runer.cibao.domain.SchoolMaster;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.SchoolMasterService;
import com.runer.cibao.service.SchoolServivce;
import com.runer.cibao.service.UserService;
import com.runer.cibao.util.NormalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author sww
 * @Date 2019/9/25
 **/

@RestController
@RequestMapping(value = "schoolMaster")
public class SchoolMasterController {


    @Autowired
    SchoolMasterService schoolMasterService ;

    @Autowired
    SchoolServivce schoolServivce ;

    @Autowired
    UserService userService ;





    @RequestMapping(value = "school_master_list")
    public LayPageResult<SchoolMaster> getSchoolListfindSchoolMaster(String schoolName , String masterName ,
                                                               String address,Integer page ,Integer limit ){
        Page<SchoolMaster> schoolPage =  schoolMasterService.findSchoolMaster(schoolName,masterName,address,page,limit);
        return NormalUtil.createLayPageReuslt(schoolPage) ;
    }


    @RequestMapping(value = "addOrUpdateSchoolMaster")
    public ApiResult addOrUpdateSchoolMaster(Long id ,String name , Long schoolId ,Long provinceId
            ,Long cityId ,Long areaId, String phone , String email , Integer sex, String address ,String birthDay , String headerFile){
        Date birthDayDate =null ;
        try {
            if (!StringUtils.isEmpty(birthDay)){
                birthDayDate =new SimpleDateFormat("yyyy-MM-dd").parse(birthDay);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date time = new Date();
                String now = formatter.format(time);
                time = new SimpleDateFormat("yyyy-MM-dd").parse(now);
                if(time.before(birthDayDate)){
                    return new  ApiResult("生日日期超出实际范围!");
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(provinceId != null && cityId != null && areaId != null){
            try {
                School school = schoolServivce.findById(schoolId);
                school.setPhone(phone);
                schoolServivce.addOrUpdateSchool(school.getId(),school.getName(),school.getCityId(),school.getProvinceId(),
                        school.getAreaId(),school.getAddress(),school.getPhone(),school.getShcoolDes());
            } catch (SmartCommunityException e) {
                e.printStackTrace();
            }
            return schoolMasterService.addOrUpdateSchoolMaster(id,name,schoolId,provinceId,cityId,areaId,phone,email,sex,address,birthDayDate,headerFile);
        }
        return new ApiResult("请完善学校地区信息!");
    }


    @RequestMapping("deleteByIds")
    public ApiResult deleteByIds(String ids){
        String[] id1 = ids.split(",");
        for (String id2:id1) {
            Long id3 = Long.parseLong(id2);
            try {
                SchoolMaster schoolMaster = schoolMasterService.findById(id3);
                if(schoolMaster != null && schoolMaster.getUser() != null){
                    Long userId = schoolMaster.getUser().getId();
                    userService.deleteById(userId);
                }
            } catch (SmartCommunityException e) {
                e.printStackTrace();
            }
        }
        return  NormalUtil.deleteByIds(schoolMasterService,ids) ;
    }



    @RequestMapping("deleteById")
    public ApiResult deleteById(Long id){
        try {
            SchoolMaster schoolMaster = schoolMasterService.findById(id);
            if(schoolMaster != null && schoolMaster.getUser() != null){
                Long userId = schoolMaster.getUser().getId();
                userService.deleteById(userId);
            }
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }

        return  NormalUtil.deleteById(schoolMasterService,id);
    }




}
