package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.PunchCardDao;
import com.runer.cibao.domain.AppUser;
import com.runer.cibao.domain.PunchCard;
import com.runer.cibao.domain.repository.PunshCardRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.AdminConfigService;
import com.runer.cibao.service.AppUserService;
import com.runer.cibao.service.IntegralService;
import com.runer.cibao.service.PunshCardService;
import com.runer.cibao.util.NormalUtil;
import com.runer.cibao.util.machine.DateMachine;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.runer.cibao.exception.ResultMsg.SUCCESS;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/29
 **/
@Service
public class PunchCardServiceImpl extends BaseServiceImp<PunchCard, PunshCardRepository> implements PunshCardService {

    @Autowired
    PunchCardDao punchCardDao;
    @Autowired
    DateMachine dateMachine;
    @Autowired
    AppUserService appUserService;
    @Autowired
    IntegralService integralService;
    @Autowired
    AdminConfigService adminConfigService;
    @Autowired
    PunshCardService punshCardService;
    @Override
    public ApiResult punchCard(Long userId) {

        ApiResult userResult = appUserService.findByIdWithApiResult(userId);

        if (userResult.getMsgCode() != SUCCESS.getMsgCode()) {
            return userResult;
        }
        AppUser appUser = (AppUser) userResult.getData();

        Date date = new Date();

        Date bDate = dateMachine.getOneDayTimes(date)[0];
        Date eDate = dateMachine.getOneDayTimes(date)[1];

        ApiResult cardsResult=  findCards(userId,bDate,eDate) ;
        List<PunchCard> cards = (List<PunchCard>) cardsResult.getData();

        if (!ListUtils.isEmpty(cards)) {
            if (cards.size() >= 1) {
                return new ApiResult(ResultMsg.TODAY_PUNCHED, null);
            }
        }
        //记录打卡记录
        PunchCard punchCard = new PunchCard();
        punchCard.setAppUser(appUser);
        punchCard.setPunchDate(date);
        punchCard = r.saveAndFlush(punchCard);
        ApiResult daysResult = findBeforeSevenDays(userId);
        List<Date> days =(List<Date>) daysResult.getData();

        if (days.size()<4){
            integralService.addDaySignIntegral(userId,false);
        }else{
            //添加单词的打卡;
            integralService.addDaySignIntegral(userId,false);
            //添加满天的打卡；
            integralService.addDaySignIntegral(userId,true);
        }
        int punchAllDays = (int) getPunchCardDays(userId).getData();
        appUser.setPunchCardsDays(punchAllDays);
        try {
            appUserService.saveOrUpdate(appUser) ;
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        return new ApiResult(SUCCESS, punchCard);
    }



    @Override
    public ApiResult findCards(Long userId, Date bDate, Date eDate) {
        return new ApiResult(SUCCESS, punchCardDao.findCard(userId, bDate, eDate));
    }


    /**
     * 获得是否是五天连续的打卡，周一开始；
     * @param userId
     * @return
     */
    @Override
    public ApiResult findBeforeSevenDays(Long userId) {
        List<Date> days=new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        int day =cal.get(Calendar.DAY_OF_WEEK);
        if (day==Calendar.FRIDAY||day==Calendar.SUNDAY||day==Calendar.SATURDAY){
            //获得最近四天的情况；
            for (int i = 0; i < 4; i++) {
                Date currnetDay = DateUtils.addDays(new Date(), -(i+1));
                Date[] times = dateMachine.getOneDayTimes(currnetDay);
                ApiResult cards = findCards(userId, times[0], times[1]);
                if (!ListUtils.isEmpty((List<?>) cards.getData())){
                    days.add(currnetDay);
                } else {
                    break;
                }
            }
        }else{

        }
        return new ApiResult(SUCCESS, days);
    }
    @Override
    public ApiResult getPunchCardDays(Long userId) {
        List<PunchCard> list = punchCardDao.findCardByUserId(userId);
        return  new ApiResult(SUCCESS,list.size());
    }

    @Override
    public ApiResult findSevenDays(Long userId) {
        List<Integer> results =new ArrayList<>() ;
        Date[] sevenDates = NormalUtil.getSevenDate();

        for (Date sevenDate : sevenDates) {
            Date bDate = dateMachine.getOneDayTimes(sevenDate)[0];
            Date eDate = dateMachine.getOneDayTimes(sevenDate)[1];
            ApiResult cardsResult=  findCards(userId,bDate,eDate) ;
            List<PunchCard> cards = (List<PunchCard>) cardsResult.getData();
            if (cards==null||cards.isEmpty()){
                results.add(0);
            }else{


               results.add(1) ;
            }
        }
        return new ApiResult(SUCCESS,results);
    }


}
