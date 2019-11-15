package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.PageApiResult;
import com.runer.cibao.domain.AppUserOrder;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.AreaService;
import com.runer.cibao.service.BookOrdersInfoService;
import com.runer.cibao.util.NormalUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/6
 **/

@RestController
@Api(value = "area",description = "地区相关")
@RequestMapping(value = "api/area")
public class AreaApi {


    @Autowired
    AreaService areaService ;

    @Autowired
    BookOrdersInfoService bookOrdersInfoService ;

    @RequestMapping(value = "findOrders",method = {RequestMethod.POST})
    public PageApiResult<AppUserOrder> findOrders(Long schoolId  ,Integer page ,Integer limit ){
        Page<AppUserOrder> pages = bookOrdersInfoService.getBooksCreateOrders(schoolId, null, null, null, null, page, limit);
         return NormalUtil.createPageResult(pages) ;
    }

    @RequestMapping(value = "findProvinces",method = {RequestMethod.POST})
    public ApiResult findProvinces(){
     return new ApiResult(ResultMsg.SUCCESS,areaService.findProvinces());
    }


    @RequestMapping(value = "getProvince",method = {RequestMethod.POST})
    public ApiResult getProvince(Long id){
        return  new ApiResult(ResultMsg.SUCCESS,areaService.getProvince(id));
    }

    @RequestMapping(value = "findCitysByProviceId",method = {RequestMethod.POST})
    public ApiResult findCitysByProviceId(Long id){
        return  new ApiResult(ResultMsg.SUCCESS,areaService.findCitysByProviceId(id));

    }
    @RequestMapping(value = "findCityById",method = {RequestMethod.POST})
    public ApiResult findCityById(Long id){
        return  new ApiResult(ResultMsg.SUCCESS,areaService.findCitysByProviceId(id));
    };

    @RequestMapping(value = "findAreaByCityId",method = {RequestMethod.POST})
    public ApiResult findAreaByCityId(Long id){
        return  new ApiResult(ResultMsg.SUCCESS,areaService.findAreaByCityId(id));
    }

    @RequestMapping(value = "findAreaById",method = {RequestMethod.POST})
    public ApiResult findAreaById(Long id){
        return  new ApiResult(ResultMsg.SUCCESS,areaService.findAreaById(id));
    }




}
