package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.WordCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author k
 * @Date: Created in 16:11 2018/8/23
 * @Description:
 */
public interface WordCountRepository extends JpaRepository<WordCount ,Long> , JpaSpecificationExecutor<WordCount> {

}
