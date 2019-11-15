package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/7/5
 **/
public interface MessageRepository  extends JpaRepository<Message,Long> ,JpaSpecificationExecutor<Message> {

}
