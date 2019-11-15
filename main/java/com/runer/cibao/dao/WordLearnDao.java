package com.runer.cibao.dao;

import com.runer.cibao.domain.person_word.WordLearn;
import com.runer.cibao.domain.repository.WordLearnRepostitory;
import com.runer.cibao.util.JpaQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.ListUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/17
 **/
@Repository
public class WordLearnDao  {

    @Autowired
    WordLearnRepostitory wordLearnRepostitory ;



    @Autowired
    EntityManager entityManager ;





   public long countWordLearn(Integer state , List<Integer> states  , Date startTime , Date endTime , Long userId ,
                              Long bookId , Long unitId  ,Integer success ){
      return wordLearnRepostitory.count((root, criteriaQuery, criteriaBuilder) -> {
        return   JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
              predicates(predicates,criteriaBuilder,root,state,states,startTime,endTime,userId,bookId,unitId ,success);

          },criteriaQuery) ;
      });
   }

   public List<WordLearn> findGroupByUserId(Long classId, Date startTime , Date endTime){
       return  wordLearnRepostitory.findAll((root, criteriaQuery,
                                             criteriaBuilder) -> JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
          // criteriaQuery.groupBy(root.get("personalLearnWord").get("appUser").get("id"));
           if (classId!=null){
               predicates.add(criteriaBuilder.equal(root.get("personalLearnWord").get("appUser").get("classInSchoolId"),classId)) ;
           }
           if (startTime!=null&&endTime!=null){
               predicates.add(criteriaBuilder.between(root.get("learnDate"),startTime,endTime) );
           }

       },criteriaQuery));
   }


    public Page<WordLearn> findWordLearn(Integer state , List<Integer> states  , Date startTime , Date endTime , Long userId ,
                                         Long bookId , Long unitId , Integer success , Pageable pageable){
        return   wordLearnRepostitory.findAll((root, criteriaQuery,
                                             criteriaBuilder) -> JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                                         criteriaQuery.groupBy(root.get("personalLearnWord").get("appUser").get("id"));
                                                 predicates(predicates,criteriaBuilder,root,state,states,startTime,endTime,userId,bookId,unitId ,success);

      },criteriaQuery),pageable);
    }



    private void  predicates(List<Predicate> predicates , CriteriaBuilder criteriaBuilder , Root root ,
                             Integer state , List<Integer> states  , Date startTime , Date endTime , Long userId ,
                             Long bookId , Long unitId ,Integer success
                             ){
        /**
         * 判空
         */
        predicates.add(criteriaBuilder.isNotNull(root.get("personalLearnWord").get("id"))) ;
        predicates.add(criteriaBuilder.isNotNull(root.get("personalLearnWord").get("bookWord").get("id"))) ;

        CriteriaBuilder a = criteriaBuilder;

        if (state!=null){
            predicates.add(criteriaBuilder.equal(root.get("state"),state));
        }

        if (!ListUtils.isEmpty(states)){
            predicates.add(root.get("state").in(states));
        }

        if (startTime!=null){
            predicates.add(criteriaBuilder.greaterThan(root.get("learnDate"),startTime)) ;
        }
        if (endTime!=null){
            predicates.add(criteriaBuilder.lessThan(root.get("learnDate"),endTime)) ;
        }
        if (userId!=null){
            predicates.add(criteriaBuilder.equal(root.get("personalLearnWord").get("appUser").get("id"),userId)) ;
        }
        if (unitId!=null){
            predicates.add(criteriaBuilder.equal(root.get("personalLearnWord").get("bookWord").get("unit").get("id"),unitId));
        }
        if (bookId!=null){
            predicates.add(criteriaBuilder.equal(root.get("personalLearnWord").get("bookWord").get("unit").get("learnBook").get("id"),bookId));
        }

        if (success!=null){
            predicates.add(criteriaBuilder.equal(root.get("isSuccess"),success));
        }


    }




}
