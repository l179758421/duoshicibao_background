package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.PersonalLearnBook;
import com.runer.cibao.domain.PersonalLearnUnit;
import com.runer.cibao.domain.UnitBeanForApi;
import com.runer.cibao.domain.WordLearnForPersonal;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.*;
import com.runer.cibao.util.NormalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/26
 **/

@RestController
@RequestMapping(value = "api/PersonalBookApi")
@Api(description = "个人课本")
public class PersonalBookApi {

    @Autowired
    private PersonalLearnUnitService personalLearnUnitService ;

    @Autowired
    private PersonalLearnBookService personalLearnBookService ;

    @Autowired
    private PersonalBookApiService personalBookApiService ;

    @Autowired
    private LearnBookService learnBookService ;


    @Autowired
    private WordCountService wordCountService;

    @Autowired
    private PersonalTestForUnitService testForUnitService;

    @Autowired
    private WordLearnForPersonalService wordLearnForPersonalService ;




    @ApiOperation(value = "downLoadBookWordsFile" ,notes = "打包下载文件")
    @RequestMapping(value = "downLoadBookWordsFile",method = {RequestMethod.POST,RequestMethod.GET})
    public ResponseEntity<FileSystemResource> downLoadWordsFile(Long bookId){
        ApiResult responesAPiResult = learnBookService.downLoadWordsZip(bookId);
        if (responesAPiResult.isSuccess()){
            return (ResponseEntity<FileSystemResource>) responesAPiResult.getData();
        }else{
            return  null ;
        }
    }
    @ApiOperation(value = "购买图书",notes = "购买图书")
    @RequestMapping(value = "buyPersonalBook",method = RequestMethod.POST)
    public ApiResult bought(Long userId ,Long bookId){
        return  personalLearnBookService.buyPersonalBook(bookId,userId,false) ;
    }

    /**
     * 获得图书的价格；
     * @param userId
     * @param bookId
     * @return
     */
    @ApiOperation(value = "获得图书的价格",notes = "获得图书的价格")
    @RequestMapping(value = "getBookPrice",method = RequestMethod.POST)
    public ApiResult getBookPrice(Long userId ,Long bookId){
        int price = personalLearnBookService.getBookPrice(userId,bookId) ;
        ApiResult apiResult =new ApiResult(ResultMsg.SUCCESS,price) ;
        return  apiResult ;
    }


    @ApiOperation(value = "直接购买图书--无需进行体验",notes = "直接购买图书")
    @RequestMapping(value = "buyBookAll",method = RequestMethod.POST)
    public ApiResult buyBookAll(Long userId ,Long bookId){
        return  personalLearnBookService.buyPersonalBook(bookId,userId,true) ;
    }


    @ApiOperation(value = "获得个人所有的课本",notes = "获得个人所有的课本")
    @RequestMapping(value = "personalAllBooks",method = RequestMethod.POST)
    public ApiResult getPersonalAllBooks(Long userId){
        return  personalBookApiService.findAllPersonalBooks(userId,false) ;
    }

    @ApiOperation(value = "获得个人除当前的所有课本",notes = "获得个人除当前的所有课本")
    @RequestMapping(value = "personalBooksWithOutCurrent",method = RequestMethod.POST)
    public ApiResult personalBooksWithOutCurrent(Long userId){
        return  personalBookApiService.findLearnBooksWithOutCurrent(userId);
    }

    @ApiOperation(value = "获得个人当前课本",notes = "获得个人当前课本")
    @RequestMapping(value = "personalCurrentBook",method = RequestMethod.POST)
    public ApiResult personalCurrentBook(Long userId){
        return  personalBookApiService.findCurrentLearnBook(userId);
    }

    @ApiOperation(value = "设置为当前的课本",notes = "设置为当前的课本")
    @RequestMapping(value = "setCurrentLearnBooks",method = RequestMethod.POST)
    public ApiResult setCurrentBook(Long personalLeanrBookId ){
        return  personalLearnBookService.setLearnBookToCurrent(personalLeanrBookId);
    }

    @ApiOperation(value = "获得个人的unit",notes = "获得个人的unit")
    @RequestMapping(value = "personalUnits",method = RequestMethod.POST)
     public ApiResult  findPersnalUnits(Long personalBookId,Long appUserId,Long bookId ){


        PersonalLearnBook personalLearnBook =null;

        if (personalBookId!=null) {
            ApiResult personlBookResult = personalLearnBookService.findByIdWithApiResult(personalBookId);
            if (personlBookResult.isFailed()){
                return  new ApiResult(ResultMsg.SUCCESS,new ArrayList<>());
            }
            personalLearnBook = (PersonalLearnBook) personlBookResult.getData();
        }
        if (appUserId!=null&&bookId!=null){
         ApiResult  personlBookResult =    personalLearnBookService.getSinglePersonalLearnBook(appUserId,bookId);
         if (personlBookResult.isFailed()){
             return  new ApiResult(ResultMsg.SUCCESS,new ArrayList<>()) ;
         }
         personalLearnBook = (PersonalLearnBook) personlBookResult.getData();
        }

        List<UnitBeanForApi> result =new ArrayList<>() ;
        List<PersonalLearnUnit> datas;

        WordLearnForPersonal wordLearnForPersonal = (WordLearnForPersonal) wordLearnForPersonalService.findOne(personalLearnBook.getPersonalLearnBooks().getAppUser().getId(),personalLearnBook.getLearnBook().getId()).getData();

       //初始的数据
       Page<PersonalLearnUnit> page  = personalLearnUnitService.findUnits(null, null, personalBookId, null, null, null,null ,1, Integer.MAX_VALUE);;
       datas = page.getContent();

        datas.forEach(personalLearnUnit -> {
            UnitBeanForApi api = personalLearnUnit.personalUnitToUnitApi();
            if (!ListUtils.isEmpty((List<?>) testForUnitService.findOneDayTests(api.getUserId(),api.getUnitId(),api.getPersonalUnitId(),new Date()).getData())){
                api.setTodayTest(true);
            }
            result.add(api) ;
        });
        return NormalUtil.generateSuccessResult(result) ;
    }

    @Deprecated
    @ApiOperation(value = "认知当前单词" ,notes = "认知当前单词")
    @RequestMapping(value = "addCurrentWord",method = {RequestMethod.POST})
    public ApiResult addCurrentWord(Long wordId ,Long pesonalLearnBookId ){
        return  personalLearnBookService.setCurrentWord(wordId,pesonalLearnBookId) ;
    }

    @ApiOperation(value = "dataSync" ,notes = "同步文件")
    @RequestMapping(value = "dataSync",method = {RequestMethod.POST,RequestMethod.GET})
    public ResponseEntity<FileSystemResource> dataSync(Long userId ){
        try {
            ApiResult responesAPiResult   = personalLearnBookService.syncBooksData(userId);
            return (ResponseEntity<FileSystemResource>) responesAPiResult.getData();
        } catch (Exception e) {
            e.printStackTrace();
            return  null ;
        }
    }
}
