package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.NewBookWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/29
 **/
public interface NewBookWordRepository extends JpaRepository<NewBookWord,Long> ,JpaSpecificationExecutor<NewBookWord> {



}
