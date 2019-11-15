package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.PageApiResult;
import com.runer.cibao.domain.GoodTeaches;
import com.runer.cibao.service.GoodTeachesService;
import com.runer.cibao.service.RedeemCodeService;
import com.runer.cibao.util.NormalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/10
 **/
@RestController
@RequestMapping(value = "api/goodTeaches")
public class GoodTeachesApi {

    @Autowired
    GoodTeachesService goodTeachesService ;

    @Autowired
    RedeemCodeService redeemCodeService ;

    @RequestMapping(value = "data_list" ,method = RequestMethod.POST)
    public PageApiResult<GoodTeaches> getGoodTeaches(Integer page , Integer limit ){
        Page<GoodTeaches> pages = goodTeachesService.findByPage(page, limit);
        return NormalUtil.createPageResult(pages) ;
    }
    @RequestMapping(value = "count" ,method = RequestMethod.POST)
    public ApiResult count( ){
        return redeemCodeService.countCodes() ;
    }


}
