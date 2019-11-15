package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.AdminLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdminLevelRepository extends JpaRepository<AdminLevel,Long>,JpaSpecificationExecutor<AdminLevel> {
}
