package com.runer.cibao.service.impl;

import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.ErrorRecoverDao;
import com.runer.cibao.domain.AppUser;
import com.runer.cibao.domain.BookWord;
import com.runer.cibao.domain.ErrorRecovery;
import com.runer.cibao.domain.User;
import com.runer.cibao.domain.repository.ErrorRecoveryRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.AppUserService;
import com.runer.cibao.service.BookWordService;
import com.runer.cibao.service.ErrorRecoveryService;
import com.runer.cibao.service.UserService;
import com.runer.cibao.util.NormalUtil;
import com.runer.cibao.util.page.PageableUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

import static com.runer.cibao.exception.ResultMsg.DES_IS_NULL;
import static com.runer.cibao.exception.ResultMsg.SUCCESS;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/29
 **/
@Service
public class ErrorRecoveryServiceImpl extends BaseServiceImp<ErrorRecovery, ErrorRecoveryRepository> implements ErrorRecoveryService {


    @Autowired
    ErrorRecoverDao errorRecoverDao ;

    @Autowired
    AppUserService appUserService ;


    @Autowired
    BookWordService bookWordService ;


    @Autowired
    UserService userService ;

    @Value("${web.upload-appPath}")
    private String appPath;

    @Value("${web.upload-app}")
    private String absolutePath;


    @Override
    public ApiResult errorRecovery(Long userId, Long wordId, String des) {

        ApiResult userResult = appUserService.findByIdWithApiResult(userId) ;
        if (userResult.getMsgCode()!=SUCCESS.getMsgCode()){
            return  userResult ;
        }
        AppUser appUser = (AppUser) userResult.getData();


        ApiResult wordResult =bookWordService.findByIdWithApiResult(wordId);
        if (wordResult.getMsgCode()!=SUCCESS.getMsgCode()){
            return  wordResult ;
        }
        BookWord bookWord = (BookWord) wordResult.getData();


        if (StringUtils.isEmpty(des)){
            return  new ApiResult(ResultMsg.DES_IS_NULL,null) ;
        }



        ErrorRecovery errorRecovery =new ErrorRecovery() ;
        errorRecovery.setBookWord(bookWord);
        errorRecovery.setAppUser(appUser);
        errorRecovery.setDes(des);
        errorRecovery.setCreateTime(new Date());
        errorRecovery.setIsResolved(Config.IS_NOT_RESOLVED);
        errorRecovery.setReply("");
        errorRecovery.setUser(null);


       errorRecovery =  r.saveAndFlush(errorRecovery) ;


        return new ApiResult( SUCCESS,errorRecovery);
    }

    @Override
    public Page<ErrorRecovery> findErrors(Long userId, Long replyUserId, Integer isResolved ,Integer page, Integer limit) {
        return errorRecoverDao.findErrorRecoveries(userId,isResolved,replyUserId,PageableUtil.basicPage(page,limit));
    }



    @Override
    public ApiResult resolveError(Long errorId, Long userId, String reply) {

       ApiResult errorResult =  findByIdWithApiResult(errorId) ;
       if (errorResult.getMsgCode()!=SUCCESS.getMsgCode()){
           return  errorResult ;
       }
       ErrorRecovery errorRecovery = (ErrorRecovery) errorResult.getData();


       ApiResult userResult =userService.findByIdWithApiResult(userId);
       if (userResult.getMsgCode()!=SUCCESS.getMsgCode()){
           return  userResult ;
       }
       User user = (User) userResult.getData();


       if (StringUtils.isEmpty(reply)){
           return  new ApiResult(DES_IS_NULL,null) ;
       }


       errorRecovery.setReply(reply);
       errorRecovery.setUser(user);
       errorRecovery.setResolvedTime(new Date());
       errorRecovery.setIsResolved(Config.IS_RESOLVED);


      errorRecovery =  r.saveAndFlush(errorRecovery) ;

        return new ApiResult(SUCCESS,errorRecovery);
    }

    @Override
    public ApiResult uploadErrorImag(Long userId, MultipartFile img) {
        ErrorRecovery errorRecovery = null;

        List<ErrorRecovery> errorRecoveryList = r.findByAppUser_IdOrderByCreateTimeDesc(userId);
      if(errorRecoveryList!=null && errorRecoveryList.size()>0){
          errorRecovery=  errorRecoveryList.get(0);
      }
        if (img != null && !img.isEmpty()) {
            ApiResult fileApiResult = NormalUtil.saveMultiFile(img, appPath, absolutePath);
            if (fileApiResult.getMsgCode() == ResultMsg.SUCCESS.getMsgCode()) {
                errorRecovery.setImgUrl((String) fileApiResult.getData());
            }
        }
        r.saveAndFlush(errorRecovery);

        return new ApiResult(ResultMsg.SUCCESS,errorRecovery);
    }
}
