package com.runer.cibao.web;

import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.School;
import com.runer.cibao.service.SchoolServivce;
import com.runer.cibao.service.UserLoginService;
import com.runer.cibao.util.NormalUtil;
import com.runer.cibao.util.PowerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author sww
 * @Date 2019/9/26
 **/

@RestController
@RequestMapping(value = "agentsSchools")
public class AgentsSchoolMasterController {

    @Autowired
    SchoolServivce schoolServivce ;

    @Autowired
    UserLoginService userLoginService ;

    @RequestMapping(value = "data_list")
    public LayPageResult<School> findDataList(String schoolName , String schoolMasterName , Long agentsId  , Integer page , Integer limit ){
        Page<School> pageData = schoolServivce.findSchool(schoolName, null, null, null, null, schoolMasterName, null, null, agentsId, page, limit);
        for (School school : pageData.getContent()) {
            if (school.getSchoolMaster()!=null) {
                school.setMasterId(school.getSchoolMaster().getId());
                school.setMastername(school.getSchoolMaster().getName());
            }
        }
        return NormalUtil.createLayPageReuslt(pageData);

    }
}
