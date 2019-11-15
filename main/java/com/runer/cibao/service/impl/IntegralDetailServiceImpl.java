package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.IntegralDetailDao;
import com.runer.cibao.domain.Integral;
import com.runer.cibao.domain.IntegralDetail;
import com.runer.cibao.domain.repository.IntegralDetailRepository;
import com.runer.cibao.service.IntegralDetailService;
import com.runer.cibao.service.IntegralService;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class IntegralDetailServiceImpl extends BaseServiceImp<IntegralDetail, IntegralDetailRepository> implements IntegralDetailService {
    @Autowired
    IntegralService integralService;
    @Autowired
    IntegralDetailDao integralDetailDao;

    @Override
    public IntegralDetail addIntegralDetail(IntegralDetail integralDetail) {
        return r.save(integralDetail);
    }
    @Override
    public ApiResult findByUserId(Long userId) {
       ApiResult integralResult =  integralService.findUserIntegral(userId);
       if(integralResult.isFailed()){
           return integralResult;
       }
        Integral integral = (Integral)integralResult.getData();
        return  r.findByIntegral_Id(integral.getId());
    }
    @Override
    public List<IntegralDetail> findIntegralDetail(int type, Long userId, Date startDate, Date endDate) {
        return integralDetailDao.findIntegralDetail(type,userId,startDate,endDate);
    }
    @Override
    public Page<IntegralDetail> findIntegralDetailPage(Integer page, Integer limit, Long integralId) {
        return  integralDetailDao.findIntegralDetail(integralId,PageableUtil.basicPage(page,limit));
    }
}
