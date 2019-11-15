package com.runer.cibao.api;

import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.service.NewReviceRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/9/3
 **/
@RestController
@Api(value = "NewReviewRecordApi",description = "统计新学复习的单词数量")
@RequestMapping(value = "api/NewReviewRecordApi")
public class NewReviewRecordApi {

    @Autowired
    NewReviceRecordService newReviceRecordService ;

    @ApiOperation(value = "更新复习新学",notes = "更新复习新学  \n yyyy-MM-dd HH:mm:ss")
    @RequestMapping(value = "uploadNewReviewRecord",method = RequestMethod.POST)
    public ApiResult uploadNewReviewRecord(Long appUserId ,String date ,String newIds ,String reviewIds){
       return newReviceRecordService.uploadNewReviewRecord(appUserId,new Date(),newIds,reviewIds);
    }

    @ApiOperation(value = "获得更新复习新学",notes = "获得更新复习新学  \n yyyy-MM-dd HH:mm:ss")
    @RequestMapping(value = "getReviewRecordNum",method = RequestMethod.POST)
    public ApiResult getReviewRecordNum(Long appUserId ,String date ){
        Date dateTime =null ;
        try {
            if (!StringUtils.isEmpty(date)) {
                dateTime = DateUtils.parseDate(date, Config.DATE_FORMAT_ALL);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            dateTime =new Date() ;
        }
        return newReviceRecordService.getReviewRecordNum(appUserId,dateTime);
    }


    @ApiOperation(value = "获得统计的新学和复习的个数",notes = "获得统计的新学和复习的个数  \n yyyy-MM-dd HH:mm:ss")
    @RequestMapping(value = "getReviewRecordNums",method = RequestMethod.POST)
    public ApiResult getReviewNum(Long appUserId ,String startTime ,String endTime  ){

        Date start =null ;
        Date end =null;

        try {
             if (!StringUtils.isEmpty(startTime)) {
                 start = DateUtils.parseDate(startTime);
             }
             if (!StringUtils.isEmpty(endTime)) {
                 end = DateUtils.parseDate(endTime);
             }
        } catch (ParseException e) {
            e.printStackTrace();
            return  new ApiResult("解析日期出现错误");
        }

        return newReviceRecordService.getReviewCounts(appUserId,start,end);
    }






}
