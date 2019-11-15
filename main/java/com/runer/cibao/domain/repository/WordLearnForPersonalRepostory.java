package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.WordLearnForPersonal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/27
 **/
public interface  WordLearnForPersonalRepostory  extends JpaRepository<WordLearnForPersonal,Long> ,JpaSpecificationExecutor<WordLearnForPersonal> {

    WordLearnForPersonal findWordLearnForPersonalByAppUserIdAndBookId(Long userId, Long bookId) ;


}
