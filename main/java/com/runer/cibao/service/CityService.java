package com.runer.cibao.service;

import com.runer.cibao.domain.City;
import com.runer.cibao.domain.repository.CityRepository;

public interface CityService extends BaseService<City, CityRepository> {

    City findByName(String name);

}
