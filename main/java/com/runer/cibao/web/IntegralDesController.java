package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.IntegralDes;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.IntegralDesService;
import com.runer.cibao.util.NormalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="integral")
public class IntegralDesController {
    @Autowired
    IntegralDesService integralDesService;

    @RequestMapping(value = "index")
    public String integralIndex(){

        return "integral/integral_manage";
    }

    @RequestMapping(value = "data_list")
    @ResponseBody
    public LayPageResult<IntegralDes> getIntegralList(Integer page, Integer limit){
        Page<IntegralDes> integralDesPage  = integralDesService.findByPage(page,limit);
        LayPageResult<IntegralDes> layPageResult = new LayPageResult(integralDesPage.getContent());
        return layPageResult;
    }

    @RequestMapping(value = "addIntegralDes")
    public String addIntegralDes(Long id, ModelMap map){
        if (id!=null){
            try {
                map.put("integralDes",integralDesService.findById(id));
            } catch (SmartCommunityException e) {
                e.printStackTrace();
            }
        }
        return "integral/add_integralDes";
    }

    @RequestMapping(value = "addOrUpdateIntegralDes")
    @ResponseBody
    public ApiResult addOrUpdateIntegralDes(Long id,String integralSource,String integralUse){
        return  integralDesService.saveOrUpdate(id,integralSource,integralUse);
    }

    @RequestMapping("deleteByIds")
    @ResponseBody
    public ApiResult deleteByIds(String ids){
        return  NormalUtil.deleteByIds(integralDesService,ids) ;
    }


    @RequestMapping("deleteById")
    @ResponseBody
    public ApiResult deleteById(Long id){
        return  NormalUtil.deleteById(integralDesService,id);
    }


}
