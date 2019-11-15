package com.runer.cibao.service.impl;

import com.runer.cibao.domain.SchoolTips;
import com.runer.cibao.domain.repository.SchoolTipsRepository;
import com.runer.cibao.service.SchoolTipsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolTipsServiceImpl extends BaseServiceImp<SchoolTips,SchoolTipsRepository> implements SchoolTipsService {
    @Override
    public List<SchoolTips> findSchoolTips(Long shoolId) {
        return r.findSchoolTipsBySchool_Id(shoolId);
    }
}
