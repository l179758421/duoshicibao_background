package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.OneWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/11/1
 * 名言相关的
 **/
public interface OneWordRepository  extends JpaRepository<OneWord,Long> ,JpaSpecificationExecutor<OneWord> {
    /**
     * 根据content查询；
     * @param content
     * @return
     */
    OneWord findWordByContent(String content) ;
}
