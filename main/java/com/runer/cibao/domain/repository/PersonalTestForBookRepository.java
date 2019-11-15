package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.PersonalTestForBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/25
 **/
public interface PersonalTestForBookRepository extends JpaRepository<PersonalTestForBook,Long> ,JpaSpecificationExecutor<PersonalTestForBook> {

    @Query(value = "select * from presonal_test_for_book where user_id = ?1 and test_date between DATE_ADD(CURDATE() ,interval 0 HOUR) and DATE_ADD(CURDATE() ,interval 24 HOUR)",nativeQuery = true)
    PersonalTestForBook findTodayTestForBook(Long appUserId);
}
