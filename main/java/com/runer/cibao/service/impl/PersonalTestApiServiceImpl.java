package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.*;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.*;
import com.runer.cibao.util.NormalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

import static com.runer.cibao.exception.ResultMsg.*;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/28
 **/

@Service
public class PersonalTestApiServiceImpl implements PersonalTestApiService {

    @Autowired
    PersonalLearnBookService personalLearnBookService ;

    @Autowired
    PersonalTestForBookService personalTestForBookService ;

    @Autowired
    PersonalLearnUnitService personalLearnUnitService ;

    @Autowired
    PersonalTestForUnitService personalTestForUnitService ;

    @Override
    public ApiResult findPersonalLearnBookTest(Long userId) {
        if (userId==null){
            return  new ApiResult(USER_ID_IS_NOT_ALLOWED_NULL,null) ;
        }
      ApiResult booksResult  =  personalLearnBookService.findAllByUserId(userId) ;
      if (booksResult.getMsgCode()!=SUCCESS.getMsgCode()){
          return  booksResult ;
      }

      /****
         * 个人的课本
         */
      List<PersonalLearnBook> personalLearnBookList = (List<PersonalLearnBook>) booksResult.getData();
      //输出的api实体类；
      List<BookBeanForTest> bookBeanForTests =new ArrayList<>() ;

      if (!ListUtils.isEmpty(personalLearnBookList)){
          personalLearnBookList.forEach(personalLearnBook -> {
              /**
               * 创建输出的实体类实例
               */
              BookBeanForTest bookBeanForTest =new BookBeanForTest() ;
              /**
               * 设置公共的属性
               */
              bookBeanForTest.setPersonalLearnBook(personalLearnBook);
              bookBeanForTest.setBookName(personalLearnBook.getLearnBook().getBookName());
              bookBeanForTest.setImgUrl(personalLearnBook.getLearnBook().getImgUrl());
              /**
               * 获得最新的书本测试
               */
              ApiResult personalTestResult =  personalTestForBookService.findTestLatest(personalLearnBook.getId());

              //没有测试的情况下；
            if (personalTestResult.getMsgCode()!=SUCCESS.getMsgCode()){
                bookBeanForTest.setCurrentScore(0);
                bookBeanForTest.setPreLearnScore(0);
            }else{
                /**
                 * 最新的一个
                 */
                PersonalTestForBook latestOne = (PersonalTestForBook) personalTestResult.getData();
                /**
                 * 学前的一个
                 */
                ApiResult personalPreLearnTest =personalTestForBookService.findTestPreLearn(personalLearnBook.getId()) ;
                if (personalPreLearnTest.getMsgCode()== ResultMsg.SUCCESS.getMsgCode()){
                    PersonalTestForBook personalTestForBook = (PersonalTestForBook) personalPreLearnTest.getData();
                    bookBeanForTest.setPreLearnScore(personalTestForBook.getScore());
                }else{
                    bookBeanForTest.setPreLearnScore(-1);
                }
                bookBeanForTest.setCurrentScore(latestOne.getScore());
            }

            bookBeanForTests.add(bookBeanForTest) ;

          });
      }

        return new ApiResult(SUCCESS,bookBeanForTests);
    }


    @Override
    public ApiResult findPersonalLearnBookUnitTest(Long personalLearnBookId) {

        if (personalLearnBookId==null){
            return  new ApiResult(ID_IS_NULL,null) ;
        }


        ApiResult booksResult  =  personalLearnUnitService.findByLearnBookId(personalLearnBookId) ;

        if (booksResult.getMsgCode()!=SUCCESS.getMsgCode()){
            return  booksResult ;
        }

        /**
         * 个人的课本
         */
        List<PersonalLearnUnit> personalLearnUnits = ((Page<PersonalLearnUnit>)booksResult.getData()).getContent();

        List<UnitBeanForTest> bookBeanForTests =new ArrayList<>();
        /**
         */
        if (!ListUtils.isEmpty(personalLearnUnits)){
            personalLearnUnits.forEach(personalLearnUnit -> {

                /**
                 * 创建输出的实体类实例
                 */
                UnitBeanForTest unitBeanForTest =new UnitBeanForTest() ;
                /**
                 * 设置公共的属性
                 */
                unitBeanForTest.setPersonalLearnUnit(personalLearnUnit);
                unitBeanForTest.setName(personalLearnUnit.getBookUnit().getName());
                /**
                 * 获得最新的书本测试
                 */
                ApiResult personalTestResult =  personalTestForUnitService.findLatestOne(personalLearnUnit.getId());

                //没有测试的情况下；
                if (personalTestResult.isFailed()){
                    unitBeanForTest.setCurrentScore(0);
                    unitBeanForTest.setPreScore(0);
                }else{
                    /**
                     * 最新的一个
                     */
                    PersonalTestForUnit latestOne = (PersonalTestForUnit) personalTestResult.getData();
                    /**
                     * 学前的一个
                     */
                    ApiResult personalPreLearnTest =personalTestForUnitService.findPreOne(personalLearnUnit.getId()) ;
                    if (personalPreLearnTest.isSuccess()){
                        PersonalTestForBook personalTestForBook = (PersonalTestForBook) personalPreLearnTest.getData();
                        unitBeanForTest.setPreScore(personalTestForBook.getScore());
                    }else{
                        unitBeanForTest.setPreScore(0);
                    }
                    unitBeanForTest.setCurrentScore(latestOne.getScore());
                }
                personalLearnUnit.setPersonalLearnBook(null);
                personalLearnUnit.setBookUnit(null);

                bookBeanForTests.add(unitBeanForTest) ;
            });
        }
        return new ApiResult(SUCCESS,bookBeanForTests);
    }

    @Override
    public ApiResult findPeronalLearnBookTestRecords(Long personalLearnBookId) {

        Page<PersonalTestForBook> page = personalTestForBookService.findBooksTest(null, null, personalLearnBookId, null, null, null, 1, Integer.MAX_VALUE);;

        return NormalUtil.generateSuccessResult(page.getContent()) ;
    }

    @Override
    public ApiResult findPersonalLearnUnitsTestRecords(Long userId, Long personalLearnTestId) {
        Page<PersonalTestForUnit> page = personalTestForUnitService.findUnitTests(userId, null, personalLearnTestId, null, null, null, 1, Integer.MAX_VALUE);;
        return NormalUtil.generateSuccessResult(page.getContent()) ;

    }


}
