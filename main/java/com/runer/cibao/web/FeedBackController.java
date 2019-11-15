package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.FeedBack;
import com.runer.cibao.domain.ImageInDbForCache;
import com.runer.cibao.domain.School;
import com.runer.cibao.domain.User;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.FeedBackService;
import com.runer.cibao.service.ImageInDbForCacheService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "feedback")
public class FeedBackController {
    @Autowired
    FeedBackService feedBackService;

    @Autowired
    UserLoginService userLoginService ;

    @Autowired
    SchoolServivce schoolServivce;

    @Autowired
    ImageInDbForCacheService imageInDbForCacheService;

    @RequestMapping(value = "getSchool")
    public ApiResult getSchool(HttpServletRequest req){
        User user = userLoginService.getCurrentUser(req);
        if(!user.getLoginName().equals("Admin")){
//            String error = PowerUtil.schoolMasterPower(userLoginService,modelMap, member -> {
//                if (member.getSchoolMaster().getSchool()!=null){
//                    modelMap.put("school",member.getSchoolMaster().getSchool()) ;
//                }
//            });
//            if (!StringUtils.isEmpty(error)){
//                return  error ;
//            }
//            return "feedback/feed_back_manage";
        }else{
        }
        return new ApiResult(ResultMsg.SUCCESS,user);
    }

    @RequestMapping(value = "data_list")
    public LayPageResult<FeedBack> feedBackList(Long schoolId, Long askUserId , String askName , Long answerUserId , String answerUserName , Integer ifSolve, Integer page , Integer limit){
        try {
            if(schoolId != null){
                School school =schoolServivce.findById(schoolId);
                Page<FeedBack> pageResult = feedBackService.findFeedBacks(school.getUid(),askUserId,askName,answerUserId,answerUserName,ifSolve,page,limit);
                return NormalUtil.createLayPageReuslt(pageResult) ;
            }else{
                Page<FeedBack> pageResult = feedBackService.findFeedBacks(null,askUserId,askName,answerUserId,answerUserName,ifSolve,page,limit);
                return NormalUtil.createLayPageReuslt(pageResult) ;
            }
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("deleteById")
    public ApiResult deleteById(Long id){
        return  NormalUtil.deleteById(feedBackService,id);
    }


    @RequestMapping("getById")
    public ApiResult getById(Long id){

        return feedBackService.getById(id);
    }

    @RequestMapping("deleteByIds")
    public ApiResult deleteByIds(String ids){

        return  NormalUtil.deleteByIds(feedBackService,ids) ;
    }

    @RequestMapping(value = "updateFeedBack")
    public ApiResult updateFeedBack(Long id, String answerContent, HttpServletRequest req){
        User currentUser = userLoginService.getCurrentUser(req);
        Long userId =null;
        if (currentUser!=null){
            userId = currentUser.getId() ;
        }else{
            return  new ApiResult(ResultMsg.USER_ID_IS_NOT_ALLOWED_NULL,null) ;
        }
        return  feedBackService.answerFeedBack(id,answerContent,userId);
    }

}
