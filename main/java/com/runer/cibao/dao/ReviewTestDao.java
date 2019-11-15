package com.runer.cibao.dao;

import com.runer.cibao.domain.ReviewTest;
import com.runer.cibao.domain.repository.ReviewTestRepository;
import com.runer.cibao.util.JpaQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewTestDao {

    @Autowired
    ReviewTestRepository reviewTestRepository;

   public List<ReviewTest> findByUserIdAndUnitId(Long userId,Long unitId){
        return reviewTestRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates =new ArrayList<>() ;
            if (userId!=null){
                predicates.add(criteriaBuilder.equal(root.get("userId"),userId));
            }
            if (unitId!= null){
                predicates.add(criteriaBuilder.equal(root.get("unitId"),unitId));
            }
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("testDate")));
            return JpaQueryUtil.createPredicate(predicates,criteriaQuery);
        });
    }

    /**
     *
     * @param userId
     * @param unitId
     * @return
     */
    public long countReviewTestNum(Long userId ,Long unitId ){
        return reviewTestRepository.count((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates =new ArrayList<>() ;
            if (userId!=null){
                predicates.add(criteriaBuilder.equal(root.get("userId"),userId));
            }
            if (unitId!= null){
                predicates.add(criteriaBuilder.equal(root.get("unitId"),unitId));
            }
            return JpaQueryUtil.createPredicate(predicates,criteriaQuery);
        });
    }
}
