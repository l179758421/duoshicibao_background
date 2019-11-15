package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.OneWord;
import com.runer.cibao.domain.repository.OneWordRepository;
import org.springframework.data.domain.Page;

/***
 * @Author szhua
 * 名言相关的；
 */
public interface OneWordService  extends BaseService<OneWord, OneWordRepository>  {

    ApiResult addOneWord(Long id, String oneWord, String oneWordName, String translation) ;

    ApiResult getOneWord(Long appUserId) ;

    /**
     * 查询content
     * @param content
     *
     * @return
     */
    Page<OneWord> findOneWords(String content, Integer page, Integer limit);

    ApiResult getById(Long id);
}
