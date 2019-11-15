package com.runer.cibao.service.impl;

import com.runer.cibao.domain.Area;
import com.runer.cibao.domain.City;
import com.runer.cibao.domain.Province;
import com.runer.cibao.domain.repository.AreaRepository;
import com.runer.cibao.domain.repository.CityRepository;
import com.runer.cibao.domain.repository.ProvinceRepository;
import com.runer.cibao.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/6
 **/

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    ProvinceRepository provinceRepository ;

    @Autowired
    CityRepository cityRepository ;

    @Autowired
    AreaRepository areaRepository ;


    @Override
    public List<Province> findProvinces() {
        return provinceRepository.findAll();
    }

    @Override
    public Province getProvince(Long id) {
        Optional<Province> proOptional = provinceRepository.findById(id);
        if (proOptional.isPresent()){
            return  proOptional.get() ;
        }
        return  null ;
    }
    @Override
    public List<City> findCitysByProviceId(Long id) {
        Province province =getProvince(id);
        if (province==null){
            return  new ArrayList<>() ;
        }else{
            if (ListUtils.isEmpty(province.getCityList())){
                return  new ArrayList<>() ;
            }
        }
        return  province.getCityList() ;
    }

    @Override
    public City findCityById(Long id) {
        Optional<City> cityOptional =cityRepository.findById(id);
        if (cityOptional.isPresent()){
            return  cityOptional.get() ;
        }
        return null;
    }

    @Override
    public List<Area> findAreaByCityId(Long id) {
        City city =findCityById(id);
        if(city==null){
            return  new ArrayList<>() ;
        }else{
            if (ListUtils.isEmpty(city.getAreaList())){
                return  new ArrayList<>() ;
            }
        }
        return city.getAreaList() ;
    }

    @Override
    public Area findAreaById(Long id) {
        Optional<Area> areaOptional =areaRepository.findById(id);
        if (areaOptional.isPresent()){
            return  areaOptional.get() ;
        }
        return null;
    }
}
