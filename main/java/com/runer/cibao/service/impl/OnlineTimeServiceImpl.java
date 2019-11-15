package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.OnlineTimeDao;
import com.runer.cibao.domain.AppUser;
import com.runer.cibao.domain.OnlineTime;
import com.runer.cibao.domain.repository.OnlineTimeRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.AppUserService;
import com.runer.cibao.service.OnlineTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import java.util.Date;
import java.util.List;

@Service
public class OnlineTimeServiceImpl extends BaseServiceImp<OnlineTime, OnlineTimeRepository> implements OnlineTimeService {
    @Autowired
    AppUserService appUserService;
    @Autowired
    OnlineTimeDao onlineTimeDao;

    @Override
    public ApiResult getOnlineTime(Long userId) {

        ApiResult userReuslt =appUserService.findByIdWithApiResult(userId) ;
        if (userReuslt.isFailed()){
            return  new ApiResult("用户不存在");
        }
        Date date=new Date();
        List<OnlineTime> dates = onlineTimeDao.findOnlineByUserAndDate(userId,date);
        if (ListUtils.isEmpty(dates)){

            OnlineTime onlineTime=new OnlineTime();
            onlineTime.setTime(0l);
            onlineTime.setAppUser((AppUser) userReuslt.getData());
            onlineTime.setOnlineDate(date);
            r.saveAndFlush(onlineTime) ;

            return  new ApiResult(ResultMsg.SUCCESS,onlineTime) ;
        }else {
            OnlineTime onlineTime = dates.get(0);
            //每一百二十秒进行上传一下；
            Long time = onlineTime.getTime()+120;
            onlineTime.setTime(time);
            r.saveAndFlush(onlineTime);
            return  new ApiResult(ResultMsg.SUCCESS,onlineTime) ;
        }

    }

    @Override
    public List<OnlineTime> findByUserId(Long userId) {
       List<OnlineTime> list=r.findByAppUser_Id(userId);
        return  list;
    }

    @Override
    public List<OnlineTime> findByUserAndDate(Long userId, Date date) {
        List<OnlineTime> dates = onlineTimeDao.findOnlineByUserAndDate(userId,date);
        return dates;
    }


    @Override
    public List<OnlineTime> getByUserId(Long userId) {
        List<OnlineTime> list=onlineTimeDao.findOnlineByUser(userId);
        return  list;
    }
}
