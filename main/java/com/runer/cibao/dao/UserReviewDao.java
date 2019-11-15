package com.runer.cibao.dao;

import com.runer.cibao.domain.UserRevivews;
import com.runer.cibao.domain.repository.UserReviewsRepostory;
import com.runer.cibao.util.JpaQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/30
 **/
@Repository
public class UserReviewDao {

    @Autowired
    UserReviewsRepostory userReviewsRepostory ;

     public UserRevivews findOne(Long bookId , Long unitId , Long userId , Integer type ){
        Optional<UserRevivews> oneExists = userReviewsRepostory.findOne((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                if (bookId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("bookId"), bookId));
                }
                if (unitId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("unitId"), unitId));
                }
                if (userId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("appUserId"), userId));
                }
                if (type != null) {
                    predicates.add(criteriaBuilder.equal(root.get("type"), type));
                }
            }, criteriaQuery);
        });

        if (oneExists.isPresent()){
            return  oneExists.get();
        }else{
            return  null ;
        }


    }



  public   List<UserRevivews> findUserReviews(Long bookId , Long unitId , Long userId  , Integer type ){
       return userReviewsRepostory.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                if (bookId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("bookId"), bookId));
                }
                if (unitId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("unitId"), unitId));
                }
                if (userId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("appUserId"), userId));
                }
                if (type!=null){
                    predicates.add(criteriaBuilder.equal(root.get("type"),type)) ;
                }
            }, criteriaQuery);

        });
    }




}
