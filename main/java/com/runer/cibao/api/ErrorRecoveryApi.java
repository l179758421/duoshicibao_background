package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.PageApiResult;
import com.runer.cibao.domain.ErrorRecovery;
import com.runer.cibao.service.ErrorRecoveryService;
import com.runer.cibao.util.NormalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/29
 **/


@RestController
@RequestMapping(value = "api/ErrorRecoveryApi")
@Api(value = "纠错",description = "纠错")
public class ErrorRecoveryApi {


    @Autowired
    ErrorRecoveryService errorRecoveryService ;


    @ApiOperation(value = "纠错",notes = "纠错")
    @RequestMapping(value = "errorRecovery",method = RequestMethod.POST)
    public ApiResult errorRecovery(Long userId , Long wordId ,String des){
      return   errorRecoveryService.errorRecovery(userId,wordId ,des) ;
    }

    @ApiOperation(value = "获得纠错的集合",notes = "获得纠错的集合")
    @RequestMapping(value = "getErrors",method = RequestMethod.POST)
    public PageApiResult<ErrorRecovery> getError(Long userId , Integer resolved , Integer page , Integer limit ){
        Page<ErrorRecovery> datas = errorRecoveryService.findErrors(userId, null, resolved, page, limit);;
        return NormalUtil.createPageResult(datas) ;
    }

    @ApiOperation(value = "纠错图片上传",notes = "纠错图片上传")
    @RequestMapping(value = "errorRecoveryImg",method = RequestMethod.POST)
    public ApiResult errorRecoveryImg(Long userId , MultipartFile img ){
        return   errorRecoveryService.uploadErrorImag(userId,img);
    }


}
