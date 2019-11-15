package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.LearnBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/11
 **/
public interface LearnBookRepository  extends JpaRepository<LearnBook,Long> ,JpaSpecificationExecutor<LearnBook> {
}
