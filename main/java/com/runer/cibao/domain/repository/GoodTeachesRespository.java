package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.GoodTeaches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/9
 **/
public interface GoodTeachesRespository extends JpaRepository<GoodTeaches,Long> ,JpaSpecificationExecutor<GoodTeaches> {


}
