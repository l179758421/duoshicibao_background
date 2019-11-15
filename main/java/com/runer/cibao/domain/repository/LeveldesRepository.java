package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.Leveldes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/11/14
 **/
public interface LeveldesRepository  extends JpaRepository<Leveldes,Long>,JpaSpecificationExecutor<Leveldes> {
}
