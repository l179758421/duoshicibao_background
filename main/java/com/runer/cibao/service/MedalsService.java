package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.Medals;
import com.runer.cibao.domain.repository.MedalsRepository;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao== 奖章相关
 * @Date 2018/11/15
 **/
public interface MedalsService extends BaseService<Medals,MedalsRepository>  {

    /**
     * 获得个人的奖牌；
     * @param appUserId
     * @return
     */
    List<Medals> findByAppUsers(Long appUserId) ;


    ApiResult  findAppusersMedals(Long appUserId) ;

    ApiResult  findAppusersMedalsYW(Long appUserId) ;

}
