package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.BookUnitDao;
import com.runer.cibao.domain.*;
import com.runer.cibao.domain.repository.BookUnitRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import com.runer.cibao.util.Arith;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/15
 **/

@Service
public class BookUintServiceImpl extends BaseServiceImp<BookUnit,BookUnitRepository> implements BookUnitService {


    @Autowired
    BookUnitDao bookUnitDao;
    @Autowired
    LearnBookService learnBookService ;
    @Autowired
    BookUnitService bookUnitService ;
    @Autowired
    UserReviewService userReviewService;
    @Autowired
    PersonalTestForUnitService personalTestForUnitService;
    @Autowired
    ReviewTestService reviewTestService;


    @Override
    public List<BookUnit> findUnits(Long bookId,
                                    String bookversion, String bookStage, String bookName, Long unitId, String unitName, String bookversionLikeName, String booklikeName, String unitLikeName) {
        return bookUnitDao.findUnits(bookId,bookversion,bookStage,bookName,unitId,unitName,bookversionLikeName,booklikeName,unitLikeName);
    }

    @Override
    public ApiResult addOrUpdateUnit(Long id, String name, Long bookId) {
        BookUnit bookUnit =new BookUnit() ;
        if (id!=null){
            try {
                bookUnit =findById(id);
            } catch (SmartCommunityException e) {
                e.printStackTrace();
                return new ApiResult(e.getResultMsg(),null) ;
            }
        }

        if (bookId==null){
            return  new ApiResult(ResultMsg.BOOK_ID_IS_NULL,null) ;
        }
        if(bookId != null && name !=null && name !=""){
            List<BookUnit> lb = bookUnitService.findUnits(bookId,null,null,null,null,name,null,null,null);
            if(lb.size()!= 0){
                return  new  ApiResult("该单元已存在!");
            }else{
                try {
                    LearnBook learnBook = learnBookService.findById(bookId);

                    bookUnit.setId(id);
                    bookUnit.setLearnBook(learnBook);
                    bookUnit.setName(name);

                    bookUnit =r.saveAndFlush(bookUnit);

                    return new ApiResult(ResultMsg.SUCCESS,bookUnit);

                } catch (SmartCommunityException e) {
                    e.printStackTrace();
                    return  new ApiResult(e.getResultMsg(),null);
                }
            }
        }else{
            return  new  ApiResult("请确认填写内容!");
        }

    }

    @Override
    public Page<BookUnit> findByBookId(Long bookId, String unitName, Integer page, Integer limit) {
        return bookUnitDao.findByBookId(bookId,unitName,PageableUtil.basicPage(page,limit));
    }

    @Override
    public List<BookUnit> personalLearnBookUnitList(Long userId,Long bookId){
        List<BookUnit> list = new ArrayList<>();
        Page<BookUnit> page = findByBookId(bookId,null,1,Integer.MAX_VALUE);
        for (BookUnit unit:page.getContent()) {
            Page<PersonalTestForUnit> personalTestForUnitPage = personalTestForUnitService.findUnitTestOrderByDate(userId, unit.getId(), 1, 10);
            List<PersonalTestForUnit> personalTestForUnits = personalTestForUnitPage.getContent();
            if (personalTestForUnits.size() > 0) {
                PersonalTestForUnit testUnit = personalTestForUnits.get(0);
                unit.setTestScore(testUnit.getScore());
                Long test = testUnit.getTotalTestTime();
                Long pass = testUnit.getTestTime();
                unit.setTestTime(Arith.dayHousMinS(test));
                unit.setPassTime(Arith.dayHousMinS(pass));
            } else {
                unit.setTestScore(0);
                unit.setTestTime("0秒");
                unit.setPassTime("0秒");
            }

            //课本每单元的语音复习次数
            ApiResult apiResult1 = userReviewService.getone(userId, bookId, unit.getId(), 1);
            UserRevivews lu1 = (UserRevivews) apiResult1.getData();
            if (lu1 != null) {
                unit.setUnitTypeNumber1(lu1.getTimes());
            }else{
                unit.setUnitTypeNumber1("0");
            }
            //课本每单元的听音辨意次数
            ApiResult apiResult2 = userReviewService.getone(userId, bookId, unit.getId(), 2);
            UserRevivews lu2 = (UserRevivews) apiResult2.getData();
            if (lu1 != null) {
                unit.setUnitTypeNumber2(lu2.getTimes());
            }else{
                unit.setUnitTypeNumber2("0");
            }
            //智能默写次数
            ApiResult apiResult4 = userReviewService.getone(userId, bookId, unit.getId(), 4);
            UserRevivews lu4 = (UserRevivews) apiResult4.getData();
            if (lu4 != null) {
                unit.setUnitTypeNumber4(lu4.getTimes());
            }else{
                unit.setUnitTypeNumber4("0");
            }
            //句子填空次数
            ApiResult apiResult5 = userReviewService.getone(userId, bookId, unit.getId(), 5);
            UserRevivews lu5 = (UserRevivews) apiResult5.getData();
            if (lu5 != null) {
                unit.setUnitTypeNumber5(lu5.getTimes());
            }else{
                unit.setUnitTypeNumber5("0");
            }
            //句式练习次数
            ApiResult apiResult6 = userReviewService.getone(userId, bookId, unit.getId(), 6);
            UserRevivews lu6 = (UserRevivews) apiResult6.getData();
            if (lu6 != null) {
                unit.setUnitTypeNumber6(lu6.getTimes());
            }else{
                unit.setUnitTypeNumber6("0");
            }
            ApiResult apiResult = reviewTestService.findReviewTestList(userId,unit.getId());
            List<ReviewTest> reviewTests = (List<ReviewTest>) apiResult.getData();
            if(reviewTests != null){
                if(reviewTests.size() == 0){
                    unit.setUnitTestStart("记忆");
                }else{
                    unit.setUnitTestStart("复习"+reviewTests.size());
                }
            }else{
                unit.setUnitTestStart("记忆");
            }
            list.add(unit);
        }
        return list;
    }
}
