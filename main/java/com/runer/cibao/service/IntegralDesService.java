package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.IntegralDes;
import com.runer.cibao.domain.repository.IntegralDesRepository;

public interface IntegralDesService extends BaseService<IntegralDes,IntegralDesRepository> {
    ApiResult findIntegralSourceAndUse();

    /**
     * 添加更新积分说明
     * @param id
     * @param integralSource
     * @param integralUse
     * @return
     */
    ApiResult saveOrUpdate(Long id, String integralSource, String integralUse);
}
