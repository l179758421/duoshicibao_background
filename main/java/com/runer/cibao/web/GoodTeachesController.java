package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.GoodTeaches;
import com.runer.cibao.service.GoodTeachesService;
import com.runer.cibao.util.NormalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author sw
 * @Date 2019/9/26
 **/
@RestController
@RequestMapping(value = "goodTeaches")
public class GoodTeachesController {

    @Autowired
    GoodTeachesService beanService ;





    @RequestMapping(value = "data_list")
    public LayPageResult<GoodTeaches> datlist (Integer page , Integer limit){
        Page<GoodTeaches> pageResult = beanService.findByPage(page, limit);
         return NormalUtil.createLayPageReuslt(pageResult) ;
    }


    @RequestMapping(value = "addOrUpdateData")
    public ApiResult addOrUpdateData(Long id, String title , String url ,String imgUrl, String teacherName,
                                     String teacherDes, Long  wathNum, Double score, Integer isBought){
        if (wathNum==null){
            wathNum = 0L ;
        }
        if (score==null){
            score =0.0 ;
        }
        if (isBought==null){
            isBought =0 ;
        }
        return    beanService.addGoodTeaches(id,title ,url, imgUrl ,teacherName,teacherDes,wathNum,score,isBought);
    }

    @RequestMapping("deleteByIds")
    public ApiResult deleteByIds(String ids){
        return  NormalUtil.deleteByIds(beanService,ids) ;
    }


    @RequestMapping("deleteById")
    public ApiResult deleteById(Long id){
        return  NormalUtil.deleteById(beanService,id);
    }

}
