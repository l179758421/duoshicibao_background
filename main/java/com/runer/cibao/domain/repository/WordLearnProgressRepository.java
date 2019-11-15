package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.person_word.WordLearnProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/17
 **/
public interface  WordLearnProgressRepository extends JpaRepository<WordLearnProgress,Long> ,JpaSpecificationExecutor<WordLearnProgress> {

    /**
     * 通过appUserId和bookUnitid进行查找；
     * @param appUserId
     * @param bookUnitId
     * @return
     */
    List<WordLearnProgress>  findWordLearnProgressesByUserIdAndUnitId(Long appUserId, Long bookUnitId);




}
