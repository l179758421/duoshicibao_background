package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.AppUserBindSchool;
import com.runer.cibao.domain.User;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.AppUserBindSchoolService;
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

import javax.servlet.http.HttpServletRequest;

import static com.runer.cibao.exception.ResultMsg.SUCCESS;

/**
 * @Author sww
 * @Date 2019/9/25
 **/
@RestController
@RequestMapping(value = "appUserBindSchool")
public class AppUserBindSchoolController {


    @Autowired
    AppUserBindSchoolService beanService ;

    @Autowired
    UserLoginService userLoginService;




    @RequestMapping(value = "data_list")
    public LayPageResult<AppUserBindSchool> getDataList(Long userId ,Long adminUserId ,String userName ,
                                                    Long schoolId ,Integer state ,Integer page ,Integer limit){
        Page<AppUserBindSchool> binds = beanService.findSchoolBinds(userId, adminUserId,userName, schoolId, state, page, limit);
        for (AppUserBindSchool a:binds) {
            a.getAppUser().setName(a.getAppUser().getRealNameForInfo()==null?"":a.getAppUser().getRealNameForInfo());
        }
        return  NormalUtil.createLayPageReuslt(binds);

    }


    @RequestMapping(value = "aggreeApplyApi")
    public ApiResult addOrUpdateData(Long id, HttpServletRequest req){
        User currentUser = userLoginService.getCurrentUser(req);
        if (currentUser==null){
            return  new ApiResult(ResultMsg.NOT_FOUND,null);
        }
       return   beanService.agreeBindSchool(id,currentUser.getId()) ;
    }



    @RequestMapping("agreeBinds")
    public ApiResult agreeBinds(String ids,HttpServletRequest req ){
        User currentUser = userLoginService.getCurrentUser(req);
        if (currentUser==null){
            return  new ApiResult(ResultMsg.NOT_FOUND,null);
        }
        return  beanService.agreeBinds(ids,currentUser.getId()) ;
    }

    @RequestMapping("deleteByIds")
    public ApiResult deleteByIds(String ids){
        return  NormalUtil.deleteByIds(beanService,ids) ;
    }


    @RequestMapping("deleteById")
    public ApiResult deleteById(Long id){
        return  NormalUtil.deleteById(beanService,id);
    }

}
