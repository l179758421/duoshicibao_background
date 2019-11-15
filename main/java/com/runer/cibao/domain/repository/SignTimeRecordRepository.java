package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.SignTimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SignTimeRecordRepository extends JpaRepository<SignTimeRecord,Long>,JpaSpecificationExecutor<SignTimeRecord> {
   List<SignTimeRecord> findByUserIdOrderBySignDateDesc(Long userId);
}
