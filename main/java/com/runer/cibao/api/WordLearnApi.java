package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.PersonalLearnUnit;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/17
 **/
@RestController
@Api(value = "单词学习相关的Api",description = "单词学习相关的Api")
@RequestMapping(value = "api/userwordLearn")
public class WordLearnApi {

    @Autowired
    WordLearnService wordLearnService ;

    @Autowired
    WordLearnProgressService wordLearnProgressService ;

    @Autowired
    PersonalLearnInfoService personalLearnInfoService;

    @Autowired
    LearnTimeService learnTimeService ;

    @Autowired
    PersonalLearnUnitService personalLearnUnitService ;

    @ApiOperation(value = "添加学习的单词",notes = "添加学习的单词")
    @RequestMapping(value = "addLearnWord",method = RequestMethod.POST)
    public ApiResult addLearnWord(Long appUserId , Long  wordId , Long unitId , String  date , Integer time, Integer state ,Integer isSuccess){
        Date createDate = null ;
        if (!StringUtils.isEmpty(date)){
            try {
                createDate =   DateUtils.parseDate(date,"yyyy-MM-dd hh:mm:ss") ;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        try {
            return  wordLearnService.addWordLearn(appUserId,wordId,createDate,time,unitId,state ,isSuccess) ;
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(ResultMsg.OS_ERROR,null);
        }
    }

    @ApiOperation(value = "批量添加学习的单词",notes = "批量添加学习的单词")
    @RequestMapping(value = "batchAddlearnWord",method = RequestMethod.POST)
    public ApiResult addLearnWord(Long appUserId , Long unitId ,  String   words  , String   date , String  times
             , String  states ,String isSuccesses){
        Date createDate = null ;
        if (!StringUtils.isEmpty(date)){
            try {
                createDate =   DateUtils.parseDate(date,"yyyy-MM-dd hh:mm:ss") ;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        try {
            return    wordLearnService.addWordLearns(appUserId,words,createDate,times,unitId,states ,isSuccesses);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(ResultMsg.OS_ERROR,null);
        }
    }


    @ApiOperation(value = "保存当前的bookId",notes = "保存当前的bookId")
    @RequestMapping(value = "setCurrentBook",method = RequestMethod.POST)
    public  ApiResult setCurrentBook(Long appUserId  , Long bookId   ){
        return   wordLearnProgressService.setCurrentLearnBook(appUserId,bookId) ;
    }

    @ApiOperation(value = "获得课本的学习情况",notes = "获得课本的学习情况")
    @RequestMapping(value = "getPersonalLearnDetail",method = RequestMethod.POST)
    public  ApiResult getAllBookUnitIds(Long appUserId  , Long bookId   ){
        return   wordLearnProgressService.getPerosnalLearnDetail(appUserId,bookId) ;
    }


    /**
     * @param
     * @param appUserId
     * @param bookId
     * @param currentUnitId
     * @param allUnitIds
     * @param leftUnitIDs
     * @param isFinished
     * @return
     */
    @ApiOperation(value = "更新课本的学习情况",notes = "获得课本的学习情况")
    @RequestMapping(value = "updatePersoanlLearnDetail",method = RequestMethod.POST)
    public  ApiResult updatePersoanlLearnDetail( Long appUserId  , Long bookId  ,
                                                Long currentUnitId ,String allUnitIds ,String leftUnitIDs ,Integer isFinished  ){
        return   wordLearnProgressService.updatePersonalLearnBookDetail( appUserId,bookId,currentUnitId,allUnitIds,leftUnitIDs,isFinished) ;
    }


    @ApiOperation(value = "获得个人学习的详情---所有",notes = "获得个人学习的详情")
    @RequestMapping(value = "getPersonalLearnDetailAll",method = RequestMethod.POST)
    public  ApiResult getPersonalLearnDetailAll(Long appUserId ){
        if (appUserId==null){
            return  new ApiResult("用户id为空");
        }
        return  wordLearnProgressService.getOnePersonAllLearnDetail(appUserId) ;
    }
    /**
     *
     * @param appUserId
     * @param unitId
     * @param state
     * @return
     */
    @ApiOperation(value = "更新学习的unit",notes = "更新学习的unit/state:0失败1成功2开始学习 \n isLasteUnit:0不是1是 \nwordNum 当前单元的单词个数")
    @RequestMapping(value = "updatePersonalLearnUnitState",method = RequestMethod.POST)
    public  ApiResult updatePersonalLearnUnitState(String  leftWords ,Long appUserId ,Long bookId , Long unitId ,Integer state ,Integer isLastUnit,Integer wordNum,Integer nextLearnStage, String stageIds ,String lastWordIds){
        if (appUserId==null){
            return  new ApiResult("用户id为空");
        }
        if(state==null){
            return  new ApiResult("state不能为空");
        }
        if (isLastUnit==null){
            return  new ApiResult("请判断是不是最后一个单元");
        }
        if (wordNum==null){
            return  new ApiResult("请输入当前学习的个数");
        }
        return  wordLearnProgressService.updateUnitLearnState(leftWords,appUserId,bookId,unitId,state ,isLastUnit ,wordNum,nextLearnStage,stageIds,lastWordIds) ;
    }


    @ApiOperation(value = "获取某人的学习状态",notes = "获取某人的学习状态")
    @RequestMapping(value = "getCertainLearningState",method = RequestMethod.POST)
    public ApiResult getCertainLearningState(String appUserId , String unitId ){
        if(StringUtils.isEmpty(appUserId)){
            return new ApiResult("用户id不能为空");
        }
        if(StringUtils.isEmpty(unitId)){
            return new ApiResult("单元id不能为空");
        }
        return wordLearnProgressService.getCertainLearningState(appUserId,unitId);
    }



    @ApiOperation(value = "获得个人每天学习情况",notes = "获得个人每天学习情况")
    @RequestMapping(value = "getPersonalLearnInfo",method = RequestMethod.POST)
    public ApiResult getPersonalLearnInfo(Long userId){
        return personalLearnInfoService.generateOneLearnInfo(userId,new Date());
    }


    @ApiOperation(value = "上传个人有效的学习时间",notes = "上传个人有效的学习时间")
    @RequestMapping(value = "uploadLearnTime",method = RequestMethod.POST)
    public ApiResult uploadLearnTime(Long userId ,Long time ){
        return  learnTimeService.uploadLearnTime(time,userId,new Date()) ;
    }




    @ApiOperation(value = "获得个人有效的学习时间",notes = "获得个人有效的学习时间；；yyyy-MM-dd hh:mm:ss")
    @RequestMapping(value = "getLearnTime",method = RequestMethod.POST)
    public ApiResult getLearnTime(Long userId ,String date )  {
        Date reusltDate =null ;
        if (!StringUtils.isEmpty(date)){
            try {
                reusltDate =  DateUtils.parseDate(date,"yyyy-MM-dd hh:mm:ss");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (reusltDate==null){
            reusltDate =new Date() ;
        }
        return  learnTimeService.getUploadLearnTime(userId,reusltDate) ;
    }


    @ApiOperation(value = "获得需要强制复习的单元列表",notes = "获得需要强制复习的单元列表")
    @RequestMapping(value = "getMustReviewList",method = RequestMethod.POST)
    public ApiResult getMustReviewList(Long appUserId){
        List<PersonalLearnUnit> units = personalLearnUnitService.findReviewTestUnits(appUserId);
        return  new ApiResult(ResultMsg.SUCCESS,units) ;
    }







}
