package com.runer.cibao.service.impl;

import com.runer.cibao.domain.Province;
import com.runer.cibao.domain.repository.ProvinceRepository;
import com.runer.cibao.service.ProvinceService;
import org.springframework.stereotype.Service;

@Service
public class ProvinceServiceImpl extends BaseServiceImp<Province, ProvinceRepository> implements ProvinceService {
    @Override
    public Province findByName(String name) {
        return r.findByName(name);
    }
}
