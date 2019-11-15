package com.runer.cibao.service.impl;

import com.runer.cibao.domain.City;
import com.runer.cibao.domain.repository.CityRepository;
import com.runer.cibao.service.CityService;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl extends BaseServiceImp<City, CityRepository> implements CityService {


    @Override
    public City findByName(String name) {
        return r.findByName(name);
    }
}
