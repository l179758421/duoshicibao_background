package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.service.AdvertisementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/15
 **/

@RestController
@Api(value = "adApi",description = "地址相关")
@RequestMapping(value = "api/adApi")
public class AdApi {

    @Autowired
    AdvertisementService advertisementService;

    @ApiOperation(value = "ads" )
    @RequestMapping(value = "ads",method = RequestMethod.POST)
    public ApiResult findAds(Integer type ){
     return    advertisementService.findAllAds("",type,true) ;
    }



}
