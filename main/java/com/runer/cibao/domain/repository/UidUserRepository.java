package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.UidUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UidUserRepository extends JpaRepository<UidUser,Long>,JpaSpecificationExecutor<UidUser> {

}
