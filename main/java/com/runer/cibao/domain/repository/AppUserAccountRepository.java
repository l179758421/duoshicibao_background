package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.AppUserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/27
 **/
public interface AppUserAccountRepository extends JpaRepository<AppUserAccount,Long> ,JpaSpecificationExecutor<AppUserAccount> {

}
