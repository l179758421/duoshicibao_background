package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.BeanForPersonalBookList;
import com.runer.cibao.domain.PersonalLearnUnit;
import com.runer.cibao.domain.person_word.UnitStateBean;
import com.runer.cibao.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2019/1/2
 **/
@Service
public class SyncUserLearnServiceImpl implements SyncUserLearnService {


    @Autowired
    PersonalBookApiService personalBookApiService ;

    @Autowired
    PersonalLearnUnitService unitService ;

    @Autowired
    WordLearnProgressService wordLearnProgressService ;

    @Autowired
    UserReviewService userReviewService ;

    @Autowired
    NewReviceRecordService newReviceRecordService ;



    @Override
    public ApiResult syncHomeInfo(Long userId) {
        ApiResult learnBookResult =   personalBookApiService.findCurrentLearnBook(userId) ;
        if (learnBookResult.isFailed()){
            return  ApiResult.ok() ;
        }
        Map<String,Object> results =new HashMap<>() ;
        BeanForPersonalBookList personalLearnBook = (BeanForPersonalBookList) learnBookResult.getData();
        results.put("book",personalLearnBook) ;
        long unitId = personalLearnBook.getUnitId() ;
        if (unitId!=0){
            ApiResult unistResult =  unitService.findOneByUserIdAndUnitId(unitId,userId,null);
            if (unistResult.isSuccess()){
                PersonalLearnUnit unit = (PersonalLearnUnit) unistResult.getData();
                UnitStateBean stateBean =new UnitStateBean();
                stateBean.setUnitId(unit.getBookUnit().getId());
                stateBean.setNews(unit.getNews());
                stateBean.setLeftIds(unit.getLeftIds());
                stateBean.setKnows(unit.getKnows());
                stateBean.setStrongs(unit.getStrongs());
                stateBean.setAllWords(unit.getAllWords());
                stateBean.setStage(unit.getStage());
                stateBean.setUserId(userId);
                results.put("unit",stateBean) ;
            }
        }
        return  new ApiResult(results,true);
    }


    @Override
    public ApiResult syncLoginInfo(Long userId) {

        Map<String,Object> datas  =new HashMap<>() ;

        ApiResult learnInfos =  wordLearnProgressService.getOnePersonAllLearnDetail(userId) ;

        Object infos = learnInfos.getData();
        //获得详情；
        datas.put("infos",infos);

        Object reviewsInfo = userReviewService.findUnitsReviews(userId, null, null).getData();
        datas.put("reviews",reviewsInfo) ;


        Object recordnum = newReviceRecordService.getReviewRecordNum(userId, new Date()).getData();

        datas.put("recordnum",recordnum) ;

        return ApiResult.ok(datas);
    }
}
