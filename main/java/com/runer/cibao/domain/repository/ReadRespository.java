package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.GoodTeaches;
import com.runer.cibao.domain.Read;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author sww
 * @Description:cibao==
 * @Date 2019/11/27
 **/
public interface ReadRespository extends JpaRepository<Read,Long> ,JpaSpecificationExecutor<Read> {


}
