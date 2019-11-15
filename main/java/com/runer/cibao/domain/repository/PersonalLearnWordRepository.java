package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.person_word.PersonalLearnWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/17
 **/
public interface PersonalLearnWordRepository  extends JpaRepository<PersonalLearnWord,Long> ,JpaSpecificationExecutor<PersonalLearnWord> {

    @Query(value = "select COUNT(0) from personal_learn_word where user_id = ?1 and current_id in (select id from word_learn where learn_date between DATE_add(CURDATE(),INTERVAL 0 HOUR) and DATE_add(CURDATE(),INTERVAL 24 HOUR)  and  state in (5,6,7,8,9))" , nativeQuery = true)
    List<PersonalLearnWord> findAllByTodayCount(Long appUserId);

    @Query(value = "select SUM(t2.learn_time) from personal_learn_word as t1 , word_learn as t2 where t1.user_id = ?1 and t2.learn_date between DATE_add(CURDATE(),INTERVAL 0 HOUR) and DATE_add(CURDATE(),INTERVAL 24 HOUR)  and  t2.state in (5,6,7,8,9) and t1.current_id = t2.id",nativeQuery = true)
    Long getTotalTodayLearnTime(Long appUserId);
}

