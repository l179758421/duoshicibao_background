package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.MessageRead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/7/5
 **/
public interface MessageReadRepository  extends JpaRepository<MessageRead,Long> ,JpaSpecificationExecutor<MessageRead> {
}
