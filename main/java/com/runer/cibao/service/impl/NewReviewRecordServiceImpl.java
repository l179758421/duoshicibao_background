package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.NewReviewRecordDao;
import com.runer.cibao.domain.AppUser;
import com.runer.cibao.domain.PersonlLearnInfoBean;
import com.runer.cibao.domain.person_word.NewReviewRecord;
import com.runer.cibao.domain.repository.NewReviewRecordRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.AppUserService;
import com.runer.cibao.service.IntegralService;
import com.runer.cibao.service.NewReviceRecordService;
import com.runer.cibao.service.PersonalLearnInfoService;
import com.runer.cibao.util.machine.IdsMachine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/9/3
 **/
@Service
public class NewReviewRecordServiceImpl  extends BaseServiceImp<NewReviewRecord,NewReviewRecordRepository>  implements NewReviceRecordService {

    @Autowired
    NewReviewRecordDao reviewRecordDao ;
    @Autowired
    PersonalLearnInfoService personalLearnInfoService;
    @Autowired
    IdsMachine idsMachine ;

    @Autowired
    IntegralService integralService ;

    @Autowired
    AppUserService appUserService ;


    @Override
    public ApiResult uploadNewReviewRecord(Long appUserId, Date date, String newIds, String reviewsIds) {
        List<NewReviewRecord> reviews= (List<NewReviewRecord>) getReviewRecord(appUserId,date).getData();
        NewReviewRecord newReviewRecord =reviews.get(0);

        int lastNum =newReviewRecord.getNewNum()+newReviewRecord.getOldNum() ;

        int nowNewNum  =idsMachine.deparseIds(newIds).size() ;
        int nowReviewNum =idsMachine.deparseIds(reviewsIds).size() ;

        newReviewRecord.setNewWordsIds(newIds);
        newReviewRecord.setNewNum(nowNewNum);

        newReviewRecord.setReviewsIds(reviewsIds);
        newReviewRecord.setOldNum(nowReviewNum);

        r.saveAndFlush(newReviewRecord);

        int nowNum =nowNewNum+nowReviewNum ;

        //添加积分；
        if (nowNum-lastNum>0){
            integralService.addStudyWordIntegral(appUserId, (long) (nowNum-lastNum));
        }
        //更新个人的学习情况；---
        PersonlLearnInfoBean personlLearnInfoBean = (PersonlLearnInfoBean) personalLearnInfoService.generateOneLearnInfo(appUserId,date).getData();
       personlLearnInfoBean.setWordsNumNew(nowNewNum);
       personlLearnInfoBean.setWordsNumReview(nowReviewNum);
       personlLearnInfoBean.setAllWords(nowNewNum+nowReviewNum);
        try {
            personalLearnInfoService.save(personlLearnInfoBean);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }

        //更新用户
        AppUser appUser = (AppUser) appUserService.findByIdWithApiResult(appUserId).getData();
        //第一次进行初始化；
        long  totalPersonlWordNum =0;
        List<PersonlLearnInfoBean> personlLearnInfoBeans  = (List<PersonlLearnInfoBean>) personalLearnInfoService.findOneLearn(appUserId).getData();
        if (!ListUtils.isEmpty(personlLearnInfoBeans)){
            for (PersonlLearnInfoBean personlLearnInfoBeanItem : personlLearnInfoBeans) {
                totalPersonlWordNum+=personlLearnInfoBeanItem.getWordsNumNew() ;
            }
            appUser.setWordNum(totalPersonlWordNum);
        }
        try {
            appUserService.saveOrUpdate(appUser) ;
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }


        return  getReviewRecordNum(appUserId,date);
    }

    @Override
    public ApiResult getReviewRecord(Long appUserId, Date date) {
        List<NewReviewRecord> reviews = reviewRecordDao.findNewReviewRecords(appUserId, date);
        if (date!=null&&ListUtils.isEmpty(reviews)){
            NewReviewRecord record =new NewReviewRecord() ;
            record.setUserId(appUserId);
            record.setUploadTime(date);
            r.saveAndFlush(record);
            reviews.add(record) ;
        }
        return new ApiResult(ResultMsg.SUCCESS,reviews);
    }

    @Override
    public ApiResult getReviewRecordNum(Long appUserId, Date date) {
        List<NewReviewRecord> reviews = (List<NewReviewRecord>) getReviewRecord(appUserId,date).getData();
        NewReviewRecord newReviewRecord =new NewReviewRecord();
        newReviewRecord.setUserId(appUserId);
        if (date==null){
            StringBuffer newsIds = new StringBuffer( );
            StringBuffer reviewIds =new StringBuffer() ;
            for (NewReviewRecord review : reviews) {
                if (StringUtils.isNotEmpty(review.getNewWordsIds()))
                newsIds.append(review.getNewWordsIds()) ;
                if (StringUtils.isNotEmpty(review.getReviewsIds()))
                reviewIds.append(review.getReviewsIds());
            }
            newReviewRecord.setReviewsIds(reviewIds.toString());
            newReviewRecord.setNewWordsIds(newsIds.toString());
        }else{
            newReviewRecord =reviews.get(0) ;
        }
        if (!StringUtils.isEmpty(newReviewRecord.getNewWordsIds())){
            newReviewRecord.setNewNum(idsMachine.deparseIds(newReviewRecord.getNewWordsIds()).size());
        }
        if (!StringUtils.isEmpty(newReviewRecord.getReviewsIds())){
            newReviewRecord.setOldNum(idsMachine.deparseIds(newReviewRecord.getReviewsIds()).size());
        }
        return new ApiResult(ResultMsg.SUCCESS,newReviewRecord);
    }
    @Override
    public List<NewReviewRecord> findAllUsersReviewsRecords(Long appUserId, Date startTime, Date endTime) {
        List<NewReviewRecord> reviews = reviewRecordDao.findNewReviewRecordsWithDates(appUserId, startTime, endTime);
        return reviews;
    }

    @Override
    public ApiResult getReviewCounts(Long appUserId, Date startTime, Date endTime) {

        Map<String,Integer> reviewsCountResult =new HashMap<>();
        List<NewReviewRecord> allUserReviews ;

        if (startTime==null&&endTime==null){
             allUserReviews = findAllUsersReviewsRecords(appUserId, null, null);
        }else {
             allUserReviews =findAllUsersReviewsRecords(appUserId,startTime,endTime) ;
        }
        /*
         */

        int newCount  =0 ;
        int oldCount  =0 ;
        if (ListUtils.isEmpty(allUserReviews)){
             for (NewReviewRecord allUserReview : allUserReviews) {
                 newCount+= allUserReview.getNewNum() ;
                 oldCount+=allUserReview.getOldNum() ;
            }
        }
        reviewsCountResult.put("newCount",newCount) ;
        reviewsCountResult.put("oldCount",oldCount) ;

        return new ApiResult(ResultMsg.SUCCESS,reviewsCountResult);
    }
}
