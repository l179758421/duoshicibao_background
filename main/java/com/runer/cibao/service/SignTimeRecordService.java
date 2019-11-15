package com.runer.cibao.service;

import com.runer.cibao.domain.SignTimeRecord;
import com.runer.cibao.domain.repository.SignTimeRecordRepository;

import java.util.List;

public interface SignTimeRecordService extends BaseService<SignTimeRecord, SignTimeRecordRepository> {
    public void saveRecord(SignTimeRecord signTimeRecord);

    public List<SignTimeRecord> findByUserId(Long userId);
}
