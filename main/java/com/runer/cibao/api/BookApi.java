package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.AppUserShowInfoBean;
import com.runer.cibao.domain.BookUnit;
import com.runer.cibao.service.*;
import com.runer.cibao.util.NormalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/28
 **/

@RestController
@RequestMapping(value = "api/bookApi")
@Api(description = "课本相关")
public class BookApi {



    @Autowired
    PersonalLearnBookService personalLearnBookService ;


    @Autowired
    PersonalBookApiService personalBookApiService;

    @Autowired
    BookUnitService bookUnitService ;

    @Autowired
    LearnBookService learnBookService ;


    @ApiOperation(value = "获得所有的阶段",notes = "获得所有的阶段")
    @RequestMapping(value = "getAllStage",method = RequestMethod.POST)
    public ApiResult getAllStage( ){
        return personalBookApiService.findAllStage() ;
    }


    @ApiOperation(value = "根据阶段获得版本",notes = "根据阶段获得版本")
    @RequestMapping(value = "getVersions",method = RequestMethod.POST)
    public ApiResult getVersions(String stage ){
        return personalBookApiService.findVersions(stage) ;
    }

    @ApiOperation(value = "获得课本列表",notes = "获得课本列表")
    @RequestMapping(value = "findAllBooksWihtUser",method = RequestMethod.POST)
    public ApiResult findAllBooksWihtUser(String version ,String stage ,Long userId){
        return  personalBookApiService.findAllbooks(version,stage,userId) ;
    }


    @ApiOperation(value = "获得课本列表",notes = "获得课本列表")
    @RequestMapping(value = "findBookUnits",method = RequestMethod.POST)
    public ApiResult findUnitsByBookId(Long bookId){
        List<BookUnit> data = bookUnitService.findUnits(bookId, null, null, null, null, null, null, null
                , null);
        return NormalUtil.generateSuccessResult(data) ;
    }


    @Autowired
    BookOrdersInfoService bookOrdersInfoService ;

    /**
     * 学员数 激活总数 小初高大学 30天内激活数
     * @return
     */
    @RequestMapping(value = "agentsSellsInfo" ,method = RequestMethod.POST)
    public LayPageResult<AppUserShowInfoBean> agentsSellsInfo(Long  schoolId , Long classId , Integer page , Integer limit    ){
        return  bookOrdersInfoService.findAppUserInfos(schoolId,classId,page ,limit);
    }








}
