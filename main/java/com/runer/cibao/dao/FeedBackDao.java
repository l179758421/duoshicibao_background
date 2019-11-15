package com.runer.cibao.dao;

import com.runer.cibao.domain.FeedBack;
import com.runer.cibao.domain.repository.FeedBackRepository;
import com.runer.cibao.util.JpaQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/12
 **/
@Repository
public class FeedBackDao {


    @Autowired
    FeedBackRepository feedBackRepository ;


    public Page<FeedBack> findFeedBacks(String  schoolId ,Long askUserId , String askUserName , Long answerUserId , String anserUserName , Integer ifSolve,Pageable pageable){
      return   feedBackRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {

          List<Predicate> predicateList =new ArrayList<>() ;


          if (schoolId!=null && schoolId!=""){
              predicateList.add(criteriaBuilder.equal(root.get("askUser").get("schoolId"),schoolId)) ;
          }

          if (askUserId!=null) {
              predicateList.add(criteriaBuilder.equal(root.get("askUser").get("id"), askUserId));
          }
          if (!StringUtils.isEmpty(askUserName)){
              predicateList.add(criteriaBuilder.like(root.get("askUser").get("name"), JpaQueryUtil.getLikeStrAll(askUserName)));
          }

          if (!StringUtils.isEmpty(anserUserName)){
              predicateList.add(criteriaBuilder.like(root.get("answerUser").get("name"), JpaQueryUtil.getLikeStrAll(anserUserName)));
          }

          if (answerUserId!=null){
              predicateList.add(criteriaBuilder.equal(root.get("answerUser").get("id"),answerUserId));
          }

          if (ifSolve!=null){
              predicateList.add(criteriaBuilder.equal(root.get("ifSolve"),ifSolve));
          }

          return JpaQueryUtil.createPredicate(predicateList,criteriaQuery);

        },pageable);


    }


}
