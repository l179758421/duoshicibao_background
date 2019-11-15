package com.runer.cibao.service;

import com.runer.cibao.domain.Province;
import com.runer.cibao.domain.repository.ProvinceRepository;

public interface ProvinceService extends BaseService<Province, ProvinceRepository> {
    Province findByName(String name);
}
