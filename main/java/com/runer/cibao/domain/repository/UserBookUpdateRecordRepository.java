package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.UserBookUpdateRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/11/26
 **/
public interface UserBookUpdateRecordRepository extends JpaRepository<UserBookUpdateRecord,Long> ,JpaSpecificationExecutor<UserBookUpdateRecord> {

    /**
     * 查找
     * @param userId
     * @param bookId
     * @return
     */
    UserBookUpdateRecord findUserBookUpdateRecordByUserIdAndBookId(Long userId, Long bookId);

    List<UserBookUpdateRecord>  findUserBookUpdateRecordsByUserId(Long userId);

    /**
     *   : ARGS : appUserId:["24204"]
     *   : ARGS : userId:["24204"]
     *   : ARGS : time:["148264"]
     *   : ARGS : unitId:["23124"]
     *   : ARGS : state:["1"]
     *   : ARGS : leftWords:["23091,23093,23099,23102,23104,23111,"]
     */

}
