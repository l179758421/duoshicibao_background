package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.AdminConfig;
import com.runer.cibao.domain.LearnTime;
import com.runer.cibao.domain.person_word.NewReviewRecord;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.AdminConfigService;
import com.runer.cibao.service.LearnTimeService;
import com.runer.cibao.service.NewReviceRecordService;
import com.runer.cibao.service.PunshCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/30
 **/

@RestController
@RequestMapping(value = "api/PunchCardApi")
@Api(value = "打卡",description = "打卡相关的api")
public class PunchCardApi {


    @Autowired
    private PunshCardService punshCardService ;

    @Autowired
    private AdminConfigService configService ;

    @Autowired
    private LearnTimeService learnTimeService ;

    @Autowired
    private NewReviceRecordService newReviewRecordService ;



    @ApiOperation(value = "获得总的打卡天数-累计打卡",notes = "获得总的打卡天数--返回数字")
    @RequestMapping(value = "punchCardDays",method = RequestMethod.POST)
    public ApiResult punchCardDays(Long userId){
        return  punshCardService.getPunchCardDays(userId);
    }

    @ApiOperation(value = "打卡",notes = "打卡")
    @RequestMapping(value = "punchCard",method = RequestMethod.POST)
    public ApiResult punchCard(Long userId){
        return  punshCardService.punchCard(userId);
    }

    @ApiOperation(value = "获得连续的几天",notes = "获得连续的几天-【0，0，0，0，0，0，1，0】")
    @RequestMapping(value = "getContinued",method = RequestMethod.POST)
    public ApiResult getContinued(Long userId){
        return  punshCardService.findSevenDays(userId);
    }


    @ApiOperation(value = "获得打卡界面的详情",notes = "获得打卡界面的详情")
    @RequestMapping(value = "getPunchCardsDetail",method = RequestMethod.POST)
    public ApiResult getPunchCardsDetail(Long userId){

        int allPunchDays = (int) punshCardService.getPunchCardDays(userId).getData();
        List<Integer> days = (List<Integer>) punshCardService.findSevenDays(userId).getData();
        AdminConfig adminConfig = (AdminConfig) configService.forceGetAdminConfig().getData();
        List<LearnTime> learnTimes = (List<LearnTime>) learnTimeService.getUploadLearnTime(userId,new Date() ).getData();
        LearnTime learnTime =learnTimes.get(0) ;
        int time = (int) (learnTime.getTime()/1000/60);
        //学习获得积分
        int score = (int) (learnTime.getTime()/1000/60/10*adminConfig.getStudyScore());
        List<NewReviewRecord> reviews = (List<NewReviewRecord>) newReviewRecordService.getReviewRecord(userId, new Date()).getData();
        int newNum =reviews.get(0).getNewNum();
        int oldNum =reviews.get(0).getOldNum() ;


        Map<String,Object> results =new HashMap<>() ;

        results.put("days",days) ;
        results.put("allPunchDays",allPunchDays) ;
        results.put("pushTimeScore",adminConfig.getAvrageCardScrore()) ;
        results.put("fulltimeScore",adminConfig.getCardScroeForFullDay());
        results.put("learntime",time) ;
        results.put("score",score) ;
        results.put("newNum",newNum);
        results.put("oldNum",oldNum);

        return  new ApiResult(ResultMsg.SUCCESS,results);
    }



}
