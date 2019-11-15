package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.OneWordDao;
import com.runer.cibao.domain.OneWord;
import com.runer.cibao.domain.repository.OneWordRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.OneWordService;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/11/1
 **/
@Service
public class OneWordServiceImpl extends BaseServiceImp<OneWord,OneWordRepository> implements OneWordService {

    @Autowired
    OneWordDao oneWordDao ;

    @Override
    public ApiResult addOneWord(Long id ,String oneWord, String oneWordName ,String translation ) {
        OneWord bean =new OneWord() ;
        if (id!=null){
            ApiResult oneResult = findByIdWithApiResult(id);
            if (oneResult.isSuccess()){
                bean = (OneWord) oneResult.getData();
            }else{
                return  oneResult ;
            }
        }else{
            bean.setCreateTime(new Date());
        }
        bean.setTranslation(translation);
        bean.setContent(oneWord);
        bean.setOneWordName(oneWordName);
        r.save(bean) ;
        return new ApiResult(ResultMsg.SUCCESS,bean);
    }

    @Override
    public ApiResult getOneWord(Long appUserId ) {
        List<OneWord> words = findAll();
        if (!ListUtils.isEmpty(words)){
            int oneIndex = new Random().nextInt(words.size()) ;
            return  new ApiResult(ResultMsg.SUCCESS,words.get(oneIndex));
        }
        return new ApiResult(ResultMsg.SUCCESS,null);
    }

    @Override
    public Page<OneWord> findOneWords(String content, Integer page, Integer limit) {
        Page<OneWord> oneWordPage = oneWordDao.findOneWords(content,PageableUtil.basicPage(page, limit));
        return oneWordPage;
    }

    @Override
    public ApiResult getById(Long id){
        if(id==null){
            return new ApiResult(ResultMsg.NOT_FOUND,null);
        }
        OneWord byId = null;
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
