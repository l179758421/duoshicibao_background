package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.Agents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/9
 **/


public interface AgentsRepostory  extends JpaRepository<Agents,Long> ,JpaSpecificationExecutor<Agents> {

    Agents findAgentsByName(String name) ;

}
