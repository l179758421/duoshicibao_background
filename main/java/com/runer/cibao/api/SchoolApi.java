package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.School;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.SchoolServivce;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/12
 **/

@RestController
@RequestMapping("api/school")
@Api(value = "school" ,description = "学校相关")
public class SchoolApi {

    @Autowired
    SchoolServivce schoolServivce ;

    @ApiOperation(value = "schoollist",notes = "获得学校列表")
    @RequestMapping(value = "schoolList",method = RequestMethod.POST)
    ApiResult getSchools(){
     List<School> schools = schoolServivce.findAll();
     return  new ApiResult(ResultMsg.SUCCESS,schools);
    }


}
