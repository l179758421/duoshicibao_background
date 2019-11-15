package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.Medals;
import com.runer.cibao.domain.repository.MedalsRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.AppUserService;
import com.runer.cibao.service.MedalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/11/15
 **/
@Service
public class MedalsServiceImpl extends BaseServiceImp<Medals,MedalsRepository> implements MedalsService {

    @Autowired
    AppUserService appUserService ;






    @Override
    public List<Medals> findByAppUsers(Long appUserId) {
        return  r.findAllByAppUserId(appUserId);
    }

    @Override
    public ApiResult findAppusersMedals(Long appUserId) {


        if (appUserId==null){
            return  new ApiResult("用户id为空");
        }

        if (appUserService.findByIdWithApiResult(appUserId).isFailed()){
            return  new ApiResult("用户不存在") ;
        }

        List<Medals> medals = findByAppUsers(appUserId) ;
        Map<String,Integer> results= new HashMap<>() ;
        results.put("国金",0) ;
        results.put("国银",0) ;
        results.put("国铜",0) ;

        results.put("省金",0) ;
        results.put("省银",0) ;
        results.put("省铜",0) ;

        results.put("校金",0) ;
        results.put("校银",0) ;
        results.put("校铜",0) ;

        results.put("班金",0) ;
        results.put("班银",0) ;
        results.put("班铜",0) ;

        for (Medals medal : medals) {
            String des =medal.getDes();
            if (results.containsKey(des)){
                results.put(des,results.get(des)+1);
            }
        }

        return new ApiResult(ResultMsg.SUCCESS,results);
    }



    @Override
    public ApiResult findAppusersMedalsYW(Long appUserId) {

        ApiResult apiResult = findAppusersMedals(appUserId);
        Map<String,Integer> map = (Map<String, Integer>) apiResult.getData();
        Map<String,Integer> results= new HashMap<>() ;
        results.put("GJ",map.get("国金")) ;
        results.put("GY",map.get("国银")) ;
        results.put("GT",map.get("国铜")) ;

        results.put("SJ",map.get("省金")) ;
        results.put("SY",map.get("省银")) ;
        results.put("ST",map.get("省铜")) ;

        results.put("XJ",map.get("校金")) ;
        results.put("XY",map.get("校银")) ;
        results.put("XT",map.get("校铜")) ;

        results.put("BJ",map.get("班金")) ;
        results.put("BY",map.get("班银")) ;
        results.put("BT",map.get("班铜")) ;

        return new ApiResult(ResultMsg.SUCCESS,results);
    }
}
