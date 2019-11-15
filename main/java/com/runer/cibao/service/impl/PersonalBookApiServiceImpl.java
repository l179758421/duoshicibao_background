package com.runer.cibao.service.impl;

import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.*;
import com.runer.cibao.service.*;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.runer.cibao.exception.ResultMsg.SUCCESS;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/26
 **/

@Service
public class PersonalBookApiServiceImpl implements PersonalBookApiService {

    @Autowired
    PersonalLearnBookService personalLearnBookService ;

    @Autowired
    PersonalTestForBookService personalTestForBookService ;

    @Autowired
    LearnBookService learnBookService ;


    @Autowired
    AdminConfigService adminConfigService ;

    @Autowired
    PersonalLearnUnitService personalLearnUnitService ;




    @Override
    public ApiResult findLearnBooksWithOutCurrent(Long userId  ) {
     return    findAllPersonalBooks(userId,true) ;
    }


    @Override
    public ApiResult findCurrentLearnBook(Long userId) {
        ApiResult bookResult =personalLearnBookService.getCurrentBook(userId);
        if (bookResult.isFailed()){
            return  new ApiResult("您尚未购买课本");
        }
        BeanForPersonalBookList beanForBookTest =personalBookToAPiBean(bookResult) ;
        return new ApiResult(SUCCESS,beanForBookTest);
    }

    /**
     * 课本转化成api ；
     * @param bookResult
     * @return
     */
    private BeanForPersonalBookList personalBookToAPiBean(ApiResult bookResult){

        PersonalLearnBook personalLearnBook = (PersonalLearnBook) bookResult.getData();
        if (personalLearnBook.getLearnBook()==null){
            return  null ;
        }
        BeanForPersonalBookList beanForBookTest =new BeanForPersonalBookList();
        beanForBookTest.setCurrenNum(personalLearnBook.getCurrentWordNum());
        beanForBookTest.setBookid(personalLearnBook.getLearnBook().getId());
        beanForBookTest.setBookCoverImg(personalLearnBook.getLearnBook().getImgUrl());
        beanForBookTest.setPrivce(String.valueOf(personalLearnBook.getLearnBook().getPrice()));
        if (personalLearnBook.getScore()!=null)
            beanForBookTest.setCurrentScore(personalLearnBook.getScore());
        beanForBookTest.setTotalWordsNum(personalLearnBook.getLearnBook().getWordsNum());
        beanForBookTest.setPersonalLeanrBookId(personalLearnBook.getId());
        beanForBookTest.setBookName(personalLearnBook.getLearnBook().getBookName());
        beanForBookTest.setLearnedWords(personalLearnBook.getLearnedWords());
        if (personalLearnBook.getUnitId()!=null) {
            beanForBookTest.setUnitId(personalLearnBook.getUnitId());
        }
        if (null!=personalLearnBook.getIsPreTested()&&personalLearnBook.getIsPreTested()== Config.IS_PRE_TEST) {
            beanForBookTest.setPred(true);
        }
        if (null!=personalLearnBook.getIsPassed()&&personalLearnBook.getIsPassed()== Config.PASSED){
            beanForBookTest.setPassed(true);
        }
        if (null!=personalLearnBook.getIsFinished()&&personalLearnBook.getIsFinished()== Config.isFinished){
            beanForBookTest.setFinished(true);
            beanForBookTest.setCurrenNum(beanForBookTest.getTotalWordsNum());
        }


        //todo 删除；
        if (!personalLearnBook.isCurrentUnitFinished()){
            if (personalLearnBook.getUnitId()!=null&&personalLearnBook.getPersonalLearnBooks()!=null&&personalLearnBook.getPersonalLearnBooks().getAppUser()!=null){
                ApiResult units = personalLearnUnitService.findOneByUserIdAndUnitId(personalLearnBook.getUnitId(), personalLearnBook.getPersonalLearnBooks().getAppUser().getId(), null);
                if (units.isSuccess()){
                    PersonalLearnUnit personalLearnUnit = (PersonalLearnUnit) units.getData();
                    if (personalLearnUnit.getIsFinished()== Config.isFinished){
                        beanForBookTest.setCurrentUnitisFinished(true);
                    }else{
                        beanForBookTest.setCurrentUnitisFinished(false);
                    }
                }
            }
        }




        //是否过了体验期； 过了以后进行购买；
        if (personalLearnBook.getBoughtTime()!=null){
            beanForBookTest.setBuy(true);
            Date boughtTime =personalLearnBook.getBoughtTime() ;
            beanForBookTest.setBoughtTime(boughtTime.getTime());
            if (DateUtils.addYears(boughtTime,1).after(new Date())){
                beanForBookTest.setValiable(true);
            }else{
                beanForBookTest.setValiable(false);
            }
            if (personalLearnBook.getFreeBoughtTime()!=null) {
                beanForBookTest.setBoughtTime(personalLearnBook.getFreeBoughtTime().getTime());
            }
        }else{

            beanForBookTest.setBuy(false);
            beanForBookTest.setValiable(true);

            AdminConfig adminConfig = (AdminConfig) adminConfigService.forceGetAdminConfig().getData();
            int frees = adminConfig.getFreeExperienceTime() ;

            Date freeTime =new Date() ;
            if (personalLearnBook.getFreeBoughtTime()!=null) {
              freeTime =personalLearnBook.getFreeBoughtTime() ;
            }
            beanForBookTest.setFreeBoughtTime(freeTime.getTime());
            //操作体验的时间，提出去购买
            if (DateUtils.addDays(freeTime,frees).before(new Date())){
                beanForBookTest.setFreeOutTime(true);
            }else{
                beanForBookTest.setFreeOutTime(false);
            }

        }
        beanForBookTest.setUnitName(personalLearnBook.getCurrentUnitName());
        beanForBookTest.setVersion(personalLearnBook.getLearnBook().getVersion());
        beanForBookTest.setStage(personalLearnBook.getLearnBook().getStage());
        beanForBookTest.setGrade(personalLearnBook.getLearnBook().getGrade());

        return  beanForBookTest ;
    }

    @Override
    public ApiResult findAllbooks(String version, String stage, Long userId) {

        //结果；
        List<BookBeanForApi> beanForApis =new ArrayList<>() ;

        List<LearnBook> books = learnBookService.findAllBooks(version, null, null, stage);

        List<PersonalLearnBook> personalLearnBooks =new ArrayList<>( );

        //用于对比是否购买
        if (userId!=null){
            personalLearnBooks =personalLearnBookService.findPersonalLearnBooks(userId,null,null,null,1,Integer.MAX_VALUE).getContent();
        }

        Map<Long , PersonalLearnBook> personalLearnBookMap =new HashMap<>();

        personalLearnBooks.forEach(personalLearnBook -> {
            personalLearnBookMap.put(personalLearnBook.getLearnBook().getId(),personalLearnBook) ;
        });


        for (LearnBook learnBook : books) {
            BookBeanForApi beanForApi =new BookBeanForApi() ;
            beanForApi.setLearnBook(learnBook);
            if (personalLearnBookMap.containsKey(learnBook.getId())){
                PersonalLearnBook personalLearnBook =personalLearnBookMap.get(learnBook.getId()) ;
                beanForApi.setBuy(personalLearnBook.isBuy());
                beanForApi.setActive(true);
                beanForApi.setCurrentWord(personalLearnBookMap.get(learnBook.getId()).getCurrentWordNum());
                beanForApi.setPersonalBookId(personalLearnBookMap.get(learnBook.getId()).getId());
            }else{
                beanForApi.setActive(false);
            }
            beanForApi.setTotalWord(learnBook.getWordsNum());
            beanForApi.setActivePrice(learnBook.getPrice());
            beanForApi.setImgUrl(learnBook.getImgUrl());
            beanForApis.add(beanForApi);
        }

        return new ApiResult(SUCCESS,beanForApis);

    }

    /*
    todo
     */
    @Override
    public ApiResult findAllStage() {

        List<LearnBook> learnBooks = learnBookService.findAll();

        Map<String,Integer> stages =new HashMap<>() ;

        for (LearnBook learnBook : learnBooks) {
            if (!StringUtils.isEmpty(learnBook.getStage())){
                if (!stages.containsKey(learnBook.getStage())){
                    stages.put(learnBook.getStage(),1) ;
                }else{
                    stages.put(learnBook.getStage(),stages.get(learnBook.getStage())+1);
                }
            }
        }
        return  new ApiResult(SUCCESS,stages);
    }


    @Override
    public ApiResult findVersions(String stage) {

        List<LearnBook> learnBooks=  learnBookService.findAllBooks(null,null,
                null,stage);

        Set<String> versions =new HashSet<>() ;

        for (LearnBook learnBook : learnBooks) {
            versions.add(learnBook.getVersion()) ;
        }
        return new ApiResult(SUCCESS,versions);
    }

    /**
     * 获得个人的课本；
     * @param userId
     * @param isWithOutCurrent
     * @return
     */
    @Override
    public ApiResult findAllPersonalBooks(Long userId,boolean isWithOutCurrent) {

        List<PersonalLearnBook> books =null ;
        if (isWithOutCurrent) {
            //books
            books = personalLearnBookService.getBooksWithOutCurrentBook(userId, 1, Integer.MAX_VALUE).getContent();
        }else{
            books =personalLearnBookService.findPersonalLearnBooks(userId,null,null,null,1,Integer.MAX_VALUE).getContent();
        }
        List<BeanForPersonalBookList> personalBookLists =new ArrayList<>() ;
        books.forEach(personalLearnBook -> {
            BeanForPersonalBookList bean  =personalBookToAPiBean(new ApiResult(SUCCESS,personalLearnBook));
            if (bean!=null) {
                personalBookLists.add(bean);
            }
        });


        return new ApiResult(SUCCESS,personalBookLists);
    }

}
