package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.AdminConfig;
import com.runer.cibao.domain.AppUser;
import com.runer.cibao.domain.Integral;
import com.runer.cibao.domain.IntegralDetail;
import com.runer.cibao.domain.repository.IntegralRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import com.runer.cibao.util.machine.DateMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class IntegralServiceImpl extends BaseServiceImp<Integral, IntegralRepository> implements IntegralService {


    @Autowired
    IntegralDetailService integralDetailService;
    @Autowired
    AdminConfigService adminConfigService;
    @Autowired
    AppUserService appUserService;

    @Autowired
    LevelDesService levelDesService ;

    @Override
    public ApiResult findUserIntegral(Long appUserId) {
        Integral integral = r.findIntegralByAppUser_Id(appUserId);
        if (integral == null) {
            Integral userIntegral = new Integral();
            userIntegral.setTotal(0l);
            userIntegral.setSign(0l);
            userIntegral.setRecharge(0l);
            userIntegral.setShare(0l);
            ApiResult userResult = appUserService.findByIdWithApiResult(appUserId);
            if (userResult.isFailed()) {
                return new ApiResult("用户不存在");
            }
            AppUser appUser = (AppUser) userResult.getData();
            userIntegral.setAppUser(appUser);
            userIntegral.setOffset(0l);
            userIntegral.setStudy(0l);
            userIntegral.setCreateTime(new Date());
            r.save(userIntegral);
            return new ApiResult(ResultMsg.SUCCESS, userIntegral);
        }
        return new ApiResult(ResultMsg.SUCCESS, integral);
    }


    /**
     * 打卡获得积分；
     * @param appUserId
     * @param isFiveFull
     * @return
     */

    @Override
    public ApiResult addDaySignIntegral(Long appUserId ,boolean isFiveFull) {
        Integral integral = r.findIntegralByAppUser_Id(appUserId);
        //修改用户积分状态

        AdminConfig config = findConfig();

        int thisItnegral = isFiveFull==true?config.getCardScroeForFullDay():config.getAvrageCardScrore() ;
        Long signIntegral = integral.getSign() + thisItnegral;
        Long totalIntegral = integral.getTotal() + thisItnegral;

        integral.setSign(signIntegral);
        integral.setTotal(totalIntegral);

        r.saveAndFlush(integral);

        //根据积分修改级别
        updateAppUserLevel(appUserId,totalIntegral);

        //记录积分明细
        IntegralDetail integralDetail = new IntegralDetail();
        integralDetail.setCreateTime(new Date());
        if (isFiveFull){
            integralDetail.setName("连续打卡奖励积分");
            integralDetail.setNumber(Long.valueOf(findConfig().getCardScroeForFullDay()));
        }else{
            integralDetail.setName("单次打卡奖励积分");
            integralDetail.setNumber(Long.valueOf(findConfig().getAvrageCardScrore()));
        }
        integralDetail.setType(0);

        integralDetail.setIntegral(integral);
        try {
            integralDetailService.save(integralDetail);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        return new ApiResult(ResultMsg.SUCCESS, integral);
    }


    //分享获得积分；
    @Override
    public ApiResult addShareIntegral(Long appUserId) {
        //修改用户积分状态
        Integral integral = r.findIntegralByAppUser_Id(appUserId);

        AdminConfig adminConfig =findConfig() ;
        long toAdd =adminConfig.getShareScore() ;
        long oneDayIntegrals = findOneDayIntegrals(appUserId, 1, new Date());;
        if (adminConfig.getShareScoreUp()<oneDayIntegrals+toAdd){
            toAdd =adminConfig.getShareScoreUp()-oneDayIntegrals ;
        }
        if (toAdd==0){
            return  new ApiResult(ResultMsg.SUCCESS,integral) ;
        }


        Long shareIntegral = integral.getShare() + toAdd;
        Long totalIntegral = integral.getTotal() + toAdd;
        integral.setShare(shareIntegral);
        integral.setTotal(totalIntegral);
        r.saveAndFlush(integral);
        //根据积分修改级别
        updateAppUserLevel(appUserId,totalIntegral);
        //记录积分明细
        IntegralDetail integralDetail = new IntegralDetail();
        integralDetail.setCreateTime(new Date());
        integralDetail.setName("分享奖励积分");
        integralDetail.setType(1);
        integralDetail.setNumber(Long.valueOf(findConfig().getShareScore()));
        integralDetail.setIntegral(integral);
        try {
            integralDetailService.save(integralDetail);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        return new ApiResult(ResultMsg.SUCCESS, integral);

    }



    //充值获得积分；
    @Override
    public ApiResult addRechargeIntegral(Long appUserId) {
        //修改用户积分状态

        Integral integral = r.findIntegralByAppUser_Id(appUserId);

        ApiResult integralResult  = findUserIntegral(appUserId);
        if (integralResult.isFailed()){
            return  integralResult ;
        }
        Long rechargeIntegral = integral.getRecharge() + findConfig().getRechargeScore();
        Long totalIntegral = integral.getTotal() + findConfig().getRechargeScore();
        integral.setRecharge(rechargeIntegral);
        integral.setTotal(totalIntegral);
        r.saveAndFlush(integral);
        //根据积分修改级别
        updateAppUserLevel(appUserId,totalIntegral);
        //记录积分明细
        IntegralDetail integralDetail = new IntegralDetail();
        integralDetail.setCreateTime(new Date());
        integralDetail.setName("充值奖励积分");
        integralDetail.setType(2);
        integralDetail.setNumber(Long.valueOf(findConfig().getRechargeScore()));
        integralDetail.setIntegral(integral);
        try {
            integralDetailService.save(integralDetail);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        return new ApiResult(ResultMsg.SUCCESS, integral);
    }


    /**
     * 学习时间获得积分；
     * @param appUserId
     * @param studyTime
     * @return
     */
    @Override
    public ApiResult addStudyIntegral(Long appUserId, Long studyTime) {
        //修改用户积分状态
        Integral integral = r.findIntegralByAppUser_Id(appUserId);
        ApiResult integralResult  = findUserIntegral(appUserId);
        if (integralResult.isFailed()){
            return  integralResult ;
        }
        //获得学习应得的积分；
        long  toAdd = judgeIntegralByStudyTime(studyTime);

        AdminConfig adminConfig =findConfig() ;
        long oneDayIntegrals = findOneDayIntegrals(appUserId, 4, new Date());;
        if (adminConfig.getStudyScoreUp()<oneDayIntegrals+toAdd){
            toAdd =adminConfig.getStudyScoreUp()-oneDayIntegrals ;
        }
        if (toAdd==0){
            return  new ApiResult(ResultMsg.SUCCESS,integral) ;
        }


        Long studyIntegral = integral.getStudy()==null?0:integral.getStudy()+toAdd;
        Long totalIntegral = integral.getTotal()==null?0:integral.getTotal()+ toAdd;
        integral.setStudy(studyIntegral);
        integral.setTotal(totalIntegral);
        r.saveAndFlush(integral);
        //根据积分修改级别
        updateAppUserLevel(appUserId,totalIntegral);
      //记录积分明细
        IntegralDetail integralDetail = new IntegralDetail();
        integralDetail.setCreateTime(new Date());
        integralDetail.setName("学习时间奖励积分");
        integralDetail.setType(4);
        integralDetail.setNumber(toAdd);
        integralDetail.setIntegral(integral);
        try {
            integralDetailService.save(integralDetail);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        return new ApiResult(ResultMsg.SUCCESS, integral);
    }


    /**
     * 添加测试的积分；
     * @param appUserId
     * @return
     */
    @Override
    public ApiResult addTestIntegral(Long appUserId) {

        //修改用户积分状态
        Integral integral = r.findIntegralByAppUser_Id(appUserId);
        ApiResult integralResult  = findUserIntegral(appUserId);
        if (integralResult.isFailed()){
            return  integralResult ;
        }
        AdminConfig adminConfig =findConfig() ;
        long toAdd = adminConfig.getPassTestScore() ;
        long oneDayIntegrals = findOneDayIntegrals(appUserId, 6, new Date());;
        //达到上限的情况；添加上限情况；
        if (adminConfig.getPassTestScoreUp()<(oneDayIntegrals+toAdd)){
            toAdd =adminConfig.getPassTestScoreUp()-oneDayIntegrals ;
        }
        if (toAdd==0){
            return  new ApiResult(ResultMsg.SUCCESS,toAdd);
        }
        long testInterals = integral.getTestpassed() +toAdd ;
        long nowTotal =integral.getTotal()==null?0:integral.getTotal()+toAdd ;
        integral.setTotal(nowTotal);
        integral.setTestpassed(testInterals);
        r.saveAndFlush(integral) ;
        //根据积分修改级别
        updateAppUserLevel(appUserId,nowTotal);
        IntegralDetail integralDetail = new IntegralDetail();
        integralDetail.setCreateTime(new Date());
        integralDetail.setName("闯关成功获得积分");
        integralDetail.setType(6);
        integralDetail.setNumber(toAdd);
        integralDetail.setIntegral(integral);
        try {
            integralDetailService.save(integralDetail);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        return new ApiResult(ResultMsg.SUCCESS,integral);
    }

    @Override
    public ApiResult addStudyWordIntegral(Long appUserId, Long wordNum) {
        //修改用户积分状态
        Integral integral = r.findIntegralByAppUser_Id(appUserId);
        ApiResult integralResult  = findUserIntegral(appUserId);
        if (integralResult.isFailed()){
            return  integralResult ;
        }

        AdminConfig adminConfig =findConfig() ;
        long toAdd = adminConfig.getEveryWordScore() * wordNum;
        long oneDayIntegrals = findOneDayIntegrals(appUserId, 5, new Date());;
        //达到上限的情况；添加上限情况；
        if (adminConfig.getEveryWordScoreUp()<(oneDayIntegrals+toAdd)){
            toAdd =adminConfig.getEveryWordScoreUp() -oneDayIntegrals;
        }
        if (toAdd==0){
            return  new ApiResult(ResultMsg.SUCCESS,toAdd);
        }



        long nowWordCount = integral.getStudyWord() +toAdd ;
        long nowTotal =integral.getTotal()==null?0:integral.getTotal()+toAdd ;

        integral.setTotal(nowTotal);
        integral.setStudyWord(nowWordCount);
        r.saveAndFlush(integral) ;
        //根据积分修改级别
        updateAppUserLevel(appUserId,nowTotal);
        IntegralDetail integralDetail = new IntegralDetail();
        integralDetail.setCreateTime(new Date());
        integralDetail.setName("学习单词奖励积分");
        integralDetail.setType(5);
        integralDetail.setNumber(toAdd);
        integralDetail.setIntegral(integral);
        try {
            integralDetailService.save(integralDetail);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        return new ApiResult(ResultMsg.SUCCESS, integral);
    }

    /**
     * 根据学习时间判断所得积分
     * @param studyTime 学习时间
     * @return
     */
    private Long judgeIntegralByStudyTime(Long studyTime){
        Long integral = studyTime * findConfig().getStudyScore();
        return integral;
    }

    @Override
    public ApiResult updateTotalIntegral(Long appUserId, Long number) {
        //修改用户积分状态
        Integral integral = r.findIntegralByAppUser_Id(appUserId);
        Long totalIntegral = integral.getTotal() - number;
        Long offSetIntegral = integral.getOffset();
        offSetIntegral += number;
        integral.setTotal(totalIntegral);
        integral.setOffset(offSetIntegral);
        r.saveAndFlush(integral);
        //根据积分修改级别
        updateAppUserLevel(appUserId,totalIntegral);

        //记录积分明细
        IntegralDetail integralDetail = new IntegralDetail();
        integralDetail.setCreateTime(new Date());
        integralDetail.setName("购物抵消积分");
        integralDetail.setType(3);
        integralDetail.setNumber(number);
        integralDetail.setIntegral(integral);
        try {
            integralDetailService.save(integralDetail);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        return new ApiResult(ResultMsg.SUCCESS, integral);
    }

    @Override
    public Page<IntegralDetail> getPersonalIntegralDetail(Long appUserId, Integer page , Integer limit) {
          Integral integral =  r.findIntegralByAppUser_Id(appUserId);
           return integralDetailService.findIntegralDetailPage(page,limit,integral.getId());
    }


    /**
     * 获得当天的积分详情；
     * @param appUserId
     * @param type
     * @param date
     * @return
     */
    @Override
    public long findOneDayIntegrals(Long appUserId, int type, Date date) {
        Date[] oneDates = new DateMachine().getOneDayTimes(date);
        List<IntegralDetail> details = integralDetailService.findIntegralDetail(type,appUserId,oneDates[0],oneDates[1]) ;
        long totalIntegrals =0 ;
        for (IntegralDetail detail : details) {
            if (detail.getNumber()!=null) {
                totalIntegrals += detail.getNumber();
            }
        }
        return totalIntegrals;
    }

    private AdminConfig findConfig(){
        ApiResult configResult = adminConfigService.forceGetAdminConfig();
        AdminConfig adminConfig = (AdminConfig) configResult.getData();
        return adminConfig;
    }
    /**
     * 修改用户个人情况的总积分
     * @param appUserId
     * @param userIntegral
     */
    private ApiResult updateAppUserLevel(Long appUserId,Long userIntegral){
        try {
            ApiResult userResult = appUserService.findByIdWithApiResult(appUserId);
            if(userResult.isFailed()){
                return new ApiResult("用户不存在");
            }
            AppUser appUser = (AppUser) userResult.getData();
            //根据积分变更等级
            String level = getLevel(userIntegral);
            appUser.setLevel(level);
            appUserService.saveOrUpdate(appUser);
            return new ApiResult("更新级别成功");
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return new ApiResult("更新级别不成功");
        }
    }


    /**
     * 修改级别
     * @param integral
     * @return
     */
    private String getLevel(Long integral){
       String level="";
       if(integral > 1 && integral <= 1000){
          level= judgeLevel(integral,100,0,0);
       }else if(integral > 1000 && integral <= 5000){
           level=judgeLevel(integral,400,1000,10);
       }else if(integral > 5000 && integral <= 10000){
           level=judgeLevel(integral,500,5000,20);
       }else if(integral > 10000 && integral <= 20000){
           level=judgeLevel(integral,1000,10000,30);
       }else if(integral > 20000 && integral <= 50000){
           level=judgeLevel(integral,3000,20000,40);
       }else if(integral > 50000){
           level="LV51";
       }else {
           level="LV0";
       }
        return level;
    }


    /**
     * 判断积分； integral -->当前的积分；
     * @param integral
     * @param divisor 每多少积分升级
     * @param number1 积分的基数 ；
     * @param number2 等级的基数；
     * @return 级别名称；
     */
    private String judgeLevel(Long integral,int divisor,int number1,int number2){
        Long a=integral-number1;
        Long b;
        if(a%divisor !=0){
           b=a/divisor+1;
        }else{
            b=a/divisor;
        }
        return "LV"+(number2+b);
    }


}
