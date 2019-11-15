package com.runer.cibao.service.impl;

import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.ReviewTestDao;
import com.runer.cibao.domain.PersonalLearnUnit;
import com.runer.cibao.domain.ReviewTest;
import com.runer.cibao.domain.UnitTestBean;
import com.runer.cibao.domain.repository.ReviewTestRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.NewBookWordService;
import com.runer.cibao.service.PersonalLearnUnitService;
import com.runer.cibao.service.ReviewTestService;
import com.runer.cibao.util.NormalUtil;
import com.runer.cibao.util.machine.DateMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReviewTestServiceImpl extends BaseServiceImp<ReviewTest, ReviewTestRepository> implements ReviewTestService {

    @Autowired
    PersonalLearnUnitService personalLearnUnitService;

    @Autowired
    ReviewTestDao reviewTestDao;


    @Autowired
    DateMachine dateMachine;

    @Autowired
    NewBookWordService newBookWordService ;


    @Override
    public ApiResult addReviewTest(String state ,Long userId, Long unitId,Long testUseTime , Long totalTestTime, String rightIds, String errorIds) {
        if(unitId==null){
           return new ApiResult("当前学习单元不存在");
        }
        ReviewTest reviewTest=new ReviewTest();
        reviewTest.setUserId(userId);
        reviewTest.setUnitId(unitId);
        reviewTest.setRightIds(rightIds);
        reviewTest.setErrorIds(errorIds);
        reviewTest.setTestUseTime(testUseTime);
        reviewTest.setTestTime(totalTestTime);
        reviewTest.setTestDate(new Date());
        r.saveAndFlush(reviewTest);

        //leftwords 将测试失败的单词加入生词库；
        if (!StringUtils.isEmpty(errorIds)) {
            newBookWordService.addNewBookWordsBacthNoLimit(userId, errorIds);
        }

        if (!StringUtils.isEmpty(state)){
            PersonalLearnUnit unit = (PersonalLearnUnit) personalLearnUnitService.findOneByUserIdAndUnitId(unitId,userId,null).getData();
            if (unit!=null){
                unit.setReviewTestState(Integer.valueOf(state));
                unit.setLastedReviewTestDate(new Date());
                try {
                    personalLearnUnitService.saveOrUpdate(unit);
                } catch (SmartCommunityException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ApiResult(ResultMsg.SUCCESS,reviewTest);
    }

    @Override
    public ApiResult findReviewTest(Long userId, Long unitId) {
        List<ReviewTest> reviewTests = reviewTestDao.findByUserIdAndUnitId(userId,unitId);
        if(ListUtils.isEmpty(reviewTests)){
            return new ApiResult("测试不存在");
        }
        return new ApiResult(ResultMsg.SUCCESS,reviewTests.get(0));
    }

    @Override
    public ApiResult findReviewTestList(Long userId, Long unitId) {
        List<ReviewTest> reviewTests = reviewTestDao.findByUserIdAndUnitId(userId,unitId);
        if(ListUtils.isEmpty(reviewTests)){
            return new ApiResult("测试不存在");
        }
        return new ApiResult(ResultMsg.SUCCESS,reviewTests);
    }


    @Override
    public ApiResult findReviewTestData(long userId, Long bookId) {
        Page<PersonalLearnUnit> units = personalLearnUnitService.findUnits(userId,bookId,null,null,null,null,null ,1,Integer.MAX_VALUE);
        List<UnitTestBean> unitList = new ArrayList<>();
           List<PersonalLearnUnit> list= units.getContent();
            for (PersonalLearnUnit personalLearnUnit : list) {
                List<ReviewTest> reviewTests=  reviewTestDao.findByUserIdAndUnitId(userId,personalLearnUnit.getBookUnit().getId());
                UnitTestBean unitTestBean=new UnitTestBean();
                unitTestBean.setCount(reviewTests.size());
                unitTestBean.setUnitId(personalLearnUnit.getBookUnit().getId());
                unitTestBean.setUnitName(personalLearnUnit.getBookUnit().getName());
                unitTestBean.setName(personalLearnUnit.getBookUnit().getName());
                unitTestBean.setIsfinished(personalLearnUnit.getIsFinished());
                if(personalLearnUnit.getIsCurrentLearnedUnit()== Config.NOT_CURRENT){
                    unitTestBean.setCurrentLearn(false);
                }else{
                    unitTestBean.setCurrentLearn(true);
                }
                if(!ListUtils.isEmpty(reviewTests)){
                    unitTestBean.setTestTime(reviewTests.get(0).getTestTime());
                    unitTestBean.setErrorIds(reviewTests.get(0).getErrorIds());
                    unitTestBean.setRightIds(reviewTests.get(0).getRightIds());
                    unitTestBean.setTotalTime(reviewTests.get(0).getTestTime());
                }
                if (!NormalUtil.unitShouldReviewTest(personalLearnUnit,dateMachine)){
                     unitTestBean.setState("-1");
                }else{
                     unitTestBean.setState(""+personalLearnUnit.getState());
                }
                unitList.add(unitTestBean);
            }
            return new ApiResult(ResultMsg.SUCCESS,unitList);
    }

    @Override
    public long countReviewTestCount(Long userId, Long unitId) {
        return reviewTestDao.countReviewTestNum(userId,unitId);
    }

}
