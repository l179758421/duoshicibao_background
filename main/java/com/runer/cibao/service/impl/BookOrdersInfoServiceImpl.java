package com.runer.cibao.service.impl;

import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.dao.*;
import com.runer.cibao.domain.*;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.AgentsService;
import com.runer.cibao.service.BookOrdersInfoService;
import com.runer.cibao.service.ClassInSchoolService;
import com.runer.cibao.util.NormalUtil;
import com.runer.cibao.util.machine.DateMachine;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/7
 **/


@Service
public class BookOrdersInfoServiceImpl implements BookOrdersInfoService {

    @Autowired
    AppUserOrderDao appUserOrderDao ;

    @Autowired
    LearnBookDao learnBookDao;

    @Autowired
    ClassInSchoolService classInSchoolService ;


    @Autowired
    PersonalLearnBookDao personalLearnBookDao ;


    @Autowired
    AppUserDao appUserDao ;


    @Autowired
    AgentsService agentsService ;


    @Autowired
    PersonalTestForBookDao personalTestForBookDao ;

    @Autowired
    PersonalTestForUnitDao personalTestForUnitDao ;




    @Override
    public ApiResult getAgentsSellsInfo(Integer monthInfo, Long agentId) {
        ApiResult agentsResult =agentsService.findByIdWithApiResult(agentId);


        if (agentsResult.isFailed()){
           return  agentsResult ;
        }


        Agents agents = (Agents) agentsResult.getData();
        List<School> schools = agents.getSchools();


        if (ListUtils.isEmpty(schools)){
            return  new ApiResult("暂无关联学校，关联学校以后进行统计");
        }

        AtomicLong personsCount = new AtomicLong();
        AtomicLong personalBooksCount = new AtomicLong();
        AtomicLong booksCountMonth =new AtomicLong( );

        Date endDate  =new Date();
         Date  startDate =new DateMachine().getDaysBefore(30);


        schools.forEach(school -> {
            long booksCount =   personalLearnBookDao.findAgentsOrdersCount(school.getId(),null,null,null, null, null);
            personalBooksCount.addAndGet(booksCount);
          long personlcount = appUserDao.countUserCount(null,school.getId());
          personsCount.addAndGet(personlcount);
            long monthCount  =   personalLearnBookDao.findAgentsOrdersCount(school.getId(),null,null,null, startDate, endDate);
            booksCountMonth.addAndGet(monthCount);


        });

        Map<String, Object> result = NormalUtil.generateMapData(data -> {
            data.put("personsCount",personsCount.get()) ;
            data.put("booksCount",personalBooksCount.get()) ;
            data.put("monthCount",booksCountMonth.get()) ;
        });

        return new ApiResult(ResultMsg.SUCCESS,result);
    }

    @Override
    public ApiResult findOrdersInfo(Long angetsId, Date startDate, Date endDate) {
        return null;
    }
    @Override
    public Page<AppUserOrder> getBooksCreateOrders(Long schoolId, Long classId, Long bookId,
                                                   Date startDate, Date endDate, Integer page, Integer limit) {
       return  appUserOrderDao.findAgentsOrders(schoolId,classId,bookId,startDate,endDate,PageableUtil.basicPage(page,limit));
    }

    @Override
    public Page<AppUser> findAppUsers(Long classInschoolId, Long schoolId  , String userName , Integer page, Integer limit) {
        Page<AppUser> pages = appUserDao.findAppUsers(null, schoolId, classInschoolId, userName,null ,null,null, PageableUtil.basicPage(page, limit));

        return  pages ;
    }

    @Override
    public LayPageResult<ClassAppUsersInfoBean> findClassInfos(String className, Long schoolId, Integer page, Integer limit) {

        Page  classes = classInSchoolService.findClassInSchool(schoolId, null, className, page, limit);;

        List<ClassAppUsersInfoBean> datas =new ArrayList<>() ;

        classes.getContent().forEach(classInSchool -> {
            ClassInSchool classInSchool1 = (ClassInSchool) classInSchool;
            ClassAppUsersInfoBean bean =new ClassAppUsersInfoBean() ;
            bean.setClassId(classInSchool1.getId());
            bean.setClassName(classInSchool1.getName());
            //
            long count =personalLearnBookDao.findAgentsOrdersCount(null,classInSchool1.getId(),null,null,null,null);
            bean.setBookCreateCount(count);

            bean.setSchoolId(classInSchool1.getSchool().getId());
            bean.setSchoolName(classInSchool1.getSchool().getName());

            long  userCount =appUserDao.countUserCount(classInSchool1.getId(),null) ;
            bean.setUsersCount(userCount);
            datas.add(bean) ;
        });

        LayPageResult<ClassAppUsersInfoBean> result =new LayPageResult<>() ;
        result.setData(datas);
        result.setCode(0);
        result.setMsg("success");
        result.setCount(classes.getTotalElements());

        return result;
    }


    /**
     * 统计个人的销售学习情况
     * @param schoolId
     * @param classId
     * @param page
     * @param limit
     * @return
     */
    @Override
    public LayPageResult<AppUserShowInfoBean> findAppUserInfos(Long schoolId, Long classId, Integer page, Integer limit) {
        if (schoolId==null&&classId==null){
            return  new LayPageResult("学校和班级id不能够同时为空");
        }
        Page<AppUser> pages = appUserDao.findAppUsers(null, schoolId, classId, null,null, null ,null,PageableUtil.basicPage(page, limit));

        List<AppUserShowInfoBean> appUserShowInfoBeans =new ArrayList<>() ;

        for (AppUser appUser : pages) {
            AppUserShowInfoBean appUserShowInfoBean =new AppUserShowInfoBean() ;

            appUserShowInfoBean.setUserId(appUser.getId());
            appUserShowInfoBean.setUserName(appUser.getName());

            //class
            appUserShowInfoBean.setClassId(appUser.getClassInSchool().getId());
            appUserShowInfoBean.setClassName(appUser.getClassInSchool().getName());
            School school =appUser.getClassInSchool().getSchool() ;
            if (school!=null) {
                appUserShowInfoBean.setSchoolId(appUser.getClassInSchool().getSchool().getId());
                appUserShowInfoBean.setSchoolName(appUser.getClassInSchool().getSchool().getName());
            }

            //learnboooks;
            Map<Long,String> books =new HashMap<>() ;

            PersonalLearnBooks personalLearnBooks =appUser.getPersonalLearnBooks() ;
            //pesonalLearnBook
            if (personalLearnBooks!=null){
                //count
               appUserShowInfoBean.setCountBooks(personalLearnBooks.getPersonalLearnBooks()==null?0:personalLearnBooks.getPersonalLearnBooks().size());

               if (!ListUtils.isEmpty(personalLearnBooks.getPersonalLearnBooks())){
                   personalLearnBooks.getPersonalLearnBooks().forEach(personalLearnBook -> {
                           books.put(personalLearnBook.getId(),personalLearnBook.getLearnBook().getBookName()) ;
                           if (personalLearnBook.getIsCurrentBook()== Config.IS_CURRENT){
                              appUserShowInfoBean.setCurrentBookName(personalLearnBook.getLearnBook().getBookName());
                              appUserShowInfoBean.setCurrentWord(String.valueOf(personalLearnBook.getCurrentWord()));
                              appUserShowInfoBean.setCurrentUnit(personalLearnBook.getCurrentUnitName());
                              appUserShowInfoBean.setCurrentWordName(personalLearnBook.getCurrnetWordname());
                              appUserShowInfoBean.setProgress(personalLearnBook.getCurrentWordNum()+"/"+personalLearnBook.getLearnBook().getWordsNum());
                           }
                   });
               }
            }

            appUserShowInfoBean.setBooks(books);
            //bookTest ;
            Page<PersonalTestForBook> booksTestPage = personalTestForBookDao.findBooksTest(appUser.getId(), null, null, null, null, null, PageableUtil.basicPage(1, Integer.MAX_VALUE));

            List<AppUserShowInfoBean.BookTestInfo> bookTest =new ArrayList<>() ;
            booksTestPage.getContent().forEach(personalTestForBook -> {
                AppUserShowInfoBean.BookTestInfo bookTestInfo =new AppUserShowInfoBean.BookTestInfo() ;
                bookTestInfo.setBookId(personalTestForBook.getPersonalLearnBook().getLearnBook().getId());
                bookTestInfo.setBookName(personalTestForBook.getPersonalLearnBook().getLearnBook().getBookName());
                bookTestInfo.setIsPre(personalTestForBook.getIsPreLearnTest());
                bookTestInfo.setTestDate(personalTestForBook.getTestDate());
                bookTestInfo.setScore(personalTestForBook.getScore());
                bookTest.add(bookTestInfo) ;
            });
            appUserShowInfoBean.setBookTestInfos(bookTest);
            //unitTest
            Page<PersonalTestForUnit> unitsTestPage = personalTestForUnitDao.findPersionUnitsTest(null, appUser.getId(), null, null, null, null, PageableUtil.basicPage(1, Integer.MAX_VALUE));

            List<AppUserShowInfoBean.UnitTestInfo> unitTest =new ArrayList<>() ;
            unitsTestPage.getContent().forEach(personalTestForUnit -> {
                AppUserShowInfoBean.UnitTestInfo bookTestInfo =new AppUserShowInfoBean.UnitTestInfo() ;
                bookTestInfo.setUnitid(personalTestForUnit.getPersonalLearnUnit().getBookUnit().getId());
                bookTestInfo.setUnitName(personalTestForUnit.getPersonalLearnUnit().getBookUnit().getName());
                bookTestInfo.setIsPre(personalTestForUnit.getIsPreLearnTest());
                bookTestInfo.setTesttime(personalTestForUnit.getTestDate());
                bookTestInfo.setScore(personalTestForUnit.getScore());
                unitTest.add(bookTestInfo) ;
            });

            appUserShowInfoBean.setUnitTestInfos(unitTest);
            appUserShowInfoBeans.add(appUserShowInfoBean);
        }
        LayPageResult<AppUserShowInfoBean> datas =new LayPageResult<>(appUserShowInfoBeans);
        datas.setCount(pages.getTotalElements());
        return datas;
    }
}
