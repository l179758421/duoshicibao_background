package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.LearnTime;
import com.runer.cibao.domain.OnlineTime;
import com.runer.cibao.domain.person_word.NewReviewRecord;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.LearnTimeService;
import com.runer.cibao.service.NewReviceRecordService;
import com.runer.cibao.service.OnlineTimeService;
import com.runer.cibao.util.machine.DateMachine;
import com.runer.cibao.util.machine.IdsMachine;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.ListUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author k
 * @Date: Created in 10:00 2018/8/22
 * @Description: 单词统计相关
 */
@RestController
@RequestMapping("wordCount")
@Api(value = "api/wordCount",description = "数据统计-发现")
public class WordCountApi {

    @Autowired
    OnlineTimeService onlineTimeService ;


    @Autowired
    LearnTimeService learnTimeService ;


    @Autowired
    NewReviceRecordService newReviceRecordService ;


    @Autowired
    DateMachine dateMachine ;


    @Autowired
    IdsMachine idsMachine ;

    @ApiOperation(value = "newAndReviewsInfos",notes = "新学和复习的统计")
    @RequestMapping(value = "newAndReviewsInfos",method = RequestMethod.POST)
    public ApiResult getNewAndReviewsInfos(Long appUserId ){

        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");

        //获得七天的学习情况
        Map<String,Object> datas =new HashMap<>();

        Map<String,Integer> news =new TreeMap<>() ;
        Map<String,Integer> olds =new TreeMap<>() ;

        Date today =new Date() ;
        for (int i = 0; i <7 ; i++) {
            Date currentDay =DateUtils.addDays(today,-i) ;
            List<NewReviewRecord> newReview = (List<NewReviewRecord>) newReviceRecordService.getReviewRecord(appUserId, currentDay).getData();
            int newNum = 0 ;
            int reviewNum =0 ;
            if (!ListUtils.isEmpty(newReview)){
                newNum=idsMachine.deparseIds(newReview.get(0).getNewWordsIds()).size() ;
                reviewNum =idsMachine.deparseIds(newReview.get(0).getReviewsIds()).size() ;
            }
            news.put(simpleDateFormat.format(currentDay),newNum) ;
            olds.put(simpleDateFormat.format(currentDay),reviewNum) ;
        }
        datas.put("newDatas",news) ;
        datas.put("oldDatas",olds) ;

        return  new ApiResult(ResultMsg.SUCCESS,datas) ;

    }




    @ApiOperation(value = "wordCount",notes = "数据统计-发现")
    @RequestMapping(value = "getLearnTimeCount",method = RequestMethod.POST)
    public ApiResult getUnitCount(Long appUserId){
        //今日的学习时间
        List<OnlineTime> times = onlineTimeService.findByUserAndDate(appUserId, new Date());
        long  currentDayOnlineTime = 0 ;
        if (!ListUtils.isEmpty(times)){
            currentDayOnlineTime =times.get(0).getTime()/60 ;
        }
        List<LearnTime> learnTimes  = (List<LearnTime>) learnTimeService.getUploadLearnTime(appUserId,new Date()).getData();
        long currentDayLearnTime =0 ;
        if(!ListUtils.isEmpty(learnTimes)){
            currentDayLearnTime =learnTimes.get(0).getTime()/1000/60;
        }

        Map<String,Object> results =new HashMap() ;
        results.put("todayLearnTime",currentDayLearnTime) ;
        results.put("todayOnlineTime",currentDayOnlineTime) ;

        long allOnelineTime = 0 ;
        for (OnlineTime onlineTime : onlineTimeService.findByUserId(appUserId)) {
           allOnelineTime+=onlineTime.getTime() ;
        }
        long allLearnTime =0 ;
        for (LearnTime time : learnTimeService.getUploadLearnTime(appUserId)) {
            allLearnTime+=time.getTime() ;
        }
        results.put("allLearnTime",allLearnTime/1000/60) ;
        results.put("allOnlineTime",allOnelineTime/60) ;
        return  new ApiResult(ResultMsg.SUCCESS,results) ;
    }


}
