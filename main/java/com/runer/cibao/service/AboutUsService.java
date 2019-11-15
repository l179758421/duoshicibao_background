package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.AboutUs;
import com.runer.cibao.domain.repository.AboutUsRepository;

public interface AboutUsService extends BaseService<AboutUs, AboutUsRepository> {

    /**
     * 编辑关于我们
     * @param content
     * @return
     */
    ApiResult editAboutUs(String content);

    ApiResult forceGetAboutUs();

}
