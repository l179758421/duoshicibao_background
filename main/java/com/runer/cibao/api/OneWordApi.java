package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.AppUser;
import com.runer.cibao.domain.OneWord;
import com.runer.cibao.domain.PersonlLearnInfoBean;
import com.runer.cibao.domain.School;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.*;
import com.runer.cibao.util.machine.DateMachine;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.ListUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/11/1
 **/
@RestController
@Api(value = "api/oneWordApi",description = "名言相关")
public class OneWordApi {

    @Autowired
    OneWordService oneWordService ;

    @Autowired
    AppUserService appUserService ;

    @Autowired
    PunshCardService punchCardService ;

    @Autowired
    SchoolServivce schoolServivce;

    @Autowired
    PersonalLearnInfoService personalLearnInfoService ;

    @Autowired
    IntegralService integralService ;

    @Autowired
    DateMachine dateMachine ;

    @ApiOperation(value = "获得当天的名言",notes = "获得当天的名言")
    @RequestMapping(value = "getOneWord",method = RequestMethod.POST)
    public ApiResult getOneWord(Long appUserId){
        return  oneWordService.getOneWord(appUserId);
    }

    @ApiOperation(value = "获得分享的内容",notes = "获得分享的内容")
    @RequestMapping(value = "getShareContent",method = RequestMethod.POST)
    public ApiResult getShareContent(Long appUserId ){


        Map<String,Object> results = new HashMap<>() ;
        AppUser appUser  =null ;
        ApiResult userResult = appUserService.findByIdWithApiResult(appUserId);
        if (userResult.isSuccess()){
            appUser = (AppUser) userResult.getData();
            results.put("userName",appUser.getName()) ;
            results.put("imgUrl",appUser.getImgUrl()) ;
        }else{
            return  userResult ;
        }

        OneWord oneWord = (OneWord) getOneWord(appUserId ).getData();
        results.put("oneWord",oneWord) ;

        int pushCarddays = (int) punchCardService.getPunchCardDays(appUserId).getData();
        results.put("pushCarddays",pushCarddays) ;

        Date[] oneDates = dateMachine.getOneDayTimes(new Date());;

        List cards = (List) punchCardService.findCards(appUserId, oneDates[0], oneDates[1]).getData();
        if (!ListUtils.isEmpty(cards)){
            results.put("isPushCarded",true) ;
        }else{
            results.put("isPushCarded",false) ;
        }

        PersonlLearnInfoBean infoBean = (PersonlLearnInfoBean) personalLearnInfoService.generateOneLearnInfo(appUserId,new Date()).getData();

        results.put("wordsNum",infoBean.getAllWords()+infoBean.getWordsNumReview());
        results.put("learnTime",infoBean.getLearnTime()/1000/60) ;


        if (!StringUtils.isEmpty(appUser.getSchoolId())){
          School school = (School) schoolServivce.findSchoolByUID(appUser.getSchoolId()).getData();
          if (school!=null) {
              results.put("schoolName", school.getName());
              results.put("schoolPhone",school.getPhone());
          }else{
              results.put("schoolName", "");
              results.put("schoolPhone","");
          }
        }
        return  new ApiResult(ResultMsg.SUCCESS,results) ;
    }

    @ApiOperation(value = "分享成功进行调用",notes = "分享成功进行调用")
    @RequestMapping(value = "shareCallback",method = RequestMethod.POST)
    public ApiResult shareCallback(Long appUserId){
      return   integralService.addShareIntegral(appUserId) ;
    }


}
