package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/5
 **/
public interface CityRepository extends JpaSpecificationExecutor<City> ,JpaRepository<City,Long> {
    City findByName(String name);
}
