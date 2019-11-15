package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.FeedBack;
import com.runer.cibao.domain.repository.FeedBackRepository;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/12
 **/
public interface FeedBackService  extends BaseService<FeedBack,FeedBackRepository>{


    /**
     * 查找反馈
     * @param askUserId
     * @param askName
     * @param answerUserId
     * @param answerUserName
     * @param page
     * @param limit
     * @return
     */
    Page<FeedBack> findFeedBacks(String school, Long askUserId, String askName, Long answerUserId, String answerUserName, Integer ifSolve, Integer page, Integer limit);


    /**
     * 提问反馈
     * @param askUserId
     * @param content
     * @param files
     * @return
     */
    ApiResult askFeedBack(Long askUserId, String content, List<MultipartFile> files);


    /**
     * 回答反馈
     * @param id
     * @param answerContent
     * @param answerUserId
     * @return
     */
    ApiResult answerFeedBack(Long id, String answerContent, Long answerUserId) ;


    ApiResult getById(Long id);
}
