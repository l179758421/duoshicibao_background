package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.service.MailAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/21
 **/

@RestController
@Api(value = "AddresApi",description = "地址相关")
@RequestMapping(value = "api/AddresApi")
public class AddressApi  {

    @Autowired
    MailAddressService mailAddressService ;

    @ApiOperation(value = "获取收货地址",notes = "获取收货地址")
    @RequestMapping(value = "getMailAddress",method = RequestMethod.POST)
    public  ApiResult getMailAddress(Long userId){
      return   mailAddressService.findMailAddressByUserId(userId);
    }

    @ApiOperation(value = "修改或者添加收货地址",notes = "修改或者添加收货地址")
    @RequestMapping(value = "addOrUpdateMailAddress",method = RequestMethod.POST)
    public ApiResult addOrUpdateAddress(Long id,Long userId
            ,String phone,String detailAddress,String receiveName
            ,Long provinceId ,Long cityId ,Long areaId){
      return   mailAddressService.addOrUpdateMailAddress(id,userId,phone,detailAddress,receiveName,provinceId,cityId,areaId);
    }






}


