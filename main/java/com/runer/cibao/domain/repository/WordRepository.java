package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/14
 **/
public interface WordRepository  extends JpaRepository<Word,Long> ,JpaSpecificationExecutor<Word> {

    /**
     * 根据word的名字查询；
     * @param word
     * @return
     */
    Word findWordByWord(String word) ;



}
