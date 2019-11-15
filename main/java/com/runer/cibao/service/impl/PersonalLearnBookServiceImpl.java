package com.runer.cibao.service.impl;

import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.PersonalLearnBookDao;
import com.runer.cibao.domain.*;
import com.runer.cibao.domain.repository.PersonalLearnBookRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.*;
import com.runer.cibao.util.DownloadUtil;
import com.runer.cibao.util.NormalUtil;
import com.runer.cibao.util.machine.ZipMachine;
import com.runer.cibao.util.page.PageableUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.util.ListUtils;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.runer.cibao.exception.ResultMsg.NOT_FOUND;
import static com.runer.cibao.exception.ResultMsg.SUCCESS;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/25
 **/

@Service
public class PersonalLearnBookServiceImpl extends BaseServiceImp<PersonalLearnBook, PersonalLearnBookRepository> implements PersonalLearnBookService {

    @Autowired
    PersonalLearnBookDao personalLearnBookDao ;

    @Autowired
    AppUserService appUserService ;

    @Autowired
    LearnBookService learnBookService;

    @Autowired
    PersonalLearnBooksService personalLearnBooksService ;

    @Autowired
    PersonalLearnUnitService personalLearnUnitService ;

    @Autowired
    AppUserOrderService appUserOrderService ;

    @Autowired
    BookWordService bookWordService ;

    @Autowired
    CityService cityService;

    @Autowired
    ProvinceService provinceService;

    @Autowired
    SchoolServivce schoolServivce;

    @Autowired
    AdminConfigService configService ;

    @Autowired
    ZipMachine zipMachine ;

    @Autowired
    PersonalTestForBookService personalTestForBookService;


    /**
     * 生成个人课本，更新个人课本；
     * @param id
     * @param userId
     * @param bookId
     * @param currentWord
     * @param boughtTime
     * @param isCurrentBook
     * @param score
     * @param isPassed
     * @return
     */
    @Override
    public ApiResult addOrUpdateLearnBook(Long id, Long userId, Long bookId, Long currentWord, Date boughtTime, Integer isCurrentBook, Integer score, Integer isPassed) {

        PersonalLearnBook personalLearnBook =new PersonalLearnBook();
        /**
         * 更新的状态下
         */
        if (id!=null){
         ApiResult  bookResult  =  findByIdWithApiResult(id);
         if (bookResult.isFailed()){
             return  bookResult ;
         }
         personalLearnBook = (PersonalLearnBook) bookResult.getData();
        }

        /**
         * books
         */
        PersonalLearnBooks personalLearnBooks =null ;

        ApiResult  apiResultBooks =personalLearnBooksService.findbyUsrId(userId);
        //查到的情况下
        if (apiResultBooks.getMsgCode()==SUCCESS.getMsgCode()){
             personalLearnBooks = (PersonalLearnBooks) apiResultBooks.getData();
        }else{
             ApiResult addResult  =personalLearnBooksService.addPersonalBooks(userId);
             if (addResult.getMsgCode()!=SUCCESS.getMsgCode()){
                 return  addResult ;
             }
             personalLearnBooks = (PersonalLearnBooks) addResult.getData();

        }
        personalLearnBook.setPersonalLearnBooks(personalLearnBooks);
        /**
         * learnBook
         */
        ApiResult  bookAPiResult =learnBookService.findByIdWithApiResult(bookId);

        if (bookAPiResult.isFailed()){
            return  bookAPiResult ;
        }

        LearnBook learnBook = (LearnBook) bookAPiResult.getData();

        personalLearnBook.setLearnBook(learnBook);

        if (currentWord!=null) {
            personalLearnBook.setCurrentWord(currentWord);
        }

        //购买时间来区分是否进行了购买，是不是免费的购买；
        if (boughtTime!=null) {
            personalLearnBook.setBoughtTime(boughtTime);
            personalLearnBook.setBuy(true);
        }else{
            personalLearnBook.setBuy(false);
            personalLearnBook.setFreeBoughtTime(new Date());
        }

        if (isCurrentBook!=null){
            personalLearnBook.setIsCurrentBook(isCurrentBook);
        }
        if (score!=null){
            personalLearnBook.setScore(score);
        }
        if (isPassed!=null){
            personalLearnBook.setIsPassed(isPassed);
        }
        if (learnBook.getWordsNum()!=null){
            personalLearnBook.setTotalWordNum(learnBook.getWordsNum());
        }

        personalLearnBook =r.saveAndFlush(personalLearnBook);
        /**
         * 当id不为空
         * 切unit对应的不存在的情况下 ；
         */
        //生成units
        if (personalLearnBook.getId()!=null&&ListUtils.isEmpty(personalLearnBook.getPersonalLearnUnits())) {
            personalLearnUnitService.generatePersonalLearnUnitByBook(personalLearnBook.getId());
        }
        return new ApiResult(SUCCESS,personalLearnBook);
    }

    @Override
    public Page<PersonalLearnBook> findPersonalLearnBooks(Long userId, Long bookId, Integer isPassed, Integer isCurrentBook, Integer page , Integer limit) {
        Page<PersonalLearnBook> pageResult = personalLearnBookDao.findPersonalLearnBooks(userId, bookId, isPassed, isCurrentBook, PageableUtil.basicPage(page, limit));

        for (PersonalLearnBook personalLearnBook : pageResult.getContent()) {
            //是否过了体验期； 过了以后进行购买；
            if (personalLearnBook.getBoughtTime()!=null){
                Date boughtTime =personalLearnBook.getBoughtTime() ;
                if (DateUtils.addYears(boughtTime,1).after(new Date())){
                    personalLearnBook.setValiable(true);
                }else{
                    personalLearnBook.setValiable(false);
                }
            }else{
                personalLearnBook.setValiable(true);
            }
        }
        return pageResult;
    }

    @Override
    public Page<PersonalLearnBook> findPersonalLearnBooksByBookName(Long userId, String bookName, Integer page, Integer limit) {
       return personalLearnBookDao.findPersonalLearnBookByBookName(userId,bookName,PageableUtil.basicPage(page,limit));
    }

    @Override
    public ApiResult setLearnBookToCurrent(Long personalBookid) {

        //查找当前的课本
        ApiResult bookResult =findByIdWithApiResult(personalBookid);
        if (bookResult.getMsgCode()!=SUCCESS.getMsgCode()){
            return  bookResult ;
        }
        PersonalLearnBook personalLearnBook = (PersonalLearnBook) bookResult.getData();

        if (personalLearnBook.getIsCurrentBook()== Config.IS_CURRENT){
            return  new ApiResult(SUCCESS,personalLearnBook);
        }
        //不然的话
        Long userId = personalLearnBook.getPersonalLearnBooks().getAppUser().getId();

        List<PersonalLearnBook> personalLearnBooks = findPersonalLearnBooks(userId, null, null, Config.IS_CURRENT, 1, 10).getContent();

        //若是存在的情况下，进行更新
        if (!ListUtils.isEmpty(personalLearnBooks)){
            PersonalLearnBook currentBook= personalLearnBooks.get(0) ;
            currentBook.setIsCurrentBook(Config.NOT_CURRENT);
            r.saveAndFlush(currentBook);
        }
        personalLearnBook.setIsCurrentBook(Config.IS_CURRENT);
        personalLearnBook=r.saveAndFlush(personalLearnBook );

        return new ApiResult(SUCCESS,personalLearnBook);
    }

    @Override
    public ApiResult getCurrentBook(Long userId) {
        List<PersonalLearnBook> personalLearnBooks = findPersonalLearnBooks(userId, null, null, Config.IS_CURRENT, 1, 10).getContent();
        //若是存在的情况下，进行更新
        if (ListUtils.isEmpty(personalLearnBooks)){
            List<PersonalLearnBook> allBooks =findPersonalLearnBooks(userId,null,null,null,1,Integer.MAX_VALUE).getContent() ;
            //没有数据就是找不到
            if (ListUtils.isEmpty(allBooks)){
                return  new ApiResult("您还未购买课本");
            }

            //设置di yi be第一本书为当前的书；
            PersonalLearnBook learnBook =allBooks.get(0);
            learnBook.setIsCurrentBook(Config.IS_CURRENT);
            r.saveAndFlush(learnBook);

            if (learnBook.getBoughtTime()!=null){
                Date boughtTime =learnBook.getBoughtTime() ;
                if (DateUtils.addYears(boughtTime,1).after(new Date())){
                    learnBook.setValiable(true);
                }else{
                    learnBook.setValiable(false);
                }
            }else{
                learnBook.setValiable(true);
            }
            return  new ApiResult(SUCCESS,learnBook);
        }






        return new ApiResult(SUCCESS,personalLearnBooks.get(0));
    }
    @Override
    public Page<PersonalLearnBook>  getBooksWithOutCurrentBook(Long userId , Integer page , Integer limit) {
       return findPersonalLearnBooks(userId,null,null, Config.NOT_CURRENT,page,limit);
    }

    @Override
    public ApiResult getSinglePersonalLearnBook(Long userId, Long book_id) {
        List<PersonalLearnBook> learnBooks = findPersonalLearnBooks(userId, book_id, null, null, 1, 10).getContent();
        if (ListUtils.isEmpty(learnBooks)){
            return  new ApiResult(NOT_FOUND,null ) ;
        }

        PersonalLearnBook learnBook =learnBooks.get(0) ;


        if (learnBook.getBoughtTime()!=null){
            Date boughtTime =learnBook.getBoughtTime() ;
            if (DateUtils.addYears(boughtTime,1).after(new Date())){
                learnBook.setValiable(true);
            }else{
                learnBook.setValiable(false);
            }
        }else{
            learnBook.setValiable(true);
        }

        return  new ApiResult(SUCCESS,learnBooks.get(0));
    }


    @Override
    public List<PersonalLearnBook> getSinglePersonalLearnBook2(Long userId, Long book_id) {
        List<PersonalLearnBook> learnBooks = findPersonalLearnBooks(userId, book_id, null, null, 1, 10).getContent();

        return  learnBooks;
    }


    /**
     *
     * 购买个人的课本；支付金额；
     * @param bookId
     * @param userId
     * @param  all 是否直接进行购买课本；
     *温馨提示：新注册用户首册课本可免费体验3天   （）（）（）（）（）---
     * @return
     */
    @Override
    public ApiResult buyPersonalBook(Long bookId, Long userId ,boolean all) {
        if (!all){
            /**
             * 新注册用户首册课本可免费体验3天
             */
            ApiResult appUserResult =appUserService.findByIdWithApiResult(userId) ;
            if (appUserResult.isFailed()){
                return  appUserResult ;
            }
            if (getBookPrice(userId,bookId)==0){
                return    buyPersonalBookSup(bookId,userId);
            }
        }
        /**
         * 其他的购买；
         */
         ApiResult learnBookResult =learnBookService.findByIdWithApiResult(bookId) ;
         if (learnBookResult.isFailed()){
            return  learnBookResult ;
         }
         //判断是否体验过了，或者购买过了
         PersonalLearnBook checkExistedBook =personalLearnBookDao.findByUserIdAndBookId(userId,bookId) ;
         if (checkExistedBook!=null){
             //购买了的情况下；
             if (checkExistedBook.isBuy()) {
                 return new ApiResult(ResultMsg.HAD_BOUGHT_BOOKS, null);
             }
         }
         LearnBook le = (LearnBook) learnBookResult.getData();
         if (checkExistedBook==null){
             List<PersonalLearnBook> userBookList = personalLearnBookDao.findPersonalLearnBooksByUserId(userId);
             ApiResult addResult;
             //若是没有数据的情况下；---新添加的设置为当前的课本；
             if(userBookList.size()==0){
                 addResult = addOrUpdateLearnBook(null, userId, bookId, 0L, new Date(), 1, null, Config.NOT_PASSED);
                 //有数据的情况下；添加课本；
             }else{
                 addResult = addOrUpdateLearnBook(null, userId, bookId, 0L, new Date(), 0, null, Config.NOT_PASSED);
             }
             if (addResult.isFailed()){
                 return  addResult ;
             }
             //生成订单相关；
             PersonalLearnBook personalLearnBook = (PersonalLearnBook) addResult.getData();
             //生成订单
             ApiResult orderResult = appUserOrderService.createOrder(userId, Config.ORDER_TPYE_BUY_BOOKS, "购买图书-直接购买",
                     le.getBookName(), personalLearnBook.getId(), 0 - le.getPrice());
             relateOrderBook(orderResult,personalLearnBook);
             return  orderResult ;
         }else{
             //生成订单
             ApiResult orderResult = appUserOrderService.createOrder(userId, Config.ORDER_TPYE_BUY_BOOKS, "购买图书-体验-购买",
                     le.getBookName(), checkExistedBook.getId(), 0 - le.getPrice());
             if (orderResult.isFailed()){
                  return  orderResult ;
             }
             checkExistedBook.setBuy(true);
             checkExistedBook.setBoughtTime(new Date());
             r.save(checkExistedBook);
             return  orderResult;
         }
    }
    /**
     * 体验期购买图书；
     * @param bookId
     * @param userId
     * @return
     */
    @Override
    public ApiResult buyPersonalBookSup(Long bookId, Long userId) {

        ApiResult learnBookResult =learnBookService.findByIdWithApiResult(bookId) ;
        if (learnBookResult.isFailed()){
            return  learnBookResult ;
        }
        if (personalLearnBookDao.findByUserIdAndBookId(userId,bookId) !=null){
            return  new ApiResult("您已购买此图书，请直接同步离线数据进行学习");
        }
        LearnBook learnBook = (LearnBook) learnBookResult.getData();
        //设置此课本为当前的课本；
        ApiResult addResult = addOrUpdateLearnBook(null, userId, bookId, 0L, null, 1, null, Config.NOT_PASSED);
        if (addResult.isSuccess()) {
            PersonalLearnBook personalLearnBook = (PersonalLearnBook) addResult.getData();
            //生成订单
            ApiResult orderResult = appUserOrderService.createOrder(userId, Config.ORDER_TPYE_BUY_BOOKS, "免费体验购买图书",
                    learnBook.getBookName(), personalLearnBook.getId(), 0);
            relateOrderBook(orderResult,personalLearnBook);
        }
        return  addResult ;
    }

    /**
     * 关联订单和personalLearnBook
     * @param orderResult
     * @param personalLearnBook
     */
    private void relateOrderBook(ApiResult orderResult , PersonalLearnBook personalLearnBook){
        if (orderResult.isFailed()){
            //删除数据
            try {
                List<PersonalLearnUnit> units = personalLearnUnitService.findUnits(null, null, personalLearnBook.getId(),null,null ,null, null, 1, Integer.MAX_VALUE).getContent();;
                personalLearnUnitService.deleteDatas(units);
                deleteById(personalLearnBook.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            //关联order；
            AppUserOrder appUserOrder = (AppUserOrder) orderResult.getData();
            personalLearnBook.setAppUserOrder(appUserOrder);
            r.saveAndFlush(personalLearnBook) ;
        }
    }



    @Override
    public ApiResult findAllByUserId(Long userId) {
        return new ApiResult(SUCCESS,findPersonalLearnBooks(userId,null,null,null,1,Integer.MAX_VALUE).getContent());
    }

    @Override
    public ApiResult findAllByUserIdAndBookName(Long userId,String bookName) {
        return new ApiResult(SUCCESS,personalLearnBookDao.findPersonalLearnBooksByUserIdAndBookName(userId,bookName));
    }

    @Override
    public Page<PersonalLearnBook> findBySchoolUID(String uid, Integer page, Integer limit) {
        return personalLearnBookDao.findBySchoolUID(uid,PageableUtil.basicPage(page, limit));
    }


    //todo currentNUm ;
    @Override
    public ApiResult setCurrentWord(Long bookWordId , Long personalBookId) {
        return new ApiResult(SUCCESS,null);
    }

    @Override
    public Map<String, Object> numsInfo(Long wordId, Long bookId) {

      long all =  personalLearnBookDao.findALlCount(wordId,bookId);
       long currnet = personalLearnBookDao.findCurrentbookNum(wordId,bookId);
       long left =  personalLearnBookDao.findLeftbookNum(wordId,bookId);
       return NormalUtil.generateMapData(data -> {
                data.put(Config.all,all) ;
                data.put(Config.current,currnet);
                data.put(Config.left,left) ;
           });

    }

    @Override
    public  Page<PersonalLearnBook> findByBoughtTimeAndUserId(Long userId, Date startTime, Date endTime, Integer page, Integer limit) {

        return personalLearnBookDao.findBooksByUser(userId,null,startTime,endTime,PageableUtil.basicPage(page,limit));
    }

    @Override
    public long findBooksCountByAddressAndDate(Long agentId,String rangDate, String province, String city, String schoolName) {
        Date startDate =null ;
        Date endDate =null ;
        if (!StringUtils.isEmpty(rangDate)){
            String[] dateDatas = rangDate.split(" ");
            try {
                startDate=DateUtils.parseDate(dateDatas[0]+" 00:00:00","yyyy-MM-dd HH:mm:ss") ;
                endDate =DateUtils.parseDate(dateDatas[2]+" 24:00:00","yyyy-MM-dd HH:mm:ss") ;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
       long count=0l;
       if( StringUtils.isEmpty(province)&& StringUtils.isEmpty(city) && StringUtils.isEmpty(schoolName) && StringUtils.isEmpty(agentId)){
          count= personalLearnBookDao.findBooksCountByUser(null,null,startDate,endDate);
       }
       else if(StringUtils.isEmpty(province)&& StringUtils.isEmpty(city) && StringUtils.isEmpty(schoolName) && !StringUtils.isEmpty(agentId)){

           Page<School> schoolPage= schoolServivce.findSchool(null,null,null,null,null,
                   null,null,null,agentId,1,Integer.MAX_VALUE);
           List<School> schoolList=schoolPage.getContent();
           //获取学校uid
           List<String> schoolUidList = getSchoolUid(schoolList);
           List<AppUser> appUsers = getAppUsers(schoolUidList);

           //获取激活课本数
           for (AppUser appUser : appUsers) {
               long bookCount = personalLearnBookDao.findBooksCountByUser(appUser.getId(),null,startDate,endDate);
               count+=bookCount;
           }
       }
      else if(!StringUtils.isEmpty(province) && StringUtils.isEmpty(city) && StringUtils.isEmpty(schoolName)){
           Province province1 =  provinceService.findByName(province);
           Page<School> schoolPage= schoolServivce.findSchool(null,null,province1.getId(),null,null,
                    null,null,null,agentId,1,Integer.MAX_VALUE);
           List<School> schoolList=schoolPage.getContent();
           //获取学校uid
           List<String> schoolUidList = getSchoolUid(schoolList);
           List<AppUser> appUsers = getAppUsers(schoolUidList);
           //获取激活课本数
            for (AppUser appUser : appUsers) {
                long bookCount = personalLearnBookDao.findBooksCountByUser(appUser.getId(),null,startDate,endDate);
                count+=bookCount;
            }
        }else if(!StringUtils.isEmpty(province) && !StringUtils.isEmpty(city) && StringUtils.isEmpty(schoolName)){
           Province province1 =  provinceService.findByName(province);
          City city1= cityService.findByName(city);
           Page<School> schoolPage= schoolServivce.findSchool(null,city1.getId(),province1.getId(),null,null,
                   null,null,null,agentId,1,Integer.MAX_VALUE);
           List<School> schoolList=schoolPage.getContent();
           //获取学校uid
           List<String> schoolUidList = getSchoolUid(schoolList);
           List<AppUser> appUsers = getAppUsers(schoolUidList);
           //获取激活课本数
           for (AppUser appUser : appUsers) {
               long bookCount = personalLearnBookDao.findBooksCountByUser(appUser.getId(),null,startDate,endDate);
               count+=bookCount;
           }
        }
        else if(!StringUtils.isEmpty(province) && !StringUtils.isEmpty(city) && !StringUtils.isEmpty(schoolName)){
           Province province1 =  provinceService.findByName(province);
           City city1= cityService.findByName(city);
           Page<School> schoolPage= schoolServivce.findSchool(schoolName,city1.getId(),province1.getId(),null,null,
                   null,null,null,agentId,1,Integer.MAX_VALUE);
           List<School> schoolList=schoolPage.getContent();
           //获取学校uid
           List<String> schoolUidList = getSchoolUid(schoolList);
           List<AppUser> appUsers = getAppUsers(schoolUidList);

           //获取激活课本数
           for (AppUser appUser : appUsers) {
               long bookCount = personalLearnBookDao.findBooksCountByUser(appUser.getId(),null,startDate,endDate);
               count+=bookCount;
           }
       }

        return count;
    }

    @Override
    public Page<PersonalLearnBook> findBooksByAddressAndDate(Long agentId, String rangDate, String province, String city, String schoolName, Integer page, Integer limit) {
        Date startDate =null ;
        Date endDate =null ;
        if (!StringUtils.isEmpty(rangDate)){
            String[] dateDatas = rangDate.split(" ");
            try {
                startDate=DateUtils.parseDate(dateDatas[0]+" 00:00:00","yyyy-MM-dd HH:mm:ss") ;
                endDate =DateUtils.parseDate(dateDatas[2]+" 24:00:00","yyyy-MM-dd HH:mm:ss") ;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Page<PersonalLearnBook>  personalLearnBookPage=null;
        if( StringUtils.isEmpty(province)&& StringUtils.isEmpty(city) && StringUtils.isEmpty(schoolName) && StringUtils.isEmpty(agentId)){
            personalLearnBookPage=personalLearnBookDao.findBooksByUser(null,null,startDate,endDate,PageableUtil.basicPage(page,limit));
        }
        //查询课本名称:
        else if(StringUtils.isEmpty(province)&& StringUtils.isEmpty(city) && !StringUtils.isEmpty(schoolName) && StringUtils.isEmpty(agentId)){
            personalLearnBookPage=personalLearnBookDao.findPersonalLearnBookByBookName(null,schoolName,PageableUtil.basicPage(page,limit));
        }

        else if(StringUtils.isEmpty(province)&& StringUtils.isEmpty(city) && StringUtils.isEmpty(schoolName) && !StringUtils.isEmpty(agentId)){
            Page<School> schoolPage= schoolServivce.findSchool(null,null,null,null,null,
                    null,null,null,agentId,1,Integer.MAX_VALUE);
            List<School> schoolList=schoolPage.getContent();
            //获取学校uid
            List<String> schoolUidList = getSchoolUid(schoolList);
            List<AppUser> appUsers = getAppUsers(schoolUidList);

            //获取激活课本数
            for (AppUser appUser : appUsers) {
                personalLearnBookPage=personalLearnBookDao.findBooksByUser(appUser.getId(),null,startDate,endDate,PageableUtil.basicPage(page,limit));
            }
        }
        else if(!StringUtils.isEmpty(province) && StringUtils.isEmpty(city) && StringUtils.isEmpty(schoolName)){
            Province province1 =  provinceService.findByName(province);
            Page<School> schoolPage= schoolServivce.findSchool(null,null,province1.getId(),null,null,
                    null,null,null,agentId,1,Integer.MAX_VALUE);
            List<School> schoolList=schoolPage.getContent();
            //获取学校uid
            List<String> schoolUidList = getSchoolUid(schoolList);
            List<AppUser> appUsers = getAppUsers(schoolUidList);

            //获取激活课本数
            for (AppUser appUser : appUsers) {
                personalLearnBookPage=personalLearnBookDao.findBooksByUser(appUser.getId(),null,startDate,endDate,PageableUtil.basicPage(page,limit));
            }

        }
        else if(!StringUtils.isEmpty(province) && !StringUtils.isEmpty(city) && StringUtils.isEmpty(schoolName)){
            Province province1 =  provinceService.findByName(province);
            City city1= cityService.findByName(city);
            Page<School> schoolPage= schoolServivce.findSchool(null,city1.getId(),province1.getId(),null,null,
                    null,null,null,agentId,1,Integer.MAX_VALUE);
            List<School> schoolList=schoolPage.getContent();
            //获取学校uid
            List<String> schoolUidList = getSchoolUid(schoolList);
            List<AppUser> appUsers = getAppUsers(schoolUidList);

            //获取激活课本数
            for (AppUser appUser : appUsers) {
              personalLearnBookPage = personalLearnBookDao.findBooksByUser(appUser.getId(),null,startDate,endDate,PageableUtil.basicPage(page,limit));
            }
        }
        else if(!StringUtils.isEmpty(province) && !StringUtils.isEmpty(city) && !StringUtils.isEmpty(schoolName)){
            Province province1 =  provinceService.findByName(province);
            City city1= cityService.findByName(city);
            Page<School> schoolPage= schoolServivce.findSchool(schoolName,city1.getId(),province1.getId(),null,null,
                    null,null,null,agentId,1,Integer.MAX_VALUE);
            List<School> schoolList=schoolPage.getContent();
            //获取学校uid
            List<String> schoolUidList = getSchoolUid(schoolList);
            List<AppUser> appUsers = getAppUsers(schoolUidList);

            //获取激活课本数
            for (AppUser appUser : appUsers) {
                personalLearnBookPage = personalLearnBookDao.findBooksByUser(appUser.getId(),null,startDate,endDate,PageableUtil.basicPage(page,limit));
            }
        }
        return personalLearnBookPage;
    }

    @Override
    public long findBooksByUserIdAndBookStage(Long userId, String stage) {
        return personalLearnBookDao.findBooksCountByUser(userId,stage,null,null);
    }
    /**
     * 获得单个课本的价格；
     * @param userId
     * @param bookId
     * @return
     */
    @Override
    public int getBookPrice(Long userId, Long bookId) {
        ApiResult personlBooks = findAllByUserId(userId);
        List<PersonalLearnBook> personalLearnBooks = (List<PersonalLearnBook>) personlBooks.getData();
        //若是为空的情况下；那么就是免费的价格；
        if (ListUtils.isEmpty(personalLearnBooks)){
             return  0 ;
        }
        LearnBook learnBook = (LearnBook) learnBookService.findByIdWithApiResult(bookId).getData();
        return learnBook.getPrice();
    }

    @Override
    public ApiResult findByBook(Long bookId) {
        return new ApiResult(SUCCESS,personalLearnBookDao.findByBookId(bookId));
    }
    /**
     * 课本zip的存储位置；
     */
    @Value("${web.upload-zipPath}")
    private String zipRepath ;

    @Value("${web.upload-zip}")
    private String zipAbPath ;


    @Override
    public ApiResult syncBooksData(Long userId)  throws  Exception{
        ApiResult booksResult= findAllByUserId(userId);
        if (booksResult.isSuccess()){
            String date =DateFormatUtils.format(new Date(),"yyyy.MM.dd") ;
            String zipPath =zipAbPath+userId+"."+date;
            if (new File(zipPath+".zip").exists()){
                long lastModifiedTime =  new File(zipPath+".zip").lastModified();
                //一小时之内不再打包直接进行
                if ((System.currentTimeMillis()-lastModifiedTime)/1000/60<=60){
                    System.err.println((System.currentTimeMillis()-lastModifiedTime)/1000/60);
                    return  ApiResult.ok(DownloadUtil.export(zipPath+".zip"));
                }
            }
            List<PersonalLearnBook> personalLearnBooks = (List<PersonalLearnBook>) booksResult.getData();
            List<String > booksIds =new ArrayList<>();
            for (PersonalLearnBook personalLearnBook : personalLearnBooks) {
                if(personalLearnBook.getLearnBook()!=null) {
                    booksIds.add(String.valueOf(personalLearnBook.getLearnBook().getId())) ;
                }
            }
            ApiResult pathesResult  = learnBookService.downlowdWordsZipWithinNoZip(booksIds) ;
            List<FilesZipBean>  pathes = (List<FilesZipBean>) pathesResult.getData();
            ResponseEntity<FileSystemResource> data = null;
            zipMachine.insertIntoZip(pathes,zipPath,"cibao.txt");
            data = DownloadUtil.export(zipPath+".zip");
            return  new ApiResult(SUCCESS,data) ;
        }


        return  new ApiResult(ResultMsg.OS_ERROR,null) ;
    }

    /**
     *根据学校获取学校uid集合
     * @param schoolList
     * @return
     */
    private List<String> getSchoolUid( List<School> schoolList){
        List<String> schoolUidList= new ArrayList<>();
        for (School school : schoolList) {
            schoolUidList.add(school.getUid());
        }
        return schoolUidList;
    }

    /**
     * 根据学校uid获取用户
     * @param schoolUidList
     * @return
     */
    private List<AppUser> getAppUsers(List<String> schoolUidList){
        List<AppUser> appUsers=new ArrayList<>();
        for (String schoolUid : schoolUidList) {
            ApiResult apiResult = appUserService.findAppUserBySchoolId(schoolUid);
            List<AppUser> appUserList=(List<AppUser>)apiResult.getData();
            appUsers.addAll(appUserList);
        }
        return appUsers;
    }


    public List<PersonalLearnBook> personalLearnBookList(Long userId) {
        List<PersonalLearnBook> list = new ArrayList<>();
        Page<PersonalLearnBook> page = findPersonalLearnBooks(userId,null,null,null, 1,Integer.MAX_VALUE);
        if(page != null){
            for (PersonalLearnBook pl:page.getContent()) {
                ApiResult apiResult = personalTestForBookService.findTestByUserIdAndBookId(userId, pl.getLearnBook().getId());
                if (apiResult.isSuccess()) {
                    List<PersonalTestForBook> personalTestForBooks = (List<PersonalTestForBook>) apiResult.getData();
                    if(personalTestForBooks.size() > 0 ){
                        for (PersonalTestForBook ptfb:personalTestForBooks) {
                            //学前测试成绩  学后测试成绩
                            if(ptfb.getIsPreLearnTest() == 1){
                                pl.setLearnBeforeScore(ptfb.getScore().toString());
                                break;
                            }
                        }
                        for (PersonalTestForBook ptfb:personalTestForBooks) {
                            if(ptfb.getIsPreLearnTest() == 0){
                                if(ptfb.getScore() != null && ptfb.getScore() != 0){
                                    pl.setLearnAfterScore(ptfb.getScore().toString());
                                    break;
                                }
                            }
                        }
                    }
                }
                if(StringUtils.isEmpty(pl.getLearnAfterScore())){
                    pl.setLearnAfterScore("0");
                }
                if(StringUtils.isEmpty(pl.getLearnBeforeScore())){
                    pl.setLearnBeforeScore("0");
                }
                Integer BeforeScore = Integer.parseInt(pl.getLearnBeforeScore());
                Integer AfterScore = Integer.parseInt(pl.getLearnAfterScore());
                Integer ExtractPoints = AfterScore-BeforeScore;
                if(ExtractPoints<0){
                    pl.setLearnAfterScore("异常");
                    pl.setExtractPoints("异常");
                }else{
                    pl.setExtractPoints(String.valueOf(ExtractPoints));
                }
                list.add(pl);
            }
        }

        return list;
    }
}
