package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.GoodTeaches;
import com.runer.cibao.domain.Read;
import com.runer.cibao.domain.repository.GoodTeachesRespository;
import com.runer.cibao.domain.repository.ReadRespository;

/**
 * @Author sww
 * @Description:cibao==
 * @Date 2019/11/27
 **/
public interface ReadService extends BaseService<Read, ReadRespository> {

    ApiResult addRead(Long id,
                             String imgUrl,
                             String title, String author, String content);

}
