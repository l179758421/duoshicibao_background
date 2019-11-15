package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.ErrorRecovery;
import com.runer.cibao.domain.repository.ErrorRecoveryRepository;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/29
 **/
public interface ErrorRecoveryService extends BaseService<ErrorRecovery, ErrorRecoveryRepository> {

    /**
     * 添加纠错
     * @param userId
     * @param wordId
     * @param  des
     * @return
     */
    ApiResult errorRecovery(Long userId, Long wordId, String des) ;


    /**
     * 获得纠错列表
     * @param userId
     * @param replyUserId
     * @param page
     * @param limit
     * @return
     */
    Page<ErrorRecovery> findErrors(Long userId, Long replyUserId, Integer isResolved, Integer page, Integer limit) ;


    /**
     * 解决纠错
     * @param errorId
     * @param userId
     * @param reply
     * @return
     */
    ApiResult resolveError(Long errorId, Long userId, String reply) ;


    /**
     * 上传纠错图片
     * @param userId
     * @param img
     * @return
     */
    ApiResult  uploadErrorImag(Long userId, MultipartFile img);



}
