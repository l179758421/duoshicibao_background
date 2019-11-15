package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.TestRecords;
import com.runer.cibao.domain.repository.TestRecordRepository;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/11/
 * 测试记录service；
 **/
public interface TestRecordService extends  BaseService<TestRecords, TestRecordRepository>{

    ApiResult getTestRecord(Long unitTestId, Long bookTestId, Integer type) ;


}
