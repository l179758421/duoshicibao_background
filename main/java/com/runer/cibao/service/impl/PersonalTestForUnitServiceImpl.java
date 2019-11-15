package com.runer.cibao.service.impl;

import com.alibaba.fastjson.JSON;
import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.PersonalTestForUnitDao;
import com.runer.cibao.domain.*;
import com.runer.cibao.domain.repository.PersonalTestForUnitRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import com.runer.cibao.util.Arith;
import com.runer.cibao.util.ExcelUtil;
import com.runer.cibao.util.machine.DateMachine;
import com.runer.cibao.util.page.PageableUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.runer.cibao.exception.ResultMsg.SUCCESS;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/25
 **/
@Service
public class PersonalTestForUnitServiceImpl extends BaseServiceImp<PersonalTestForUnit, PersonalTestForUnitRepository> implements PersonalTestForUnitService {


    @Autowired
    PersonalTestForUnitDao personalTestForUnitDao ;

    @Autowired
    BookWordService bookWordService ;

    @Autowired
    DateMachine dateMachine ;

    @Autowired
    PersonalLearnUnitService personalLearnUnitService;

    @Autowired
    TestRecordService testRecordService;


    @Autowired
    IntegralService integralService ;


    @Autowired
    NewBookWordService newBookWordService ;


    @Override
    public Page<PersonalTestForUnit> findUnitTests(Long userId, Long unitId, Long personalLearnUnitId , Date bTime, Date eTime, Integer isPre , Integer page, Integer limit) {
        return personalTestForUnitDao.findPersionUnitsTest(unitId,userId,personalLearnUnitId,bTime,eTime,isPre ,PageableUtil.basicPage(page,limit));
    }

    @Override
    public ApiResult addOrUpdatePersonalUnitTest(Long id, Long personalLearnUnitId,
                                                 Long totalTestTime, Long totalWordsNum, Long testTime,
                                                 Long testWordsNum, Long rightWordsNum, String ids, String errorIds,
                                                 Date testDate,Integer score,Integer isPassed,String testRecords) {

        ApiResult personalLearnUnitResult =personalLearnUnitService.findByIdWithApiResult(personalLearnUnitId);
        if (personalLearnUnitResult.isFailed()){
            return  new ApiResult("当前学习单元不存在") ;
        }
        ApiResult testOneDayResult = findOneDayTests(null, null,personalLearnUnitId, testDate);
        List<PersonalTestForUnit> tests = (List<PersonalTestForUnit>) testOneDayResult.getData();


        if (!ListUtils.isEmpty(tests)){
            return  new ApiResult(ResultMsg.TODAY_TESTED,null) ;
        }

       PersonalTestForUnit personalTestForUnit =new PersonalTestForUnit();
       personalTestForUnit.setPersonalLearnUnit((PersonalLearnUnit) personalLearnUnitResult.getData());
       personalTestForUnit.setTotalTestTime(totalTestTime);
       personalTestForUnit.setTotalWordsNum(totalWordsNum);
       personalTestForUnit.setTestTime(testTime);
       personalTestForUnit.setTestWordsNum(testWordsNum);
       personalTestForUnit.setRightWordsNum(rightWordsNum);
       personalTestForUnit.setTestDate(new Date());
       personalTestForUnit.setIds(ids);
       personalTestForUnit.setErrorIds(errorIds);
       personalTestForUnit.setScore(score);

       //更新个人学习的情况；
       PersonalLearnUnit personalLearnUnit = personalTestForUnit.getPersonalLearnUnit();
       personalLearnUnit.setScore(score);
       personalLearnUnit.setIsPassed(isPassed);
       try {
           personalLearnUnitService.update(personalLearnUnit);
       } catch (SmartCommunityException e) {
           e.printStackTrace();
       }

        if (!StringUtils.isEmpty(errorIds)) {
            if (personalLearnUnit.getPersonalLearnBook()!=null&&personalLearnUnit.getPersonalLearnBook().getPersonalLearnBooks() != null && personalLearnUnit.getPersonalLearnBook().getPersonalLearnBooks().getAppUser() != null) {
                AppUser appUser = personalLearnUnit.getPersonalLearnBook().getPersonalLearnBooks().getAppUser();
                newBookWordService.addNewBookWordsBacthNoLimit(appUser.getId(), errorIds);
            }
        }

        //设置通过；
        personalTestForUnit.setIsPassed(isPassed);
        personalTestForUnit= r.saveAndFlush(personalTestForUnit);

        //更新积分；
        integralService.addTestIntegral(personalLearnUnit.getPersonalLearnBook().getPersonalLearnBooks().getAppUser().getId());

        //保存测试的记录；
        if (!StringUtils.isEmpty(testRecords)){
          List<TestRecords> testDatas  =  JSON.parseArray(testRecords, TestRecords.class);
            for (TestRecords testData : testDatas) {
                  testData.setUnitTestId(personalTestForUnit.getId());
            }
            try {
                testRecordService.saveOrUpdate(testDatas);
            } catch (SmartCommunityException e) {
                e.printStackTrace();
            }
        }
        return new ApiResult(ResultMsg.SUCCESS,personalTestForUnit);

    }
    @Override
    public ApiResult findOneDayTests(Long userId, Long unitId,Long personalLearnUnit , Date date) {
        Date[] times = dateMachine.getOneDayTimes(date);
        Page<PersonalTestForUnit> tests = findUnitTests(userId, unitId, personalLearnUnit,times[0], times[1], null,1, 10);;
        return  new ApiResult(ResultMsg.SUCCESS,tests.getContent());

    }

    @Override
    public ApiResult findTestDetail(Long testId) {
        try {
            PersonalTestForUnit personalTestForUnit =findById(testId);
            return  new ApiResult(ResultMsg.SUCCESS,personalTestForUnit);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null) ;
        }
    }

    @Override
    public ApiResult findWordsListByTestId(Long testId, Integer isWrongOnly) {
        try {
            PersonalTestForUnit testForUnit =findById(testId);
            String allIds =testForUnit.getIds();
            String errorIds =testForUnit.getErrorIds() ;
            List<BookWord> words =null;
            if (isWrongOnly==0){
                isWrongOnly = Config.ALL_WRONG_RIGHT ;
            }
            List<BookWord> wordsWrong = bookWordService.markBookWrod(errorIds,errorIds);

            List<BookWord> wordsAll =bookWordService.markBookWrod(allIds,errorIds) ;

            //只获得错误的
            if (Config.IS_WRONG_ONLY==isWrongOnly){
                words =wordsWrong;
            }else{
                words =wordsAll ;
            }
            return  new ApiResult(ResultMsg.SUCCESS,words) ;
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null) ;
        }
    }

    @Override
    public ApiResult findLatestOne(Long personalUnitId) {
        List<PersonalTestForUnit> datas = personalTestForUnitDao.findBooksTestOrderByDate(null, personalUnitId);
        if (ListUtils.isEmpty(datas)){
            return  new ApiResult(ResultMsg.NOT_FOUND,null) ;
        }
        return new ApiResult(ResultMsg.SUCCESS,datas.get(0));
    }

    @Override
    public ApiResult findPreOne(Long presonalUnitId) {

        Page<PersonalTestForUnit> datas = findUnitTests(null, null, presonalUnitId, null, null, Config.IS_PRE_TEST, 1, 10);;
        if (ListUtils.isEmpty(datas.getContent())){
            return  new ApiResult(ResultMsg.NOT_FOUND,null) ;
        }
        return new ApiResult(ResultMsg.SUCCESS,datas.getContent().get(0));
    }

    @Override
    public ApiResult finaAllByLearnUnitId(Long learnUnitId) {
        Page<PersonalTestForUnit> datas = findUnitTests(null, null, learnUnitId, null, null, null, 1, Integer.MAX_VALUE);

        return new ApiResult(ResultMsg.SUCCESS,datas.getContent());
    }

    @Override
    public Page<PersonalTestForUnit> findUnitTestOrderByDate(Long userId, Long unitId, Integer page, Integer limit) {
      Page<PersonalTestForUnit> list=  personalTestForUnitDao.findUnitTestOrderByDate(userId,unitId,PageableUtil.basicPage(page,limit));
        return list;
    }

    @Override
    public List<TestUnitScoreExcel> scoresToExcels(List<PersonalTestForUnit> testForBooks) {
        List<TestUnitScoreExcel> testUnitScoreExcels =new ArrayList<>() ;

        testForBooks.forEach(score -> {
            testUnitScoreExcels.add(scoreToExcel(score));
        });
        return testUnitScoreExcels;
    }

    @Override
    public ApiResult export2PersonalExcel(List<PersonalTestForUnit> list, String fileName, HttpServletResponse response) {
        List<TestUnitScoreExcel> exportData=scoresToExcels(list);
        Workbook work = ExcelUtil.exportExcel(exportData, null, fileName, TestUnitScoreExcel.class, "test.xls", response);

        return  new ApiResult(SUCCESS,work.getSheetName(0)) ;
    }

    @Override
    public TestUnitScoreExcel scoreToExcel(PersonalTestForUnit testForBook) {
        TestUnitScoreExcel testUnitScoreExcel =new TestUnitScoreExcel() ;

        testUnitScoreExcel.setSchoolName(testForBook.getPersonalLearnUnit().getPersonalLearnBook().getPersonalLearnBooks().getAppUser().getSchoolName());
        testUnitScoreExcel.setClassName(testForBook.getPersonalLearnUnit().getPersonalLearnBook().getPersonalLearnBooks().getAppUser().getClassInSchool().getName());
        testUnitScoreExcel.setUserName(testForBook.getPersonalLearnUnit().getPersonalLearnBook().getPersonalLearnBooks().getAppUser().getName());
        testUnitScoreExcel.setUserId(testForBook.getPersonalLearnUnit().getPersonalLearnBook().getPersonalLearnBooks().getAppUser().getId());
        if(testForBook.getPersonalLearnUnit().getPersonalLearnBook().getLearnBook() != null){
            testUnitScoreExcel.setBookName(testForBook.getPersonalLearnUnit().getPersonalLearnBook().getLearnBook().getBookName());
        }else{
            testUnitScoreExcel.setBookName("");
        }

        testUnitScoreExcel.setUnitName(testForBook.getPersonalLearnUnit().getBookUnit().getName());
        if(testForBook.getScore() == null){
            testUnitScoreExcel.setTestTscore(0);
        }else{
            testUnitScoreExcel.setTestTscore(testForBook.getScore());
        }
        if(testForBook.getTestTime() == null || testForBook.getTestTime() == 0){
            testUnitScoreExcel.setTestTime("0秒");
        }else{
            testUnitScoreExcel.setTestTime(Arith.dayHousMinS(testForBook.getTestTime()));
        }
        return  testUnitScoreExcel ;
    }
}
