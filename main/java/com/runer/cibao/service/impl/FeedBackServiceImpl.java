package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.FeedBackDao;
import com.runer.cibao.domain.AppUser;
import com.runer.cibao.domain.FeedBack;
import com.runer.cibao.domain.ImageInDbForCache;
import com.runer.cibao.domain.User;
import com.runer.cibao.domain.repository.FeedBackRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import com.runer.cibao.util.NormalUtil;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/12
 **/

@Service
public class FeedBackServiceImpl extends BaseServiceImp<FeedBack,FeedBackRepository> implements FeedBackService {


    @Autowired
    FeedBackDao feedBackDao ;

    @Autowired
    AppUserService appUserService ;

    @Autowired
    ImageInDbForCacheService imageInDbForCacheService ;

    @Autowired
    UserLoginService userLoginService ;


    @Autowired
    UserService userService ;



    @Override
    public Page<FeedBack> findFeedBacks(String school,Long askUserId, String askName, Long answerUserId, String answerUserName,Integer ifSolve,
                                        Integer page, Integer limit) {

       return feedBackDao.findFeedBacks(school,askUserId,askName,answerUserId,answerUserName,ifSolve,PageableUtil.basicPage(page,limit));
    }



    @Value("${web.upload-appPath}")
    private String appPath ;

    @Value("${web.upload-app}")
    private String absolutePath ;



    @Override
    public ApiResult askFeedBack(Long askUserId, String content, List<MultipartFile> files) {

        if (askUserId==null){
            return  new ApiResult(ResultMsg.USER_ID_IS_NOT_ALLOWED_NULL,null) ;
        }
        if (StringUtils.isEmpty(content)){
            return  new ApiResult(ResultMsg.CONENT_IS_NOT_ALLOWED_NULL,null);
        }

        try {
            appUserService.findById(askUserId);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null);
        }

        FeedBack feedBack =new FeedBack() ;
        AppUser appUser =new AppUser() ;
        appUser.setId(askUserId);
        feedBack.setAskUser(appUser);

        feedBack.setContent(content);
        feedBack.setAskTime(new Date());
        feedBack.setIfSolve(0);

        feedBack =r.save(feedBack);

        List<ImageInDbForCache> imageInDbForCaches =new ArrayList<>() ;
        if (!ListUtils.isEmpty(files)){
            for (MultipartFile file : files) {
                ApiResult fileResult =NormalUtil.saveMultiFile(file,appPath,absolutePath);
                if (fileResult.getMsgCode()== ResultMsg.SUCCESS.getMsgCode()) {
                    ImageInDbForCache imageInDbForCache = new ImageInDbForCache();
                    imageInDbForCache.setImgUrl((String) fileResult.getData());
                    FeedBack feedBackFor =new FeedBack() ;
                    feedBackFor.setId(feedBack.getId());
                    imageInDbForCache.setFeedBack(feedBackFor);
                    imageInDbForCaches.add(imageInDbForCache);
                }
            }
        }
        try {
         imageInDbForCaches =   imageInDbForCacheService.saveOrUpdate(imageInDbForCaches) ;
         feedBack.setImageInDbForCacheList(imageInDbForCaches);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null) ;
        }

        feedBack =r.findById(feedBack.getId()).get();

        return new ApiResult(ResultMsg.SUCCESS,feedBack);
    }

    @Override
    public ApiResult answerFeedBack(Long id, String answerContent, Long answerUserId) {

        if (id==null){
            return  new ApiResult(ResultMsg.IDS_IS_NOT_ILLEGAL,null);
        }
        try {
            FeedBack feedBack =  findById(id) ;

            feedBack.setAnswerContent(answerContent);

            User user =userService.findById(answerUserId);

            feedBack.setAnswerUser(user);

            feedBack.setAnswerTiem(new Date());
            if(answerContent !=null){
                feedBack.setIfSolve(1);
            }

           feedBack = r.save(feedBack);

           return  new ApiResult(ResultMsg.SUCCESS,feedBack);

        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null) ;
        }

    }

    @Override
    public ApiResult getById(Long id){
        if(id==null){
            return new ApiResult(ResultMsg.NOT_FOUND,null);
        }
        FeedBack byId = null;
        try{
            byId = findById(id);
        }catch (SmartCommunityException e){
            return new ApiResult(ResultMsg.NOT_FOUND,null);
        }
        if(byId==null){
            return new ApiResult(ResultMsg.NOT_FOUND,null);
        }
        return new ApiResult(ResultMsg.SUCCESS,byId);
    }

}
