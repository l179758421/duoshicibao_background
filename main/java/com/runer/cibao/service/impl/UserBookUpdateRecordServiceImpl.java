package com.runer.cibao.service.impl;

import com.runer.cibao.domain.UserBookUpdateRecord;
import com.runer.cibao.domain.repository.UserBookUpdateRecordRepository;
import com.runer.cibao.service.UserBookUpdateRecordService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/11/26
 **/
@Service
public class UserBookUpdateRecordServiceImpl  extends BaseServiceImp<UserBookUpdateRecord,UserBookUpdateRecordRepository> implements UserBookUpdateRecordService {
    @Override
    public UserBookUpdateRecord findOneRecord(Long userId, Long bookId) {
        return r.findUserBookUpdateRecordByUserIdAndBookId(userId,bookId);
    }

    @Override
    public List<UserBookUpdateRecord> findOnePersonRecords(Long userId) {
        return r.findUserBookUpdateRecordsByUserId(userId);
    }

    @Override
    public void updateOneRecord(Long userId, Long bookId, Date date) {
        UserBookUpdateRecord  userBookUpdateRecord= findOneRecord(userId,bookId) ;
        if (userBookUpdateRecord==null){
            userBookUpdateRecord =new UserBookUpdateRecord();
            userBookUpdateRecord.setBookId(bookId);
            userBookUpdateRecord.setUserId(userId);
        }
        userBookUpdateRecord.setUpdateOrDownloadTime(date);
        r.saveAndFlush(userBookUpdateRecord) ;
    }
}
