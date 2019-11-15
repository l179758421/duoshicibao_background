package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.PageApiResult;
import com.runer.cibao.domain.IntegralDetail;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.IntegralDesService;
import com.runer.cibao.service.IntegralService;
import com.runer.cibao.service.PunshCardService;
import com.runer.cibao.util.NormalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "积分相关api",description = "积分相关")
@RequestMapping("api/Integral")
public class IntegralApi {

    @Autowired
    IntegralService integralService;
    @Autowired
    PunshCardService punshCardService;

    @Autowired
    IntegralDesService integralDesService;


    @ApiOperation(value="获得用户积分",notes = "获得用户积分")
    @RequestMapping(value = "getUserIntegral",method = RequestMethod.POST)
    public ApiResult getUserIntegral(Long appUserId){
        try {
          return   integralService.findUserIntegral(appUserId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(ResultMsg.OS_ERROR,null);
        }
    }


    @ApiOperation(value = "积分的来源和使用说明",notes = "积分的来源和使用说明")
    @RequestMapping(value = "getIntegralDes",method = RequestMethod.POST)
    public ApiResult getIntegralDes(){

       return integralDesService.findIntegralSourceAndUse();
    }

    @ApiOperation(value = "获得个人积分明细",notes = "获得个人积分明细")
    @RequestMapping(value = "getPersonalIntegralDetail",method = RequestMethod.POST)
    public PageApiResult getPersonalIntegralDetail(Long appUserId, Integer page , Integer limit){
        if (appUserId==null){
            return  new PageApiResult("用户id不能为 i 空");
        }
        Page<IntegralDetail> pages = integralService.getPersonalIntegralDetail(appUserId,page,limit);
        return  NormalUtil.createPageResult(pages);
    }


}
