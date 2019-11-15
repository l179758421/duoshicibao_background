package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.BookUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/15
 **/
public interface BookUnitRepository extends JpaRepository<BookUnit,Long> ,JpaSpecificationExecutor<BookUnit> {



}
