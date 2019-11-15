package com.runer.cibao.api;

import com.runer.cibao.base.PageApiResult;
import com.runer.cibao.domain.AppUserOrder;
import com.runer.cibao.service.AppUserOrderService;
import com.runer.cibao.util.NormalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/27
 **/
@RestController
@RequestMapping(value = "api/orderApi")
@Api(value = "api/orderApi",description = "订单相关")
public class OrderApi {


    @Autowired
    AppUserOrderService appUserOrderService ;


    @ApiOperation(value = "获得订单列表",notes = "获得订单列表")
    @RequestMapping(value = "getOrderList",method = RequestMethod.POST)
    public PageApiResult<AppUserOrder> getOrderList(Integer type , Long userId  , Integer page , Integer limit){
     Page<AppUserOrder> pageResult = appUserOrderService.findOrders(null, type, userId, page, limit);
     return    NormalUtil.createPageResult(pageResult);
    }




}
