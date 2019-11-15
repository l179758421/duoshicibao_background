package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.PersonalLearnBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/25
 **/
public interface PersonalLearnBooksRepository extends JpaRepository<PersonalLearnBooks,Long> ,JpaSpecificationExecutor<PersonalLearnBooks> {
}
