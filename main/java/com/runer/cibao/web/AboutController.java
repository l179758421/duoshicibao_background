package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.AboutUsService;
import io.jsonwebtoken.JwtBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "about")
public class AboutController {
    @Autowired
    AboutUsService aboutUsService;

    @RequestMapping(value = "editAboutUs")
    public ApiResult editAbout(String content){
        return  aboutUsService.editAboutUs(content);
    }

    @RequestMapping(value = "getShow")
    public ApiResult getAboutUs(){
        return new ApiResult(ResultMsg.SUCCESS,aboutUsService.forceGetAboutUs().getData());
    }

}
