package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/5
 **/
public interface ProvinceRepository extends JpaRepository<Province,Long> ,JpaSpecificationExecutor<Province> {
  Province findByName(String name);
}
