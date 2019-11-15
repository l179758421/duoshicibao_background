package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.service.AboutUsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "关于我们",description = "关于我们")
@RequestMapping(value = "api/aboutUs")
public class AboutUsApi {
    @Autowired
    AboutUsService aboutUsService;

//    @ApiOperation(value = "关于我们",notes = "关于我们")
//    @RequestMapping(value = "getAboutUsUrl",method = RequestMethod.POST)
//    public ApiResult getAboutUsUrl(){
////        HashMap<String,String> result =new HashMap<>() ;
////        result.put("url","/about/aboutUsShow");
////        return new ApiResult(ResultMsg.SUCCESS,result);
////    }


    @ApiOperation(value ="关于我们内容",notes = "关于我们内容")
    @RequestMapping(value = "getContent",method = RequestMethod.POST)
    public ApiResult getContent(){
        return aboutUsService.forceGetAboutUs();
    }


}
