package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.service.PersonalBookApiService;
import com.runer.cibao.service.PersonalLearnUnitService;
import com.runer.cibao.service.SyncUserLearnService;
import com.runer.cibao.service.WordLearnProgressService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/12/30
 **/
@Api(description = "同步个人学习的情况")
@RequestMapping(value = "api/syncUserLearnInfoApi")
@RestController
public class SyncUserLearnInfoApi {


    @Autowired
    SyncUserLearnService syncUserLearnService ;


    @Autowired
    WordLearnProgressService wordLearnProgressService ;

    @Autowired
    PersonalLearnUnitService unitService ;

    @Autowired
    PersonalBookApiService personalBookApiService ;

    @PostMapping(value = "syncPersonalLearnInfo")
    public ApiResult syncPersonalLearnInfo(String info ,String states){
        try {
          return  wordLearnProgressService.syncCurrentLearnInfo(info,states) ;
        } catch (Exception e) {
            e.printStackTrace();
            return  new ApiResult(e.getMessage()) ;
        }
    }

    @PostMapping(value = "getSyncInfo")
    public ApiResult getSyncUserLearnInf(Long  userId ){
     return   syncUserLearnService.syncHomeInfo(userId) ;
    }


    @PostMapping(value = "syncLoginInfo")
    public ApiResult syncLoginInfo(Long  userId ){
        return   syncUserLearnService.syncLoginInfo(userId) ;
    }
}
