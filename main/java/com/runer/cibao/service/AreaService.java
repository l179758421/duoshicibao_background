package com.runer.cibao.service;

import com.runer.cibao.domain.Area;
import com.runer.cibao.domain.City;
import com.runer.cibao.domain.Province;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/6
 *
 * 地区相关的业务
 **/
public interface AreaService {

     List<Province> findProvinces();
     Province getProvince(Long id);

     List<City> findCitysByProviceId(Long id) ;
     City findCityById(Long id) ;

     List<Area> findAreaByCityId(Long id);
     Area findAreaById(Long id);




}
