package com.runer.cibao.service;

import com.runer.cibao.domain.UserBookUpdateRecord;
import com.runer.cibao.domain.repository.UserBookUpdateRecordRepository;

import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/11/26
 **/
public interface UserBookUpdateRecordService extends BaseService<UserBookUpdateRecord,UserBookUpdateRecordRepository> {

    /**
     * 获得个人的record；（one）
     * @param userId
     * @param bookId
     * @return
     */
    UserBookUpdateRecord findOneRecord(Long userId, Long bookId);

    /**
     * 获得个人的record ；
     * @param userId
     * @return
     */
    List<UserBookUpdateRecord> findOnePersonRecords(Long userId);
    /**
     * 更新一个记录；
     * @param userId
     * @param bookId
     * @param date
     */
    void updateOneRecord(Long userId, Long bookId, Date date) ;
}
