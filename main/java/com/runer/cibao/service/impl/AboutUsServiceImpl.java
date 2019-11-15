package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.AboutUs;
import com.runer.cibao.domain.repository.AboutUsRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.AboutUsService;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

@Service
public class AboutUsServiceImpl extends BaseServiceImp<AboutUs,AboutUsRepository> implements AboutUsService {
    @Override
    public ApiResult editAboutUs(String content) {
        AboutUs aboutUs = (AboutUs) forceGetAboutUs().getData();
        if(content != null){
            aboutUs.setContent(content);
        }
        aboutUs = r.saveAndFlush(aboutUs);
        return new ApiResult(ResultMsg.SUCCESS,aboutUs);
    }

    @Override
    public ApiResult forceGetAboutUs() {
        AboutUs aboutUs = null ;
        if (ListUtils.isEmpty(findAll())){
            aboutUs =new AboutUs() ;
            aboutUs = r.save(aboutUs);
        }else{
            aboutUs =findAll().get(0) ;
        }

        return new ApiResult(ResultMsg.SUCCESS,aboutUs);
    }
}
