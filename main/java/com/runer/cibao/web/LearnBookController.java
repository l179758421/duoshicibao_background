package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.LearnBook;
import com.runer.cibao.domain.User;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.BookUnitService;
import com.runer.cibao.service.BookWordService;
import com.runer.cibao.service.LearnBookService;
import com.runer.cibao.service.UserLoginService;
import com.runer.cibao.util.NormalUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author sww
 * @Date 2019/9/26
 **/

@RestController
@RequestMapping(value = "learnBook")
public class LearnBookController {

    @Autowired
    LearnBookService beanService ;
    @Autowired
    UserLoginService userLoginService ;
    @Autowired
    BookUnitService bookUnitService ;
    @Autowired
    BookWordService bookWordService;

    @RequestMapping(value = "addOrUpdateUnit")
    public ApiResult addOrUpdateUnit(Long bookid ,String name ,Long id ){
     return  bookUnitService.addOrUpdateUnit(id,name,bookid);
    }

    @RequestMapping(value = "data_list")
    public LayPageResult<LearnBook> getDataList(String bookName , String version , String grade ,String stage ,
                                                Integer page , Integer limit){
        Page<LearnBook> pageResult = beanService.findBooks(null, null, null,
                null,version,bookName,grade,stage, page,limit);
        return NormalUtil.createLayPageReuslt(pageResult) ;
    }

    @RequestMapping(value = "addOrUpdateData")
    public ApiResult addOrUpdateData(Long id , Integer price , String version , String bookName , String grade , String wordsNum , String stage , String imgUrl, HttpServletRequest req){
        Long userId =null ;
        User currentUser = userLoginService.getCurrentUser(req);
        if (currentUser!=null){
            userId =currentUser.getId() ;
        }else{
            return  new ApiResult(ResultMsg.NOT_FOUND,null);
        }
        return    beanService.addOrUpadateBook(id,price,version,bookName,grade,wordsNum, stage ,imgUrl ,userId);
    }


    @RequestMapping("deleteByIds")
    public ApiResult deleteByIds(String ids){
        return  NormalUtil.deleteByIds(beanService,ids);
    }

    @RequestMapping("deleteById")
    public ApiResult deleteById(Long id){
        return  NormalUtil.deleteById(beanService,id);
    }

    @RequestMapping("updateBookCover")
    public ApiResult updateBookCover(MultipartFile file ){
    return      beanService.uploadBookCover(file);
    }

}
