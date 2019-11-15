package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.Agents;
import com.runer.cibao.domain.ClassInSchool;
import com.runer.cibao.domain.School;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.AgentsService;
import com.runer.cibao.service.SchoolServivce;
import com.runer.cibao.service.UserService;
import com.runer.cibao.util.NormalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author sww
 * @Date 2019/9/25
 **/

@RestController
@RequestMapping(value = "agents")
public class AgentsController {


    @Autowired
    AgentsService beanService ;

    @Autowired
    SchoolServivce schoolServivce ;

    @Autowired
    UserService userService ;


    @RequestMapping(value = "data_list")
    public LayPageResult<ClassInSchool> getDataList(String agentName  , String phone , Integer page , Integer limit){

        Page<Agents> pageResult = beanService.findAgents(agentName, phone, page, limit);

        return NormalUtil.createLayPageReuslt(pageResult) ;
    }


    @RequestMapping(value = "addOrUpdateData")
    public ApiResult addOrUpdateData(Long id, String name, Long provinceId,
                                     Long cityId, Long areaId, String phone, String email,
                                     Integer sex, String address, String birthDay , String headerFile){

        Date birthDayForInput = null ;
        try {
            birthDayForInput =new SimpleDateFormat("yyyy-MM-dd").parse(birthDay);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date time = new Date();
            String now = formatter.format(time);
            time = new SimpleDateFormat("yyyy-MM-dd").parse(now);
            if(time.before(birthDayForInput)){
                return new  ApiResult("生日日期超出实际范围!");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    return     beanService.addOrUpdateAgents(id,name,provinceId,cityId,areaId,phone,email,sex,address,birthDayForInput,headerFile);

    }

    @RequestMapping("deleteByIds")
    public ApiResult deleteByIds(String ids){
        String[] id1 = ids.split(",");
        for (String id2:id1) {
            Long id3 = Long.parseLong(id2);
            try {
                Agents agents = beanService.findById(id3);
                if(agents != null && agents.getUser() != null){
                    Long userId = agents.getUser().getId();
                    userService.deleteById(userId);
                }
            } catch (SmartCommunityException e) {
                e.printStackTrace();
            }
        }

        return  NormalUtil.deleteByIds(beanService,ids) ;
    }

    @RequestMapping("deleteById")
    public ApiResult deleteById(Long id){

        try {
            Agents agents = beanService.findById(id);
            if(agents != null && agents.getUser() != null){
                Long userId = agents.getUser().getId();
                userService.deleteById(userId);
            }
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        return  NormalUtil.deleteById(beanService,id);
    }


    @RequestMapping(value = "ditribeSchools")
    public ApiResult distribeSchools(Long id ,String ids){
        return  beanService.distribeAgentsSchool(id,ids);
    }

    @RequestMapping(value = "getAgentsSchools")
    public LayPageResult<School> findAgentsSchools(Long id , Integer page , Integer limit){
        Page<School> schools = beanService.findAgentsSchoolsByAgentsId(id, page, limit);
        return  NormalUtil.createLayPageReuslt(schools);
    }
}
