package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.PersonlLearnInfoBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/9/5
 **/
public interface PersonlLearnInfoRepository  extends JpaRepository<PersonlLearnInfoBean,Long> ,JpaSpecificationExecutor<PersonlLearnInfoBean> {


   List<PersonlLearnInfoBean> findAllByAppUserId(Long userId);

   @Query(value = "select distinct t.user_id from " +
           "personal_learn_info t LEFT JOIN app_user t1 on t.user_id = t1.id where t1.class_id = ?1 and t.date BETWEEN ?2 and ?3 " ,nativeQuery = true)
   List<PersonlLearnInfoBean> findByLearnUser(Long classId, Date startDate, Date endDate);
}
