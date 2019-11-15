package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.LearnTimeDao;
import com.runer.cibao.domain.LearnTime;
import com.runer.cibao.domain.PersonlLearnInfoBean;
import com.runer.cibao.domain.repository.LearnTimeRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.IntegralService;
import com.runer.cibao.service.LearnTimeService;
import com.runer.cibao.service.PersonalLearnInfoService;
import com.runer.cibao.util.machine.DateMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/11/3
 **/
@Service
public class LearnTimeServiceImpl extends BaseServiceImp<LearnTime, LearnTimeRepository> implements LearnTimeService {

    @Autowired
    DateMachine dateMachine ;

     @Autowired
     LearnTimeDao learnTimeDao ;

     @Autowired
     IntegralService integralService ;

     @Autowired
     PersonalLearnInfoService personalLearnInfoService ;


    @Override
    public ApiResult uploadLearnTime(Long lerarnTime, Long appUserId, Date date) {
        if (date==null){
            return  new ApiResult("日期不能为空");
        }
        Date[] oneDaysTime = dateMachine.getOneDayTimes(date);;
        List<LearnTime> times = learnTimeDao.findByDateUser(oneDaysTime[0], oneDaysTime[1], appUserId);
        LearnTime learn  =new LearnTime();
        if (!ListUtils.isEmpty(times)){
            learn =times.get(0) ;
        }
        //上一次的分钟；
        long lastedTime =learn.getTime()/1000/60/10;
        //这一次的分钟；
        long thisAdd =  lerarnTime/1000/60/10;
        long canAddIntergral = thisAdd-lastedTime ;


        //更新个人的学习情况；
        PersonlLearnInfoBean personalLearnInfo = (PersonlLearnInfoBean) personalLearnInfoService.generateOneLearnInfo(appUserId,date).getData();
        personalLearnInfo.setLearnTime(lerarnTime);
        try {
            personalLearnInfoService.save(personalLearnInfo);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }


        if (canAddIntergral>0){
            //添加学习的积分；
            integralService.addStudyIntegral(appUserId,canAddIntergral) ;
        }
        learn.setAppUserId(appUserId);
        learn.setDate(date);
        learn.setTime(lerarnTime);
        r.save(learn);
        return new ApiResult(ResultMsg.SUCCESS,learn);
    }

    /**
     * 请登陆的时候进行同步一下；
     * @param appUserId
     * @param date
     * @return
     */
    @Override
    public ApiResult getUploadLearnTime(Long appUserId, Date date) {
        Date[] oneDaysTime = dateMachine.getOneDayTimes(date);
        List<LearnTime> times = learnTimeDao.findByDateUser(oneDaysTime[0], oneDaysTime[1], appUserId);
        if (times!=null&&!times.isEmpty()){
            return new ApiResult(ResultMsg.SUCCESS,times);
        }else{
            LearnTime learnTime =new LearnTime();
            learnTime.setAppUserId(appUserId);
            learnTime.setDate(date);
            learnTime.setTime(0);
            r.save(learnTime) ;
            times.add(learnTime);
            return new ApiResult(ResultMsg.SUCCESS,times);
        }
    }

    @Override
    public List<LearnTime> getUploadLearnTime(Long appUserId) {
        return r.findAllByAppUserId(appUserId);
    }

    @Override
    public List<LearnTime> getLearnTime(Long appUserId) {
        return learnTimeDao.findByLearnTime(appUserId);
    }

    @Override
    public List<LearnTime> getLearnTimeDate(Date startdate, Date enddate, Long appUserId) {
        return learnTimeDao.findByDateUser(startdate, enddate, appUserId);
    }
}
