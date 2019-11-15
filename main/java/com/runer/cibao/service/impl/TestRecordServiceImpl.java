package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.TestRecordDao;
import com.runer.cibao.domain.TestRecords;
import com.runer.cibao.domain.repository.TestRecordRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.TestRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/11/1
 **/
@Service
public class TestRecordServiceImpl extends BaseServiceImp<TestRecords, TestRecordRepository> implements TestRecordService {

    @Autowired
    TestRecordDao testRecordDao ;

    @Override
    public ApiResult getTestRecord(Long unitTestId, Long bookTestId , Integer type) {

        List<TestRecords> records = testRecordDao.find(unitTestId, bookTestId, type);


        return new ApiResult(ResultMsg.SUCCESS,records);
    }
}
