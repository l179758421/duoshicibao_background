package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.Comment;
import com.runer.cibao.domain.repository.CommentRepository;

public interface CommentService extends BaseService<Comment, CommentRepository> {

    /**
     * 保存点评
     * @param comment
     * @param userId
     * @param unitId
     * @return
     */
    ApiResult saveComment(String comment, Long userId, Long unitId);
}
