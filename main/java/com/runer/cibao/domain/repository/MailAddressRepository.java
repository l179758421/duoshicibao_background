package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.MailAddres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/21
 **/
public interface MailAddressRepository  extends JpaRepository<MailAddres,Long> ,JpaSpecificationExecutor<MailAddres> {




}
