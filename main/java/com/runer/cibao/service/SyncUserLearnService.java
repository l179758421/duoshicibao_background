package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2019/1/2
 **/
public interface SyncUserLearnService {


    ApiResult syncHomeInfo(Long userId);

    ApiResult syncLoginInfo(Long userId);

}
