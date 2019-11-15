package com.runer.cibao.web;

import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.AppUser;
import com.runer.cibao.domain.School;
import com.runer.cibao.domain.SignTimeRecord;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.AppUserService;
import com.runer.cibao.service.SchoolServivce;
import com.runer.cibao.service.SignTimeRecordService;
import com.runer.cibao.util.Encoder;
import com.runer.cibao.util.NormalUtil;
import com.runer.cibao.util.machine.IdsMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("uid")
public class UIDController {
    @Autowired
    AppUserService appUserService;
    @Autowired
    SchoolServivce schoolServivce;
    @Autowired
    Encoder encoder ;
    @Autowired
    SignTimeRecordService signTimeRecordService;
    @Autowired
    IdsMachine idsMachine;


    @RequestMapping("getAllSchool")
    public ApiResult getAllSchool() {
        List<School> all = schoolServivce.findAll();
        return new ApiResult(ResultMsg.SUCCESS,all);
    }

    @RequestMapping("deleteById")
    public ApiResult deleteById(Long  id ) {
        try {
            appUserService.deleteById(id );
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        return  new ApiResult(ResultMsg.SUCCESS,null) ;

    }
    @RequestMapping("deleteByIds")
    public ApiResult deleteById(String ids) {
        for (Long id:idsMachine.deparseIds(ids)) {
            List<SignTimeRecord> signTimeRecords = signTimeRecordService.findByUserId(id);
            if(signTimeRecords.size() >0){
                return new ApiResult("所选账号包含已被使用账号,请重新选择!");
            }
        }
        try {
            appUserService.deleteByIds(ids );
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        return  new ApiResult(ResultMsg.SUCCESS,null) ;

    }

    @RequestMapping("UID_list")
    public LayPageResult<AppUser> getUidList(String uid, String schoolId  ,Integer page , Integer limit){
       Page<AppUser> uidUsersPage= appUserService.findAppUsers(schoolId,null,null,
               null,null,uid, Config.isBatchCreate,page,limit);
       uidUsersPage.forEach(appUser -> {
         String pass64 =   appUser.getPass64();
         appUser.setPass64(encoder.decodePwd(pass64));
       });
        for (AppUser app:uidUsersPage) {
            List<SignTimeRecord> signTimeRecords = signTimeRecordService.findByUserId(app.getId());
            if (signTimeRecords.size() > 0) {
                //最近登录时间
                app.setLastSignTime(signTimeRecords.get(0).getSignDate().toString());
            }
        }

        return NormalUtil.createLayPageReuslt(uidUsersPage);
    }

    @RequestMapping("addUidIndex")
    public String addUid(ModelMap map){
        List<School> schools = schoolServivce.findAll();;

        map.put("schools",schools);
        return "uid/add_uid";
    }

    @RequestMapping("saveUID")
    ApiResult saveUID(Integer num, String passWord, Long schoolId){
        return appUserService.batchCreateUID(num,passWord,schoolId);
    }
    @RequestMapping("exortUID")
    public void exportSchool( HttpServletResponse httpResponse){
        appUserService.exportUid(httpResponse);
    }
    @RequestMapping("exortUID_ids")
    public void exportSchool( HttpServletResponse httpResponse ,String ids,String title,String sheetname){
        appUserService.exportUidByIds(ids,title,sheetname);
    }

}
