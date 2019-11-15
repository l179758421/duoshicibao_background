package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/4
 **/
public interface UserRepository  extends JpaRepository<User,Long> ,JpaSpecificationExecutor<User> {

    User findUserByLoginName(String name);


}
