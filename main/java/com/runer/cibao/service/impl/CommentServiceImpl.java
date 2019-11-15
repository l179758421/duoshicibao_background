package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.Comment;
import com.runer.cibao.domain.repository.CommentRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl extends BaseServiceImp<Comment,CommentRepository> implements CommentService {

    @Override
    public ApiResult saveComment(String comment, Long userId, Long unitId) {
        Comment comment1;
       List<Comment> list= r.findByUserIdAndUnitId(userId,unitId);
       if(list.size()>0){
           comment1=list.get(0);
       }else{
           comment1 = new Comment();

       }
        comment1.setComment(comment);
        comment1.setUserId(userId);
        comment1.setUnitId(unitId);
        comment1.setCommemtTime(new Date());
        r.saveAndFlush(comment1);
        return new ApiResult(ResultMsg.SUCCESS,comment1);
    }
}
