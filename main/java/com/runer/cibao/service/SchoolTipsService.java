package com.runer.cibao.service;

import com.runer.cibao.domain.SchoolTips;
import com.runer.cibao.domain.repository.SchoolTipsRepository;

import java.util.List;

public interface SchoolTipsService extends BaseService<SchoolTips,SchoolTipsRepository> {

    /**
     * 查找学校名言警句
     * @param shoolId
     * @return
     */
    List<SchoolTips> findSchoolTips(Long shoolId);
}
