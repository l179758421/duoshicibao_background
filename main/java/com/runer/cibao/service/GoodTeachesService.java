package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.GoodTeaches;
import com.runer.cibao.domain.repository.GoodTeachesRespository;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/9
 **/
public interface GoodTeachesService extends BaseService<GoodTeaches, GoodTeachesRespository> {

    ApiResult addGoodTeaches(Long id,
                             String titlte,
                             String url,
                             String imgUrl, String teacherName, String teacherDes,
                             long wathNum,
                             double score,
                             int isBought);

}
