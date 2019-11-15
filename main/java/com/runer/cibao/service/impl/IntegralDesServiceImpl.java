package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.IntegralDes;
import com.runer.cibao.domain.repository.IntegralDesRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.IntegralDesService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IntegralDesServiceImpl extends BaseServiceImp<IntegralDes,IntegralDesRepository> implements IntegralDesService {
    @Override
    public ApiResult findIntegralSourceAndUse() {
        Map<String,Object> map =new HashMap<>();
        List<String> sourceList = new ArrayList<String>();
        List<String> useList = new ArrayList<String>();
        List<IntegralDes> IntegralDesList=findAll();
        IntegralDesList.forEach(integralDes -> {
            if(integralDes.getIntegralSource()!=null){
                sourceList.add(integralDes.getIntegralSource());
            }

            if(integralDes.getIntegralUse()!=null ){
                useList.add(integralDes.getIntegralUse());
            }

        });
        map.put("source",sourceList);
        map.put("use",useList);
        return new ApiResult(ResultMsg.SUCCESS,map);
    }

    @Override
    public ApiResult saveOrUpdate(Long id, String integralSource, String integralUse) {

        IntegralDes integralDes=new IntegralDes();
        if(id != null){
            try {
                integralDes=findById(id);
            } catch (SmartCommunityException e) {
                e.printStackTrace();
                return  new ApiResult(e.getResultMsg(),null) ;
            }
        }
        if(integralUse !="" && integralUse != null){
            integralDes.setIntegralUse(integralUse);
        }
        if(integralSource != "" && integralSource != null){
            integralDes.setIntegralSource(integralSource);
        }
        integralDes=r.save(integralDes);

        return new ApiResult(ResultMsg.SUCCESS,integralDes);
    }
}
