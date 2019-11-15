package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.IntegralDetail;
import com.runer.cibao.domain.repository.IntegralDetailRepository;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface IntegralDetailService extends BaseService<IntegralDetail, IntegralDetailRepository> {
    /**
     * 记录积分明细
     * @param integralDetail
     * @return
     */
     IntegralDetail addIntegralDetail(IntegralDetail integralDetail);

    /**
     * 根据用户id查找用户积分明细
     * @param appUserId
     * @return
     */
    ApiResult findByUserId(Long appUserId);


    List<IntegralDetail> findIntegralDetail(int type, Long userId, Date startDate, Date endDate);



    Page<IntegralDetail> findIntegralDetailPage(Integer page, Integer limit, Long integralId);
}
