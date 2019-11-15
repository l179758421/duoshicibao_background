package com.runer.cibao.api;


import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.PersonalLearnInfoService;
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
import java.util.HashMap;
import java.util.Map;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/9/5
 **/
@RestController
@Api(value = "RankingApi",description = "排名相关的api")
public class RankingApi {

    @Autowired
    PersonalLearnInfoService infoService ;

    @ApiOperation(value = "获得个人的各种排名",notes = "获得个人的各种排名")
    @RequestMapping(value = "getOnePersonalRanking",method = RequestMethod.POST)
    public ApiResult getOnePersonalRanking(Long appUserId, String date){

        Date dataDate=null ;
        dataDate =wrongDate(date) ;
        if (dataDate==null){
            return  new ApiResult("日期格式不正确");
        }

        ApiResult schoolResult = infoService.getOnePersonalRanking(appUserId, Config.RANKING_SCHOOL_TYPE, dataDate);;
        ApiResult provinceResult = infoService.getOnePersonalRanking(appUserId, Config.RANKING_PROVINCE_TYPE, dataDate);;
        ApiResult countryResult = infoService.getOnePersonalRanking(appUserId, Config.RANKING_COUNTRY_TYPE, dataDate);;

        Map<String,Object> results =new HashMap();
        results.put("school",schoolResult.getData());
        results.put("province",provinceResult.getData());
        results.put("country",countryResult.getData());
        return  new ApiResult(ResultMsg.SUCCESS,results);
    }

    private   Date   wrongDate( String date   ){
        try {
            if (StringUtils.isEmpty(date)){
                return  null;
            }
            Date result = DateUtils.parseDate(date, Config.DATE_FORMAT_ONLY_DAY);
            return  DateUtils.addMinutes(result,1);
        } catch (ParseException e) {
            e.printStackTrace();
            return  null ;
        }
    }



    @ApiOperation(value = "获得排名的情况",notes = "获得排名的情况")
    @RequestMapping(value = "getRankingInfo",method = RequestMethod.POST)
    public  ApiResult getRankingInfo(Long appUserId , Integer topNum ,String  date  ,Integer type){
        Date dataDate=null ;
        dataDate =wrongDate(date) ;
        if (dataDate==null){
            return  new ApiResult("日期格式不正确");
        }
        return  infoService.getRankingInfo(appUserId,topNum,dataDate,type) ;
    }
    @ApiOperation(value = "获得排名的情况",notes = "获得排名的情况")
    @RequestMapping(value = "getRankingAll",method = RequestMethod.POST)
    public   ApiResult getRankingAll(String  schoolId ,Long provinceId,Integer topNum ,String  date ,Integer type){
        Date dataDate=null ;
        dataDate =wrongDate(date) ;
        if (dataDate==null){
            return  new ApiResult("日期格式不正确");
        }
        return  infoService.getRankingALl(schoolId,provinceId,topNum,dataDate,type);
    }




}
