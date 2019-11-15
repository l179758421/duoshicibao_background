package com.runer.cibao.dao;

import com.runer.cibao.domain.ErrorRecovery;
import com.runer.cibao.domain.repository.ErrorRecoveryRepository;
import com.runer.cibao.util.JpaQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/29
 **/
@Repository
public class ErrorRecoverDao {

  @Autowired
  private ErrorRecoveryRepository errorRecoveryRepository ;



  public Page<ErrorRecovery> findErrorRecoveries(Long userId , Integer isResolved , Long replyUserId , Pageable pageable){

    return   errorRecoveryRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
          List<Predicate> predicates =new ArrayList<>() ;
          //加空判断；
          predicates.add(criteriaBuilder.isNotNull(root.get("appUser").get("name"))) ;
          predicates.add(criteriaBuilder.isNotNull(root.get("bookWord").get("wordName")));

          if (userId!=null) {
              predicates.add(criteriaBuilder.equal(root.get("appUser").get("id"),userId)) ;
          }
          if (isResolved!=null){
              predicates.add(criteriaBuilder.equal(root.get("isResolved"),isResolved));
          }
          if (replyUserId!=null){
              predicates.add(criteriaBuilder.equal(root.get("user").get("id"),replyUserId)) ;
          }
          return JpaQueryUtil.createPredicate(predicates,criteriaQuery) ;
      },pageable);


  }





}
