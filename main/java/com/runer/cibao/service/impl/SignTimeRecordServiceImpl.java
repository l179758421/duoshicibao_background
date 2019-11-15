package com.runer.cibao.service.impl;

import com.runer.cibao.domain.SignTimeRecord;
import com.runer.cibao.domain.repository.SignTimeRecordRepository;
import com.runer.cibao.service.SignTimeRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignTimeRecordServiceImpl extends BaseServiceImp<SignTimeRecord, SignTimeRecordRepository> implements SignTimeRecordService {
    @Override
    public void saveRecord(SignTimeRecord signTimeRecord) {
        r.save(signTimeRecord);
    }

    @Override
    public List<SignTimeRecord> findByUserId(Long userId) {
       List<SignTimeRecord> list= r.findByUserIdOrderBySignDateDesc(userId);
        return list;
    }
}
