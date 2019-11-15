package com.runer.cibao.service.impl;

import com.alibaba.fastjson.JSON;
import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.PersonalTestForBookDao;
import com.runer.cibao.domain.*;
import com.runer.cibao.domain.repository.PersonalTestForBookRepository;
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

import static com.runer.cibao.Config.IS_PRE_TEST;
import static com.runer.cibao.exception.ResultMsg.SUCCESS;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/25
 **/
@Service
public class PersonalTestForBookServiceImpl extends BaseServiceImp<PersonalTestForBook,PersonalTestForBookRepository> implements PersonalTestForBookService {

    @Autowired
    PersonalTestForBookDao personalTestForBookDao ;

    @Autowired
    LearnBookService learnBookService ;

    @Autowired
    AppUserService appUserService ;

    @Autowired
    BookWordService bookWordService ;

    @Autowired
    PersonalLearnBookService personalLearnBookService ;

    @Autowired
    PersonalTestForBookService personalTestForBookService;

    @Autowired
    PersonalTestApiService personalTestApiService;

    @Autowired
    TestRecordService testRecordService ;

    @Autowired
    IntegralService integralService ;


    @Autowired
    NewBookWordService newBookWordService;



    @Override
    public Page<PersonalTestForBook> findBooksTest(Long userId, Long bookId, Long personalLearnBookId, Integer isPre , Date bTime , Date eTime , Integer page , Integer limit) {
        return personalTestForBookDao.findBooksTest(userId,bookId,personalLearnBookId,isPre,bTime,eTime,PageableUtil.basicPage(page,limit));
    }

    @Override
    public ApiResult addOrUpdatePersonalBookTest(Long id, Long personalLearnBookId,
                                                 Long totalTestTime, Long totalWordsNum,
                                                 Long testTime, Long testWordsNum, Long rightWordsNum,
                                                 Integer isPreLearnTest, String ids, String errorIds,
                                                 Date testDate, Integer score , Integer isPassed, String testRecords ) {


        if (isPreLearnTest==null){
            isPreLearnTest = Config.NOT_PRE_TEST ;
        }

        ApiResult personalLearnBookResult =personalLearnBookService.findByIdWithApiResult(personalLearnBookId);
        if (personalLearnBookResult.isFailed()){
            return  personalLearnBookResult ;
        }


        //无update//
        ApiResult testOneDayResult = findOneDayTests(null, null,personalLearnBookId, testDate);
        List<PersonalTestForBook>  tests = (List<PersonalTestForBook>) testOneDayResult.getData();

        if (!ListUtils.isEmpty(tests)){
            return  new ApiResult(ResultMsg.TODAY_TESTED,null) ;
        }
        PersonalTestForBook personalTestForBook =new PersonalTestForBook();
        personalTestForBook.setPersonalLearnBook((PersonalLearnBook) personalLearnBookResult.getData());
        personalTestForBook.setTotalTestTime(totalTestTime);
        personalTestForBook.setTotalWordsNum(totalWordsNum);
        personalTestForBook.setTestTime(testTime);
        personalTestForBook.setTestWordsNum(testWordsNum);
        personalTestForBook.setRightWordsNum(rightWordsNum);
        personalTestForBook.setIsPreLearnTest(isPreLearnTest);
        personalTestForBook.setTestDate(new Date());
        personalTestForBook.setIds(ids);
        personalTestForBook.setErrorIds(errorIds);
        //设置分数
        personalTestForBook.setScore(score);


        PersonalLearnBook personalLearnBook = personalTestForBook.getPersonalLearnBook();
        personalLearnBook.setScore(score);


        if (!StringUtils.isEmpty(errorIds)) {
            if (personalLearnBook.getPersonalLearnBooks() != null && personalLearnBook.getPersonalLearnBooks().getAppUser() != null) {
                //不是学前测试的情况下，将错误的单词上传
                if (isPreLearnTest!=IS_PRE_TEST) {
                    AppUser appUser = personalLearnBook.getPersonalLearnBooks().getAppUser();
                    newBookWordService.addNewBookWordsBacthNoLimit(appUser.getId(), errorIds);
                }
            }
        }



        //不是学前的测试的时候，更新当前的进度和分数；
        if(isPreLearnTest== Config.NOT_CURRENT) {
                //是否通关；
                if (null!=isPassed&& Config.PASSED==isPassed){
                    personalLearnBook.setIsPassed(isPassed);
                }
                try {
                    personalLearnBookService.update(personalLearnBook);
                } catch (SmartCommunityException e) {
                    e.printStackTrace();
                }

        }else{
                if (null!=isPassed&& Config.PASSED==isPassed){
                    personalLearnBook.setIsPreTested(IS_PRE_TEST);
                    personalLearnBook.setPreScore(score);
                }else{
                    personalLearnBook.setIsPreTested(Config.NOT_PRE_TEST);
                }
                try {
                    personalLearnBookService.update(personalLearnBook);
                } catch (SmartCommunityException e) {
                    e.printStackTrace();
                }
        }

        //更新测试记录；
        personalTestForBook= r.saveAndFlush(personalTestForBook);
        //进行积分的更新；
        integralService.addTestIntegral(personalLearnBook.getPersonalLearnBooks().getAppUser().getId()) ;
        //保存测试的记录；
        if (!StringUtils.isEmpty(testRecords)){
            List<TestRecords> testDatas  =  JSON.parseArray(testRecords, TestRecords.class);
            for (TestRecords testData : testDatas) {
                testData.setBookTestId(personalTestForBook.getId());
            }
            try {
                testRecordService.saveOrUpdate(testDatas);
            } catch (SmartCommunityException e) {
                e.printStackTrace();
            }
        }
        return new ApiResult(ResultMsg.SUCCESS,personalTestForBook);
    }


    @Autowired
    DateMachine dateMachine ;

    @Override
    public ApiResult findOneDayTests(Long userId, Long bookId, Long personalLearnBookId , Date date) {
        Date[] times = dateMachine.getOneDayTimes(date);
        Page<PersonalTestForBook> tests = findBooksTest(userId, bookId,personalLearnBookId, Config.NOT_PRE_TEST, times[0], times[1], 1, 10);;
        return  new ApiResult(ResultMsg.SUCCESS,tests.getContent());

    }

    @Override
    public ApiResult findTestDetail(Long testId) {
        try {
            PersonalTestForBook personalTestForBook =findById(testId);
            return  new ApiResult(ResultMsg.SUCCESS,personalTestForBook);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null) ;
        }
    }

    @Override
    public ApiResult findWordsListByTestId(Long testId , Integer isWrongOnly) {

        try {
            PersonalTestForBook testForBook =findById(testId);
            String allIds =testForBook.getIds();
            String errorIds =testForBook.getErrorIds() ;
            List<BookWord> words =null;
            if (isWrongOnly==null){
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
    public ApiResult findTestLatest(Long personalBookId) {
        List<PersonalTestForBook> datas = personalTestForBookDao.findBooksTestOrderByDate(null, personalBookId);

        PersonalTestForBook personalTestForBook = null ;

        if (!ListUtils.isEmpty(datas)){
            personalTestForBook =  datas.get(0) ;
        }else {
            return  new ApiResult(ResultMsg.NOT_FOUND,null) ;
        }
       return  new ApiResult(ResultMsg.SUCCESS,personalTestForBook);
    }

    @Override
    public ApiResult findTestPreLearn(Long personalBookId) {


        Page<PersonalTestForBook> pres = findBooksTest(null, null, personalBookId, IS_PRE_TEST, null, null, 1, Integer.MAX_VALUE);

        if (ListUtils.isEmpty(pres.getContent())){
            return  new ApiResult(ResultMsg.NOT_FOUND,null) ;
        }

        return new ApiResult(ResultMsg.SUCCESS,pres.getContent().get(0));
    }

    @Override
    public ApiResult findTestByUserIdAndBookId(Long userId, Long bookId) {
       List<PersonalTestForBook>  list =personalTestForBookDao.findByUserIdAndBookIdOrderByDate(userId,bookId);
        return new ApiResult(ResultMsg.SUCCESS,list);
    }

    @Override
    public ApiResult export2Excel(List<PersonalTestForBook> list, String fileName, HttpServletResponse response) {

        List<TestBookScoreExcel> exportData=scoresToExcels(list);
        Workbook work = ExcelUtil.exportExcel(exportData, null, fileName, TestBookScoreExcel.class, "test.xls", response);

        return  new ApiResult(SUCCESS,work.getSheetName(0)) ;
    }



    @Override
    public List<TestBookScoreExcel> scoresToExcels(List<PersonalTestForBook> scores) {
        List<TestBookScoreExcel> testBookScoreExcels =new ArrayList<>() ;

        scores.forEach(score -> {
            testBookScoreExcels.add(scoreToExcel(score));
        });
        return testBookScoreExcels;
    }

    @Override
    public TestBookScoreExcel scoreToExcel(PersonalTestForBook testForBook) {
        TestBookScoreExcel testBookScoreExcel =new TestBookScoreExcel() ;

        testBookScoreExcel.setSchoolName(testForBook.getPersonalLearnBook().getPersonalLearnBooks().getAppUser().getSchoolName());
        testBookScoreExcel.setClassName(testForBook.getPersonalLearnBook().getPersonalLearnBooks().getAppUser().getClassInSchool().getName());
        testBookScoreExcel.setUserName(testForBook.getPersonalLearnBook().getPersonalLearnBooks().getAppUser().getName());
        testBookScoreExcel.setUserId(testForBook.getPersonalLearnBook().getPersonalLearnBooks().getAppUser().getId());
        if(testForBook.getPersonalLearnBook()!=null&&testForBook.getPersonalLearnBook().getLearnBook()!=null) {
            testBookScoreExcel.setBookName(testForBook.getPersonalLearnBook().getLearnBook().getBookName());
        }

        Long appUserId = testForBook.getPersonalLearnBook().getPersonalLearnBooks().getAppUser().getId();
        Long bookId=null;
        if(testForBook.getPersonalLearnBook()!=null&&testForBook.getPersonalLearnBook().getLearnBook()!=null) {
             bookId = testForBook.getPersonalLearnBook().getLearnBook().getId();
        }

        List<PersonalTestForBook> personalTestForBooks0 = personalTestForBookDao.findBooksTestOrderByDate2(appUserId,bookId,0);
        if(personalTestForBooks0 != null && personalTestForBooks0.size() != 0 ){
            String preTime = Arith.dayHousMinS(personalTestForBooks0.get(0).getTestTime());
            testBookScoreExcel.setPreLearnTime(preTime);
        }else{
            testBookScoreExcel.setPreLearnTime("0秒");
        }

        List<PersonalTestForBook> personalTestForBooks1 = personalTestForBookDao.findBooksTestOrderByDate2(appUserId,bookId,1);
        if(personalTestForBooks1 != null && personalTestForBooks1.size() != 0){
            String aftTime = Arith.dayHousMinS(personalTestForBooks1.get((personalTestForBooks1.size()-1)).getTestTime());
            testBookScoreExcel.setAftLearnTime(aftTime);
        }else{
            testBookScoreExcel.setAftLearnTime("0秒");
        }


        if(testForBook.getPreScore() == null){
            testBookScoreExcel.setPreLearnScore(0);
        }else{
            testBookScoreExcel.setPreLearnScore(testForBook.getPreScore());
        }
        if(testForBook.getScore() == null){
            testBookScoreExcel.setAftLearnScore(0);
        }else{
            testBookScoreExcel.setAftLearnScore(testForBook.getScore());
        }
        return  testBookScoreExcel ;
    }
}
