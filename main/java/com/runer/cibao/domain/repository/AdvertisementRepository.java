package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/23
 **/
public interface AdvertisementRepository  extends JpaRepository<Advertisement,Long> ,JpaSpecificationExecutor<Advertisement> {


}
