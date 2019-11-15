package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.BookTestQuetions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/19
 **/
public interface BookTestQuestionsRepository extends JpaRepository<BookTestQuetions,Long> ,JpaSpecificationExecutor<BookTestQuetions> {

}
