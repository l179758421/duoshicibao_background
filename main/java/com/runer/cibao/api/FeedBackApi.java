package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.PageApiResult;
import com.runer.cibao.domain.FeedBack;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.FeedBackService;
import com.runer.cibao.util.NormalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/12
 **/
@RestController
@RequestMapping(value = "api/feedback")
@Api(description = "反馈相关的API")
public class FeedBackApi {

    @Autowired
    FeedBackService feedBackService ;

    @RequestMapping(value = "addFeedBack",method = {RequestMethod.POST})
    @ApiOperation(notes = "添加反馈",value = "addFeedback")
    public ApiResult addFeedBack(Long userId , String content , MultipartFile file1 ,MultipartFile file2 ,MultipartFile file3 ,MultipartFile file4){

        List<MultipartFile>  files =new ArrayList<>() ;
        if (file1!=null&&!file1.isEmpty()){
            files.add(file1) ;
        }
        if (file2!=null&&!file2.isEmpty()){
            files.add(file2) ;
        }
        if (file3!=null&&!file3.isEmpty()){
            files.add(file3) ;
        }
        if (file4!=null&&!file4.isEmpty()){
            files.add(file4) ;
        }
      return   feedBackService.askFeedBack(userId,content,files);
    }


    @RequestMapping(value = "feedDetail",method = {RequestMethod.POST})
    @ApiOperation(notes = "反馈详情",value = "反馈详情")
    public ApiResult findFeedBackDetail(Long id){
        try {
            FeedBack feedBack = feedBackService.findById(id);
            return  new ApiResult(ResultMsg.SUCCESS,feedBack);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null);
        }
    }

    @RequestMapping(value = "feedBackList",method = {RequestMethod.POST})
    @ApiOperation(notes = "反馈列表",value = "反馈列表")
    public PageApiResult<FeedBack> findFeedBacks(Long userId ,Integer page ,Integer limit){
       Page<FeedBack> feedBacks = feedBackService.findFeedBacks(null,userId,null,null,null,null,page,limit);
       return NormalUtil.createPageResult(feedBacks);
    }


}
