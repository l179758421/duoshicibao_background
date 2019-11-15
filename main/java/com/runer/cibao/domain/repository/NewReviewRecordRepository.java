package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.person_word.NewReviewRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/9/3
 **/
public interface NewReviewRecordRepository extends JpaRepository<NewReviewRecord,Long> ,JpaSpecificationExecutor<NewReviewRecord> {


}
