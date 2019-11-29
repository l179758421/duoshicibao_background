package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.Read;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.ReadService;
import com.runer.cibao.util.NormalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 阅读精选
 * @Author sww
 * @Date 2019/11/27
 **/
@RestController
@Api(value = "阅读精选相关的Api",description = "阅读精选相关的Api")
@RequestMapping(value = "readApi")
public class ReadApi {

    @Autowired
    ReadService beanService ;


    @ApiOperation(value = "阅读精选",notes = "获取阅读精选")
    @RequestMapping(value = "getData",method = {RequestMethod.GET})
    public ApiResult getData (){
        List<Read> all = beanService.findAll();
        return new ApiResult(ResultMsg.SUCCESS,all);
    }

}
