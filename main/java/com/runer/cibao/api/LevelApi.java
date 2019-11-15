package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.service.AdminLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "级别相关api",description = "级别相关")
@RequestMapping("api/Level")
public class LevelApi {
    @Autowired
    AdminLevelService adminLevelService;

    @ApiOperation(value = "获取级别",notes = "获取级别")
    @RequestMapping(value = "getLevelDes",method = RequestMethod.POST)
    public ApiResult getLevelDes(Long appUserId){
       return adminLevelService.getLevel(appUserId);
    }
}
