package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.person_word.WordLearnProgress;
import com.runer.cibao.domain.repository.WordLearnProgressRepository;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/17
 **/
public interface  WordLearnProgressService extends BaseService<WordLearnProgress,WordLearnProgressRepository> {

    WordLearnProgress getOne(Long appUserId, Long unitId);


//    /**
//     * 设置当前学习的单元；
//     * @param userId
//     * @param bookId
//     * @param unitId
//     * @return
//     */
//    ApiResult setCurrentLearnUnit(Long userId , Long bookId ,Long unitId ,String leftIds );


    ApiResult getPerosnalLearnDetail(Long userId, Long bookId) ;

    /**
     * 设置当前学习的课本；
     * @param userId
     * @param bookId
     * @return
     */
    ApiResult setCurrentLearnBook(Long userId, Long bookId);

    ApiResult getCurrentLearnBook(Long userId) ;


    /**
     *
     * @return
     *
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     Long id ;

    /**
    * 用户的ID
    * Long appUserId ;
    /**
     * bookId；
    Long bookId ;
    /**
    *当前学习的单元；
    Long currentUnitId ;
    String allUnitIds ;
    String leftUnitIds ;
    */
    ApiResult updatePersonalLearnBookDetail(Long userId, Long bookId, Long currentUnitId,
                                            String allUnitIds, String leftUnitIds, Integer isFinished);
    /**
     *
     * @param userId
     * @param bookId
     * @param unitId
     * @return
     */
    ApiResult getAllUnitWords(Long userId, Long bookId, Long unitId);


    ApiResult updateUnitWordsLeft(Long userId, Long bookId, Long unitId, String ids) ;
    /**
     *  @Id
     *     @GeneratedValue(strategy = GenerationType.AUTO)
     *     Long id ;
     *
     *     Long unitId ;
     *
     *      Long userId ;
     *
     *     String allWords;
     *
     *     String allLeftWords;
     *
     *     String tennewsWords ;
     *
     *     String knowsWords ;
     *
     *     String strongWords ;
     *
     *     String resultCacheWords;
     *
     *     String resultCacheHistory ;
     * @return
     */
//    ApiResult updateWordLearnProgress(Long unitId ,Long userId ,String allWords ,String allLeftWords ,String tenIds, String knowIds ,
//                                      String strongIds ,String resultCacheids,String historyIds);
    /**
     * 新学池子；
     * @param userId
     * @param unitId
     * @param ids
     * @return
     */
    ApiResult updateTten2news(Long userId, Long unitId, String ids) ;
    //获得十个去学习的当前；

    /**
     * 认知池
     * @param userId
     * @param unitId
     * @param ids
     * @return
     */
    ApiResult updateKnowWords(Long userId, Long unitId, String ids);


    /**
     * 强化池
     * @param userId
     * @param unitId
     * @param ids
     * @return
     */
    ApiResult  updateStrongWords(Long userId, Long unitId, String ids) ;


    /**
     * 检测池 ；
     * @param userId
     * @param unitId
     * @return
     */
    ApiResult updateresultWords(Long userId, Long unitId, String ids);


    /**
     * 将效果检测转化城历史；《----》
     * @param userId
     * @param unitId
     * @param isSuccess
     * @return
     */
    ApiResult resultToHistory(Long userId, Long unitId, Integer isSuccess, String leftIds);

    /**
     * 获得祥情；
     * @param userId
     * @param unitId
     * @return
     */

    ApiResult getUnitProgeressDetail(Long userId, Long unitId);


    ApiResult getOnePersonAllLearnDetail(Long userId);


    /**
     * 保存学习的单词；
     * @param appUserId
     * @param bookId
     * @param unitId
     * @param ids
     * @return
     */
    ApiResult insertLearnedWords(Long appUserId, Long bookId, Long unitId, String ids, Long wordId);

    /**
     * 更新单元的学习状态；
     * @param 剩余的单词
     * @param appUserId
     * @param bookId
     * @param unitId
     * @param state
     * @return
     */
    ApiResult updateUnitLearnState(String leftWords, Long appUserId, Long bookId, Long unitId, Integer state, Integer isLastUnit, Integer wordNum, Integer nextLearnStage, String stageIds, String lastWordIds);


    /**
     * 获取某人的学习状态(单元)
     */
    ApiResult getCertainLearningState(String appUserId, String unitId);


    /**
     * 上传个人的学习详情
     * @param unitIfno
     * @param learnInfo
     * @param unitstates
     * @return
     */
    ApiResult syncCurrentLearnInfo(String learnInfo, String unitstates) throws  Exception;





}
