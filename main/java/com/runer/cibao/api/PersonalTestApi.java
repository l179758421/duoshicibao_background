package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.PersonalTestForBook;
import com.runer.cibao.domain.PersonalTestForUnit;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.*;
import com.runer.cibao.util.machine.DateMachine;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.ListUtils;

import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/22
 **/

@RestController
@RequestMapping(value = "api/personalBoolTestApi")
@Api(description = "个人测试相关")
public class PersonalTestApi {


    @Autowired
    PersonalTestForBookService personalTestForBookService ;

    @Autowired
    PersonalTestForUnitService personalTestForUnitService ;

    @Autowired
    PersonalTestApiService personalTestApiService ;

    @Autowired
    DateMachine dateMachine;

    @Autowired
    ReviewTestService reviewTestService;

    @Autowired
    TestRuleService testRuleService;

    @Autowired
    TestRecordService testRecordService ;


    @ApiOperation(value = "addBookTest",notes = "保存用户的的课本测试")
    @RequestMapping(value = "addBookTest",method = RequestMethod.POST)
    public ApiResult addBookTest(Long personalLearnBooId , Long totalTestTime,
                                 Long totalWordsNum, Long testTime, Long testWordsNum,
                                 Long rightWordsNum,
                                 Integer isPreLearnTest, String ids,
                                 String errorIds,Integer score ,String testDate ,Integer isPassed  ,String testRecords ){
      Date date =null;
      try{
          date=dateMachine.parseDateDefault(testDate) ;
      }catch (Exception e){
         e.printStackTrace();
      }
      return   personalTestForBookService.addOrUpdatePersonalBookTest(null,personalLearnBooId,
              totalTestTime,totalWordsNum,testTime,
                testWordsNum,rightWordsNum,isPreLearnTest,ids,errorIds,date,score ,isPassed ,testRecords);
    }

    @ApiOperation(value = "addUnitTest",notes = "保存单元测试的结果--isPassed 2 notPassed  1")
    @RequestMapping(value = "addUnitTest",method = RequestMethod.POST)
    public ApiResult addUnitTest(Long personalUnitID, Long totalTestTime,
                                 Long totalWordsNum, Long testTime, Long testWordsNum,
                                 Long rightWordsNum, String ids,
                                 String errorIds, String  testDate,Integer score ,Integer isPassed ,String testRecords){
        Date dateResult =dateMachine.parseDateDefault(testDate);
        return  personalTestForUnitService.addOrUpdatePersonalUnitTest(null,personalUnitID,totalTestTime,
                totalWordsNum,testTime,
                testWordsNum,rightWordsNum,ids,errorIds,dateResult,score,isPassed,testRecords);
    }


    @ApiOperation(value = "获得单元测试的题目详情(单词列表)",notes = "获得单元测试的题目详情(单词列表)")
    @RequestMapping(value = "getUnitTestWords",method = RequestMethod.POST)
    public ApiResult getUnitTestWords(Long testId ,Integer isWrongOnly){
       return  personalTestForUnitService.findWordsListByTestId(testId,isWrongOnly);
    }

    @ApiOperation(value = "获得课本测试的题目详情(单词列表)",notes = "获得课本测试的题目详情(单词列表)")
    @RequestMapping(value = "getBookTestWords",method = RequestMethod.POST)
    public ApiResult getBookTestWords(Long testId ,Integer isWrongOnly){
        return  personalTestForBookService.findWordsListByTestId(testId,isWrongOnly);
    }





    @ApiOperation(value = "获得个人的课本测试",notes = "获得个人的课本测试")
    @RequestMapping(value = "getPersonalBookTests",method = RequestMethod.POST)
    public ApiResult getPersonalBookTests(Long userId){
        return personalTestApiService.findPersonalLearnBookTest(userId);
    }



    @ApiOperation(value = "获得个人单元的测试",notes = "获得个人单元的测试")
    @RequestMapping(value = "getPersonalUnitTests",method = RequestMethod.POST)
    public ApiResult getPersonalUnitTests(Long  personalLearnBookId){
        return  personalTestApiService.findPersonalLearnBookUnitTest(personalLearnBookId);
    }

    @ApiOperation(value = "获得个人课本的测试记录--日期",notes = "获得个人课本的测试记录-日期")
    @RequestMapping(value = "getPersonalbookTestRecords",method = RequestMethod.POST)
    public ApiResult getPersonalbookTestRecords(Long personalLeanrBookId ){
        return personalTestApiService.findPeronalLearnBookTestRecords(personalLeanrBookId) ;
    }

    @ApiOperation(value = "获得个人单元的测试记录-日期",notes = "获得个人单元的测试记录-日期")
    @RequestMapping(value = "getPersonalUnitTestsRecords",method = RequestMethod.POST)
    public ApiResult getPe(Long userId,Long personalUnitId ){
        return personalTestApiService.findPersonalLearnUnitsTestRecords(userId,personalUnitId) ;
    }


    @ApiOperation(value = "获得个人课本的测试记录详情---每个测试题的详情",notes = "获得个人课本的测试记录详情--每个测试题的详情")
    @RequestMapping(value = "findTestDetail",method = RequestMethod.POST)
    public ApiResult findTestDetail(Long bookTestId ,Long unitTestId ,Integer type ){
        return testRecordService.getTestRecord(unitTestId,bookTestId,type);
    }



    @ApiOperation(value = "今天是否测试过了课本",notes = "今天是否测试过了课本--0未测试   -1已测试")
    @RequestMapping(value = "isTodayTestBook",method = RequestMethod.POST)
    public ApiResult isTodayTestBook(Long personalBookId  ){
        ApiResult testOneDayResult = personalTestForBookService.findOneDayTests(null, null, personalBookId, new Date());
        List<PersonalTestForBook> tests = (List<PersonalTestForBook>) testOneDayResult.getData();



        if (ListUtils.isEmpty(tests)){
            return  new ApiResult(ResultMsg.SUCCESS,0);
        }else{
            return  new ApiResult(ResultMsg.SUCCESS,-1) ;
        }
    }

    @ApiOperation(value = "今天是否测试过了单元",notes = "今天是否测试过了单元--0未测试   -1已测试")
    @RequestMapping(value = "isTodayTestUnit",method = RequestMethod.POST)
    public ApiResult isTodayTestUnit(Long userId, Long unitId  ){
        ApiResult testOneDayResult = personalTestForUnitService.findOneDayTests(userId, unitId, null, new Date());
        List<PersonalTestForUnit> tests = (List<PersonalTestForUnit>) testOneDayResult.getData();
        if (ListUtils.isEmpty(tests)){
            return  new ApiResult(ResultMsg.SUCCESS,0);
        }else{
            return  new ApiResult(ResultMsg.SUCCESS,-1) ;
        }
    }


    @ApiOperation(value = "复习模块--个人单元测试情况",notes = "复习模块，个人单元测试情况")
    @RequestMapping(value = "getUnitTestDetails",method = RequestMethod.POST)
    public ApiResult getUnitTestDetails(long userId,Long bookId){
        return reviewTestService.findReviewTestData(userId,bookId);
    }

    @ApiOperation(value = "复习模块--上传测试结果",notes = "上传测试结果")
    @RequestMapping(value = "addReviewTest",method = RequestMethod.POST)
    public ApiResult addReviewTest(String state , Long userId, Long unitId,Long testUseTime , Long totalTestTime,String rightIds,String errorIds){
        return  reviewTestService.addReviewTest(state,userId,unitId,testUseTime ,totalTestTime,rightIds,errorIds);
    }

    @ApiOperation(value = "复习模块--查询测试结果",notes = "复习模块--查询测试结果")
    @RequestMapping(value = "findReviewTest",method = RequestMethod.POST)
    public ApiResult findReviewTest(Long userId, Long unitId){
        return  reviewTestService.findReviewTest(userId,unitId); }

    @ApiOperation(value = "获取测试规则说明",notes = "获取测试规则说明")
    @RequestMapping(value = "findTestRules",method = RequestMethod.POST)
    public ApiResult findTestRules(){
        return testRuleService.findRules();
    }


}
