package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.HelpToUser;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.HelpToUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/21
 **/
@RestController
@Api(value = "help",description = "帮助相关")
@RequestMapping("api/help")
public class HelpApi {

    @Autowired
    HelpToUserService helpToUserService ;

    @ApiOperation(value = "获得帮助列表",notes = "获得帮助列表")
    @RequestMapping(value = "getHelpList",method = RequestMethod.POST)
    public ApiResult getHelpList(){
        Page<HelpToUser> pageResult = helpToUserService.findHelpToUsers(null, 1, Integer.MAX_VALUE);
        return  new ApiResult(ResultMsg.SUCCESS,pageResult.getContent()) ;
    }

    @ApiOperation(value = "获得帮助详情",notes = "获得帮助详情")
    @RequestMapping(value = "getHelpDetail",method = RequestMethod.POST)
    public ApiResult findHelpDetail(Long id){
        ApiResult helpResult = helpToUserService.findByIdWithApiResult(id);
        if (helpResult.getMsgCode()==ResultMsg.SUCCESS.getMsgCode()){
            HashMap<String,String> result =new HashMap<>() ;
            result.put("url","/help/helpDetail?id="+id);
         return  new ApiResult(ResultMsg.SUCCESS,result) ;
        }
        return  helpResult ;
    }

}
