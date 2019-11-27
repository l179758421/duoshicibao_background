package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.Read;
import com.runer.cibao.service.ReadService;
import com.runer.cibao.util.NormalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author sww
 * @Date 2019/11/27
 **/
@RestController
@RequestMapping(value = "read")
public class ReadController {

    @Autowired
    ReadService beanService ;



    @RequestMapping(value = "data_list")
    public LayPageResult<Read> datlist (Integer page , Integer limit){
        Page<Read> pageResult = beanService.findByPage(page, limit);
         return NormalUtil.createLayPageReuslt(pageResult) ;
    }


    @RequestMapping(value = "addOrUpdateData")
    public ApiResult addOrUpdateData(Long id,
                                     String imgUrl,
                                     String title, String author, String content){
        return    beanService.addRead(id,imgUrl ,title, author ,content);
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
