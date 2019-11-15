package com.runer.cibao.service.impl;

import com.alibaba.fastjson.JSON;
import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.*;
import com.runer.cibao.domain.person_word.UnitStateBean;
import com.runer.cibao.domain.person_word.UploadPersonalLearnDetail;
import com.runer.cibao.domain.person_word.WordLearnProgress;
import com.runer.cibao.domain.repository.WordLearnProgressRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import com.runer.cibao.util.machine.IdsMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;
import org.thymeleaf.util.StringUtils;

import java.util.*;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/17
 * 学习进度；==
 **/
@Service
public class WordLearnProgressServiceImpl extends BaseServiceImp<WordLearnProgress, WordLearnProgressRepository> implements WordLearnProgressService {

    @Autowired
    AppUserService appUserService ;
    @Autowired
    BookWordService bookWordService ;
    @Autowired
    WordLearnForPersonalService wordLearnForPersonalService ;
    @Autowired
    BookUnitService bookUnitService ;
    @Autowired
    IdsMachine idsMachine ;
    @Autowired
    PersonalLearnUnitService personalLearnUnitService;
    @Autowired
    NewBookWordService newBookWordService ;


    @Override
    public WordLearnProgress getOne(Long appUserId, Long unitId) {
        List<WordLearnProgress> exists = r.findWordLearnProgressesByUserIdAndUnitId(appUserId, unitId);
        if (!ListUtils.isEmpty(r.findWordLearnProgressesByUserIdAndUnitId(appUserId,unitId))){
            if (exists.size()>1){
                r.delete(exists.get(1));
            }
            return exists.get(0);
        }
        return null;
    }
//    @Override
//    public ApiResult setCurrentLearnUnit(Long userId, Long bookId, Long unitId, String leftIds) {
//       ApiResult one =   wordLearnForPersonalService.findOne(userId,bookId) ;
//       if (one.isFailed()){
//           return  one ;
//       }
//       WordLearnForPersonal wordLearnForPersonal = (WordLearnForPersonal) one.getData();
//       wordLearnForPersonal.setLeftUnitIds(leftIds);
//       wordLearnForPersonal.setCurrentUnitId(unitId);
//        try {
//            wordLearnForPersonalService.saveOrUpdate(wordLearnForPersonal) ;
//        } catch (SmartCommunityException e) {
//            e.printStackTrace();
//        }
//        return new ApiResult(ResultMsg.SUCCESS,wordLearnForPersonal);
//    }
    @Override
    public ApiResult getPerosnalLearnDetail(Long userId, Long bookId) {
        return wordLearnForPersonalService.findOne(userId,bookId);
    }
    @Autowired
    PersonalLearnBookService personalLearnBookService ;

    @Override
    public ApiResult setCurrentLearnBook(Long userId, Long bookId ) {
     PersonalLearnBook personalLearnBook = (PersonalLearnBook) personalLearnBookService.getSinglePersonalLearnBook(userId, bookId).getData();
     if (personalLearnBook==null){
         return  new ApiResult("课本不存在");
     }
    return  personalLearnBookService.setLearnBookToCurrent(personalLearnBook.getId());
    }
    @Override
    public ApiResult getCurrentLearnBook(Long userId) {
        return personalLearnBookService.getCurrentBook(userId);
    }


    /**
     * 课本学习完成的时候必须要进行更新是否完成了；
     * @param userId
     * @param bookId
     * @param currentUnitId
     * @param allUnitIds
     * @param leftUnitIds
     * @param isFinished
     * @return todo ;
     */
    @Override
    public ApiResult updatePersonalLearnBookDetail( Long userId, Long bookId,
                                                    Long currentUnitId, String allUnitIds,
                                                    String leftUnitIds ,Integer isFinished) {
        if (currentUnitId==null){
            return  new ApiResult("当前单元的id不能为空");
        }
        if (isFinished==null){
            isFinished = Config.notFinished ;
        }
        ApiResult oneReuslt =  wordLearnForPersonalService.findOne(userId,bookId) ;
        if (oneReuslt.isFailed()){
            return  oneReuslt ;
        }

       WordLearnForPersonal wordLearnForPersonal = (WordLearnForPersonal) oneReuslt.getData();
       wordLearnForPersonal.setCurrentUnitId(currentUnitId);
       wordLearnForPersonal.setLeftUnitIds(leftUnitIds);
       wordLearnForPersonal.setAllUnitIds(allUnitIds);
       wordLearnForPersonal.setIsPassed(isFinished);

        try {
            wordLearnForPersonalService.saveOrUpdate(wordLearnForPersonal);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        return new ApiResult(ResultMsg.SUCCESS,wordLearnForPersonal);
    }

    @Override
    public ApiResult getAllUnitWords(Long userId, Long bookId, Long unitId) {
        WordLearnProgress one = generateOneProgress(userId, unitId);
        return new ApiResult(ResultMsg.SUCCESS,one);
    }

    @Override
    public ApiResult updateUnitWordsLeft(Long userId, Long bookId, Long unitId, String ids) {
        WordLearnProgress wordLearnProgress =generateOneProgress(userId,unitId);
        wordLearnProgress.setAllLeftWords(ids);
        r.saveAndFlush(wordLearnProgress) ;
        return new ApiResult(ResultMsg.SUCCESS,wordLearnProgress);
    }

    @Override
    public ApiResult updateTten2news(Long userId, Long unitId, String ids) {
        WordLearnProgress one =generateOneProgress(userId,unitId) ;
        if (one!=null){
            one.setTennewsWords(ids);
            r.saveAndFlush(one) ;
        }else{
            return   new ApiResult("未找到相对应的数据");
        }
        return new ApiResult(ResultMsg.SUCCESS,one);
    }

    @Override
    public ApiResult updateKnowWords(Long userId, Long unitId, String ids) {
        WordLearnProgress one =generateOneProgress(userId,unitId) ;
        if (one==null){
            return  new ApiResult("未找到对应的数据");
        }else{
            one.setKnowsWords(ids);
            r.saveAndFlush(one) ;
            return  new ApiResult(ResultMsg.SUCCESS,one) ;
        }

    }



    @Override
    public ApiResult updateStrongWords(Long userId, Long unitId, String ids) {
        WordLearnProgress one =generateOneProgress(userId,unitId) ;
        if (one==null){
            return  new ApiResult("未找到对应的数据");
        }else{
            one.setStrongWords(ids);
            r.saveAndFlush(one) ;
            return  new ApiResult(ResultMsg.SUCCESS,one) ;
        }
    }



    @Override
    public ApiResult updateresultWords(Long userId, Long unitId ,String ids) {
        WordLearnProgress one =generateOneProgress(userId,unitId) ;
        if (one==null){
            return  new ApiResult("未找到对应的数据");
        }else{
            one.setResultCacheWords(ids);
            r.saveAndFlush(one) ;
            return  new ApiResult(ResultMsg.SUCCESS,one) ;
        }
    }

    @Override
    public ApiResult resultToHistory(Long userId, Long unitId, Integer isSuccess ,String leftIds) {
        ApiResult  detailResult  =   getUnitProgeressDetail(userId,unitId);
        if (detailResult.isFailed()){
            return  detailResult ;
        }
        WordLearnProgress wordLearnProgress = (WordLearnProgress) detailResult.getData();
        if (isSuccess==1){
            if (StringUtils.isEmpty(leftIds)){
                return  new ApiResult("失败的时候需要设置剩余ids");
            }
            wordLearnProgress.setAllLeftWords(leftIds);
            wordLearnProgress.setResultCacheHistory(wordLearnProgress.getResultCacheWords());
            wordLearnProgress.setResultCacheWords("");
            r.saveAndFlush(wordLearnProgress);

        }else {
            wordLearnProgress.setResultCacheHistory(wordLearnProgress.getResultCacheWords());
            wordLearnProgress.setResultCacheWords("");
            r.saveAndFlush(wordLearnProgress) ;
        }
        return new ApiResult(ResultMsg.SUCCESS,wordLearnProgress);
    }

    @Override
    public ApiResult getUnitProgeressDetail(Long userId, Long unitId) {
        return getNormalOne(userId,unitId);
    }

    @Override
    public ApiResult getOnePersonAllLearnDetail(Long userId) {

        List<PersonalLearnBook> allBooks = personalLearnBookService.findPersonalLearnBooks(userId, null, null, null, 1, Integer.MAX_VALUE).getContent();

        List<Long> bookIds =new ArrayList<>();
        for (PersonalLearnBook allBook : allBooks){
            if (allBook.getLearnBook()!=null){
                bookIds.add(allBook.getLearnBook().getId()) ;
            }
        }
        List<Map<String,Object>> results =new ArrayList<>() ;
            for (Long bookId : bookIds) {
                Map<String,Object> data =new HashMap<>() ;
                WordLearnForPersonal one = (WordLearnForPersonal) wordLearnForPersonalService.findOne(userId, bookId).getData();
                if (one!=null) {
                        Long  currentUnitId = one.getCurrentUnitId();
                        if (currentUnitId!=null&&currentUnitId!=0){
                        PersonalLearnUnit unit = (PersonalLearnUnit) personalLearnUnitService.findOneByUserIdAndUnitId(currentUnitId, userId, null).getData();
                        if (unit!=null&&unit.getIsFinished()!= null&& 1==unit.getIsFinished()) {
                            one.setCurrentFinished(true);
                        }
                        }
                    data.put("book", one);
                    List<WordLearnProgress> progresses = new ArrayList<>();
                    if (!StringUtils.isEmpty(one.getAllUnitIds())) {
                        for (String s : one.getAllUnitIds().split(",")) {
                            WordLearnProgress progress = generateOneProgress(userId, Long.valueOf(s));
                            progresses.add(progress);
                        }
                    }
                    data.put("units", progresses);
                    results.add(data);
                }
            }

        return new ApiResult(results,true);
    }


    /**
     * 检测完成以后的操作；
     * @param appUserId
     * @param bookId
     * @param unitId
     * @param ids
     * @param wordId
     * @return
     */
    @Override
    public ApiResult insertLearnedWords(Long appUserId, Long bookId, Long unitId, String ids ,Long wordId) {
      ApiResult bookResult =   personalLearnBookService.getSinglePersonalLearnBook(appUserId,bookId);
      if (bookResult.isFailed()){
          return  bookResult ;
      }

      PersonalLearnBook learnBook = (PersonalLearnBook) bookResult.getData();
      List<Long> exists = idsMachine.deparseIds(learnBook.getLearnedWords()) ;
      List<Long> insertIds =new ArrayList<>();
      if (!StringUtils.isEmpty(ids)){
        insertIds =  idsMachine.deparseIds(ids);
      }
     //  List<Long> results =(List<Long>)CollectionUtils.union(exists, insertIds);
        List<Long> results =new ArrayList<>();
       Collections.sort(results);
       String resultIds =idsMachine.toIds(results);
       learnBook.setLearnedWords(resultIds);
       learnBook.setCurrentWordNum((long) results.size());
       if (unitId!=null){
         ApiResult unitResult =  bookUnitService.findByIdWithApiResult(unitId);
         if (unitResult.isFailed()){
             return  unitResult ;
         }
         BookUnit unit = (BookUnit) unitResult.getData();
         learnBook.setCurrentUnitName(unit.getName());
         learnBook.setUnitId(unitId);
        }
        if(wordId!=null){
            ApiResult wordResult =   bookWordService.findByIdWithApiResult(wordId);
            if (wordResult.isSuccess()) {
                BookWord bookWord = (BookWord) wordResult.getData();
                learnBook.setCurrentWord(wordId);
                learnBook.setCurrnetWordname(bookWord.getWordName());
            }
        }
        try {
            personalLearnBookService.saveOrUpdate(learnBook);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        return new ApiResult(ResultMsg.SUCCESS,learnBook);
    }

    /**
     * 更新单元的学习状态；
     * @param appUserId
     * @param bookId
     * @param unitId
     * @param state
     * @return
     */
    @Override
    public ApiResult updateUnitLearnState(String leftWords ,Long appUserId, Long bookId, Long unitId, Integer state,Integer isLastUnit ,Integer wordNum,Integer nextLearnStage, String stageIds,String lastWordIds) {
        if (state==null){
            state = Config.notFinished ;
        }
        ApiResult userResult =appUserService.findByIdWithApiResult(appUserId);

        AppUser appUser ;

        if (userResult.isSuccess()){
            appUser = (AppUser) userResult.getData();
        }else{
            return  new ApiResult("用户不存在");
        }
        ApiResult apiResult =  personalLearnUnitService.findOneByUserIdAndUnitId(unitId,appUserId,null);
        if (apiResult.isFailed()){
            return  new ApiResult("单元未找到");
        }
        PersonalLearnUnit personalLearnUnit = (PersonalLearnUnit) apiResult.getData();

        //学习成功以后不再进行学习；
        if (personalLearnUnit.getIsFinished()== Config.isFinished){
            return  new ApiResult(ResultMsg.SUCCESS,null) ;
        }
       ApiResult bookResult  =personalLearnBookService.findByIdWithApiResult(personalLearnUnit.getPersonalLearnBook().getId()) ;
       PersonalLearnBook personalLearnBook =null ;
       if (bookResult.isSuccess()){
           personalLearnBook = (PersonalLearnBook) bookResult.getData();
       }else{
           return  new ApiResult("当前数据错误");
       }

        personalLearnUnit.setIsFinished(state);
        personalLearnUnit.setIsCurrentLearnedUnit(Config.IS_CURRENT);
        personalLearnBook.setUnitId(personalLearnUnit.getBookUnit().getId());
        personalLearnBook.setCurrentUnitName(personalLearnUnit.getBookUnit().getName());

        //0失败1成功2开始学习
        switch (state){
            //
            case 0:
                //todo
                personalLearnUnit.setState(state);
                personalLearnBook.setCurrentUnitFinished(false);
                break;
            case   1:
                //最后一个单元的情况下；
                if (1==isLastUnit){
                    //学习完成
                     personalLearnBook.setIsFinished(Config.isFinished);
                     personalLearnBook.setCurrentWordNum(personalLearnBook.getTotalWordNum());
                }else {
                     //当前学习的单词数量；
                      personalLearnBook.setCurrentWordNum(personalLearnBook.getCurrentWordNum() + wordNum);
                }
                newBookWordService.addNewBookWordsBacth(appUserId,leftWords);
                personalLearnBook.setLearnedWords(leftWords);
                personalLearnBook.setCurrentUnitFinished(true);
                personalLearnUnit.setIsFinished(Config.isFinished);
                personalLearnUnit.setState(state);
                personalLearnUnit.setFinishedDate(new Date());
                //更新个人学习的单词；
                long personWordNUm =appUser.getWordNum() ;
                long nowPersonwordNum =personWordNUm+wordNum ;
                appUser.setWordNum(nowPersonwordNum);
                newBookWordService.addNewBookWordsBacthNoLimit(appUserId,leftWords);
                try {
                    appUserService.saveOrUpdate(appUser) ;
                } catch (SmartCommunityException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                personalLearnBook.setCurrentUnitFinished(false);
                break;
        }

        //更新数据
        try {
            personalLearnBookService.update(personalLearnBook) ;
            personalLearnUnitService.update(personalLearnUnit) ;
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        return new ApiResult(ResultMsg.SUCCESS,personalLearnUnit);
    }

    private ApiResult getNormalOne(Long userId ,Long unitId ){
        WordLearnProgress one =generateOneProgress(userId,unitId) ;
        if (one==null){
            return  new ApiResult("未找到对应的数据");
        }else{
            return  new ApiResult(ResultMsg.SUCCESS,one) ;
        }
    }
    /**
     * 创建新的单元学习记录；
     * @param appUserId
     * @param unitId
     * @return
     */
    private WordLearnProgress generateOneProgress(Long appUserId , Long unitId ){
        WordLearnProgress wordLearnProgress =getOne(appUserId,unitId);
        if (wordLearnProgress==null){
            wordLearnProgress =new WordLearnProgress();
            ApiResult userResult =appUserService.findByIdWithApiResult(appUserId) ;
            if (userResult.isFailed()){
                return  null ;
            }
            wordLearnProgress.setUserId(appUserId);
            ApiResult unitResult = bookUnitService.findByIdWithApiResult(unitId) ;
            if (unitResult.isFailed()){
                return  null ;
            }
            wordLearnProgress.setUnitId(unitId);
            r.saveAndFlush(wordLearnProgress) ;
        }
        return  wordLearnProgress ;

    }


    /**
     * 获取某人的学习状态(单元)
     */
    public ApiResult getCertainLearningState(String appUserId , String unitId){
        ApiResult userResult =appUserService.findByIdWithApiResult(Long.valueOf(appUserId));
        if (userResult.isFailed()){
            return  new ApiResult("用户不存在");
        }
        ApiResult apiResult =  personalLearnUnitService.findOneByUserIdAndUnitId(Long.valueOf(unitId),Long.valueOf(appUserId),null);
        PersonalLearnUnit personalLearnUnit = (PersonalLearnUnit) apiResult.getData();

        return  new ApiResult(ResultMsg.SUCCESS,personalLearnUnit);
    }


    /**
     *
     * 必要说明 ----  只上传unitstates ;的情况下是只更新当前学习阶段，即：
     *          ||   这种情况下为：1 初步认知新单词的完成； 2 词汇强化的完成；  3 单词的效果检测失败；
     *          ||
     *          ||   上传learnInfo的情况为单词效果检测成功的情况下；
     * @param learnInfo
     * @param unitstates
     * @return
     * @throws Exception
     */
    @Override
    public ApiResult syncCurrentLearnInfo(String learnInfo, String unitstates)  throws Exception {


        System.err.println(learnInfo);
        System.err.println(unitstates);


        if (StringUtils.isEmpty(learnInfo)&&StringUtils.isEmpty(unitstates)){
            return  new ApiResult("不能同时为空");
        }

        UploadPersonalLearnDetail learnDetail=null;
        UnitStateBean stateBean =null;
        if (!StringUtils.isEmpty(learnInfo)){
            learnDetail =JSON.parseObject(learnInfo, UploadPersonalLearnDetail.class) ;
        }
        if (!StringUtils.isEmpty(unitstates))
        {
            stateBean =JSON.parseObject(unitstates, UnitStateBean.class) ;
        }
        //只更新状态的情况；
        if(stateBean!=null&&learnDetail==null){
            ApiResult result = personalLearnUnitService.findOneByUserIdAndUnitId(stateBean.getUnitId(),stateBean.getUserId(),null);
            PersonalLearnUnit personalLearnUnit = (PersonalLearnUnit) result.getData();
            personalLearnUnit.setUnitState(stateBean);
            //更新状态；
            personalLearnUnitService.saveOrUpdate(personalLearnUnit) ;
            return  ApiResult.ok();
        }
        /**
         * 更新课本的学习状态；一些ids的储存
         */
        if (learnDetail!=null) {
            ApiResult personalApi = updatePersonalLearnBookDetail(learnDetail.getUserId(), learnDetail.getBookId(), learnDetail.getCurrentUnitId()
                    , learnDetail.getAllUnitIds(), learnDetail.getLeftUnitIDs(), learnDetail.isFinished() == true ? Config.isFinished : Config.notFinished);
            if (personalApi.isFailed()){
                return  personalApi ;
            }
        }
        /**
         *效果检测成功的情况下；
         */
        if (learnDetail!=null&&stateBean!=null){

            List<PersonalLearnBook> personalLearnBooks = personalLearnBookService.findPersonalLearnBooks(learnDetail.getUserId(),
                    learnDetail.getBookId(), null, null, 1, 10).getContent();

            PersonalLearnBook learnBook;

            if (personalLearnBooks!=null&&!personalLearnBooks.isEmpty()){
                 learnBook =personalLearnBooks.get(0) ;
            }else{
                return  new ApiResult("未找到课本");
            }

            //更新当前单元的状态
            if (learnDetail.getCurrentUnitId()!=0){

                   ApiResult result = personalLearnUnitService.findOneByUserIdAndUnitId(learnDetail.getCurrentUnitId(),learnDetail.getUserId(),null);
                   PersonalLearnUnit personalLearnUnit = (PersonalLearnUnit) result.getData();

                   //正在学习的情况下；
                   if (learnDetail.isCurrentLearning()){
                        //设置个人单元的状态；
                        personalLearnUnit.setState(2);
                        personalLearnUnit.setIsCurrentLearnedUnit(Config.IS_CURRENT);
                        personalLearnUnit.setUnitState(stateBean);

                        //设置个人课本的状态；
                        learnBook.setCurrentUnitFinished(false);
                        learnBook.setUnitId(learnDetail.getCurrentUnitId());
                        learnBook.setCurrentUnitName(personalLearnUnit.getBookUnit().getName());

                        //当前的单元学习已经完成了的情况，根据和app端进行协调-》当效果检测成功以后，若是设置当前单元已经完成的情况，
                        //那么此课本已经学习完毕；

                }else{
                       //state == 1 为完成的状态
                       personalLearnUnit.setFinishedState();
                       learnBook.setIsFinished(Config.isFinished);
                       learnBook.setCurrentUnitFinished(true);
                       learnBook.setCurrentWordNum(learnBook.getTotalWordNum());
               }

                 int wrongNum = idsMachine.deparseIds(stateBean.getWrongWords()).size();
                  /**
                  * 加入生词本
                  */
                 newBookWordService.addNewBookWordsBacthNoLimit(stateBean.getUserId(),stateBean.getWrongWords());
                 int learnNum =Math.abs(idsMachine.deparseIds(stateBean.getAllWords()).size()-wrongNum);
                /**
                 * 保存用户的学习的单词个数；
                 */
                 AppUser appUser =appUserService.findById(stateBean.getUserId());
                 appUser.setWordNum(appUser.getWordNum()+learnNum);
                 appUserService.saveOrUpdate(appUser) ;


                personalLearnUnitService.saveOrUpdate(personalLearnUnit);
                personalLearnBookService.saveOrUpdate(learnBook);

            }

            //根据与app端进行协调，若存在preUnitId,那么，preUnitId为已经完成的状态；
            if (learnDetail.getPreUnitId()!=0){
                ApiResult result = personalLearnUnitService.findOneByUserIdAndUnitId(learnDetail.getPreUnitId(),learnDetail.getUserId(),null);
                PersonalLearnUnit personalLearnUnit = (PersonalLearnUnit) result.getData();
                //设置单元完成的状态；
                personalLearnUnit.setFinishedState();
                personalLearnUnitService.saveOrUpdate(personalLearnUnit);
                int learnedWordsNum=idsMachine.deparseIds(personalLearnUnit.getAllWords()).size();
                if (learnedWordsNum==0){
                    learnedWordsNum =30 ;
                }
                if (learnedWordsNum+learnBook.getCurrentWordNum()>learnBook.getTotalWordNum()){
                    learnBook.setCurrentWordNum(learnBook.getTotalWordNum());
                }else{
                    learnBook.setCurrentWordNum(learnedWordsNum+learnBook.getCurrentWordNum());
                }
                personalLearnBookService.saveOrUpdate(learnBook);
            }
            return  ApiResult.ok() ;
        }

        return ApiResult.ok();
    }


}
