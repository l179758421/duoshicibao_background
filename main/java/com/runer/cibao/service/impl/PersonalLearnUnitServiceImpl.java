package com.runer.cibao.service.impl;

import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.PersonalLearnUnitDao;
import com.runer.cibao.domain.BookUnit;
import com.runer.cibao.domain.PersonalLearnBook;
import com.runer.cibao.domain.PersonalLearnUnit;
import com.runer.cibao.domain.repository.PersonalLearnUnitReposiory;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import com.runer.cibao.util.NormalUtil;
import com.runer.cibao.util.machine.DateMachine;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.runer.cibao.exception.ResultMsg.NOT_FOUND;
import static com.runer.cibao.exception.ResultMsg.SUCCESS;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/26
 **/
@Service
public class PersonalLearnUnitServiceImpl extends BaseServiceImp<PersonalLearnUnit,PersonalLearnUnitReposiory> implements PersonalLearnUnitService {


    @Autowired
    PersonalLearnBookService personalLearnBookService ;


    @Autowired
    PersonalLearnUnitDao personalLearnUnitDao ;

    @Autowired
    BookUnitService bookUnitService ;

    @Autowired
    DateMachine dateMachine;

    @Autowired
    NewBookWordService newBookWordService ;


    @Autowired
    ReviewTestService reviewTestService ;





    @Override
    public Page<PersonalLearnUnit> findUnits(Long userId ,Long bookId ,Long personalLearnBookId
            ,Integer isPassed,Integer isCurrentLearnedUnit ,Integer isFinished ,Integer reviewTestState
            ,Integer page ,Integer limit) {
        return personalLearnUnitDao.findPersonalLearnUnits(userId,bookId,personalLearnBookId,isPassed,isCurrentLearnedUnit,isFinished ,reviewTestState ,PageableUtil.basicPage(page,limit));
    }

    @Override
    public ApiResult addOrUpdatePersonalLearnUnit(Long id ,Long personalLearnBookId, Long bookUnitId, Integer score,
                                                  Integer isPassed, Integer isCurrentLearnedUnit) {

        PersonalLearnUnit personalLearnUnit =new PersonalLearnUnit();
        /**
         * 更新的状态下
         */

        if (id!=null) {
            ApiResult unitResult = findByIdWithApiResult(id);
            if (unitResult.getMsgCode()==SUCCESS.getMsgCode()){
                personalLearnUnit = (PersonalLearnUnit) unitResult.getData();
            }else{
                return  unitResult ;
            }
        }

        ApiResult  apiResultBook =personalLearnBookService.findByIdWithApiResult(personalLearnBookId);
        if (apiResultBook.getMsgCode()!=SUCCESS.getMsgCode()){
            return  apiResultBook ;
        }

        personalLearnUnit.setPersonalLearnBook((PersonalLearnBook) apiResultBook.getData());


        ApiResult apiResultUnit=bookUnitService.findByIdWithApiResult(bookUnitId);

        if (apiResultUnit.getMsgCode()!=ResultMsg.SUCCESS.getMsgCode()){
            return  apiResultUnit ;
        }

        personalLearnUnit.setBookUnit((BookUnit) apiResultUnit.getData());

        if (score!=null){
            personalLearnUnit.setScore(score);
        }
        if (isPassed!=null){
            personalLearnUnit.setIsPassed(isPassed);
        }
        if (isCurrentLearnedUnit!=null){
            personalLearnUnit.setIsCurrentLearnedUnit(isCurrentLearnedUnit);
        }
       personalLearnUnit =     r.saveAndFlush(personalLearnUnit) ;
        return  new ApiResult(SUCCESS,personalLearnUnit) ;
    }






    @Override
    public ApiResult generatePersonalLearnUnitByBook(Long personalLearnBookId) {


      ApiResult bookResult =   personalLearnBookService.findByIdWithApiResult(personalLearnBookId) ;

      if (bookResult.getMsgCode()!=ResultMsg.SUCCESS.getMsgCode()){
          return  bookResult ;
      }

        PersonalLearnBook personalLearnBook = (PersonalLearnBook) bookResult.getData();

        /**
         * 自动的创建个人的unit列表；
         */
            List<BookUnit> bookUnits = personalLearnBook.getLearnBook().getBookUnitList();

            List<PersonalLearnUnit> personalLearnUnits =new ArrayList<>() ;

            if (!ListUtils.isEmpty(bookUnits)){
                for (BookUnit bookUnit : bookUnits) {
                    PersonalLearnUnit personalLearnUnit =new PersonalLearnUnit();
                    personalLearnUnit.setPersonalLearnBook(personalLearnBook);
                    personalLearnUnit.setScore(0);
                    personalLearnUnit.setIsPassed(Config.NOT_PASSED);
                    personalLearnUnit.setIsCurrentLearnedUnit(0);
                    personalLearnUnit.setBookUnit(bookUnit);

                    personalLearnUnits.add(personalLearnUnit);
                }
                try {
                    saveOrUpdate(personalLearnUnits) ;
                } catch (SmartCommunityException e) {
                    e.printStackTrace();
                    return  new ApiResult(e.getResultMsg(),null) ;
                }
        }


        return  new ApiResult(ResultMsg.SUCCESS,personalLearnUnits) ;

    }

    @Override
    public ApiResult findByLearnBookId(Long learnBookId) {
        Page<PersonalLearnUnit> datas = findUnits(null, null, learnBookId, null,
                null, null, null,1, Integer.MAX_VALUE);;
        return  new ApiResult( SUCCESS,datas) ;
    }

    @Override
    public ApiResult findOneByUserIdAndUnitId(Long unitId  ,Long userId ,Long personlBookId) {
        List<PersonalLearnUnit> datas = personalLearnUnitDao.findByBookWord(unitId, userId, personlBookId);
        if (ListUtils.isEmpty(datas)){
            return  new ApiResult(NOT_FOUND,null);
        }
        return new ApiResult(datas.get(0),true);
    }


    @Override
    public Map<String, Object> numsInfo(Long wordId, Long unitId ) {

        long all =  personalLearnUnitDao.findALlCount(wordId,unitId);
        long currnet = personalLearnUnitDao.findCurrentUnitNum(wordId,unitId);
        long left =  personalLearnUnitDao.findLeftUNitNum(wordId,unitId);
        return NormalUtil.generateMapData(data -> {
            data.put(Config.all,all) ;
            data.put(Config.current,currnet);
            data.put(Config.left,left) ;
        });

    }

    @Override
    public List<PersonalLearnUnit> findByIds(Long appUser, String unitIds) {
        return personalLearnUnitDao.findByIds(appUser,unitIds);
    }

    /**
     * 查找需要强制复习的单元；
     * @param appUserId
     * @return
     */
    @Override
    public List<PersonalLearnUnit>  findReviewTestUnits(Long appUserId ){


        PersonalLearnBook personalLearnBook = (PersonalLearnBook) personalLearnBookService.getCurrentBook(appUserId).getData();
        if (personalLearnBook!=null){
            if (personalLearnBook.getLearnBook()==null){
                return  new ArrayList<>();
            }
            List<PersonalLearnUnit> units = personalLearnUnitDao.findPersonalLearnUnits(appUserId,
                    personalLearnBook.getLearnBook().getId(), null, null, null, Config.isFinished
                    , 15, PageableUtil.basicPage(1, Integer.MAX_VALUE)).getContent();
            List<PersonalLearnUnit> toReviewUnits =new ArrayList<>();
            for (PersonalLearnUnit unit : units) {
                if (NormalUtil.unitShouldReviewTest(unit,dateMachine)){
                    if (unit.getBookUnit()!=null&&unit.getBookUnit().getLearnBook()!=null) {
                        long reviewCount  =  reviewTestService.countReviewTestCount(appUserId,unit.getBookUnit().getId()) ;
                        unit.setCount((int) reviewCount);
                        unit.setBookId(unit.getBookUnit().getLearnBook().getId());
                        unit.setUnitId(unit.getId());
                        toReviewUnits.add(unit);
                    }
                }
            }
            return toReviewUnits ;

        }else{
            return  new ArrayList<>() ;
        }

    }

    /**
     * 更新复习测试的状态；
     * @param date
     * @param state
     * @param unitId
     * @param userId
     * @return
     */
    @Override
    public ApiResult updateUnitReviewState(String leftWords ,Date date, Integer state, Long unitId, Long userId) {
        ApiResult  unitResult =  findOneByUserIdAndUnitId(unitId,userId,null);
        if (unitResult.isFailed()){
            return  unitResult ;
        }
        PersonalLearnUnit unit = (PersonalLearnUnit) unitResult.getData();
        unit.setLastedReviewTestDate(date);
        unit.setReviewTestState(state);
        r.saveAndFlush(unit) ;

        //leftwords 将测试失败的单词加入生词库；
        if (!StringUtils.isEmpty(leftWords)) {
            newBookWordService.addNewBookWordsBacthNoLimit(userId, leftWords);
        }
        return  new ApiResult(SUCCESS,unit) ;
    }





}
