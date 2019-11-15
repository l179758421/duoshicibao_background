package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.OnlineTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OnlineTimeRepository extends JpaRepository<OnlineTime,Long>,JpaSpecificationExecutor<OnlineTime> {
    List<OnlineTime> findByAppUser_Id(Long userId);



}
