package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.person_word.WordLearn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/17
 **/
public interface WordLearnRepostitory  extends JpaRepository<WordLearn,Long> ,JpaSpecificationExecutor<WordLearn> {

}
