package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.service.RedeemCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/27
 **/

@RestController
@RequestMapping(value = "api/RechargeApi")
@Api(description = "充值相关")
public class RechargeApi {

    @Autowired
    RedeemCodeService redeemCodeService ;

    @RequestMapping(value = "recharge",method = {RequestMethod.POST})
    @ApiOperation(notes = "充值",value = "recharge")
    public ApiResult recharge(Long userId , String  schoolUID , String code){
     return    redeemCodeService.activeRedeemCode(code,userId,schoolUID) ;
    }



}

