package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.LearnedWords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/29
 **/
public interface LearnedWordsRepostitory  extends JpaRepository<LearnedWords,Long> ,JpaSpecificationExecutor<LearnedWords> {

}
