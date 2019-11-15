package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.OneWord;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.OneWordService;
import com.runer.cibao.util.NormalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author sww
 * @Date 2019/9/26
 **/
@RestController
@RequestMapping(value = "oneWord")
public class OneWordController {

    @Autowired
    OneWordService beanService ;

    @RequestMapping(value = "data_list")
    public LayPageResult<OneWord> getDataList(String content,Integer page , Integer limit){

        Page<OneWord> pageResult = beanService.findOneWords(content, page, limit);
        return NormalUtil.createLayPageReuslt(pageResult) ;
    }

    @RequestMapping(value = "addOrUpdateData")
    public ApiResult addOrUpdateData(String content ,String name ,Long id  ,String translation){
        return     beanService.addOneWord(id,content,name ,translation);
    }

    @RequestMapping("deleteByIds")
    public ApiResult deleteByIds(String ids){
        return  NormalUtil.deleteByIds(beanService,ids) ;
    }

    @RequestMapping("deleteById")
    public ApiResult deleteById(Long id){
        return  NormalUtil.deleteById(beanService,id);
    }

    @RequestMapping("getById")
    public ApiResult getById(Long id){
        return  beanService.getById(id);
    }
}
