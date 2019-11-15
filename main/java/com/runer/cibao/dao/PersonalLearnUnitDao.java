package com.runer.cibao.dao;

import com.runer.cibao.domain.PersonalLearnUnit;
import com.runer.cibao.domain.repository.BookWordRepository;
import com.runer.cibao.domain.repository.PersonalLearnUnitReposiory;
import com.runer.cibao.util.JpaQueryUtil;
import com.runer.cibao.util.machine.IdsMachine;
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
 * @Date 2018/6/26
 **/
@Repository
public class PersonalLearnUnitDao {


    @Autowired
    PersonalLearnUnitReposiory personalLearnUnitReposiory ;

    @Autowired
    BookWordRepository bookWordRepository;


    public long findCurrentUnitNum(Long wordId ,Long bookId ){
        return bookWordRepository.count((root, criteriaQuery, criteriaBuilder) -> {
            return   JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(criteriaBuilder.equal(root.get("unit").get("id"),bookId));
                predicates.add(criteriaBuilder.le(root.get("id"),wordId)) ;
            },criteriaQuery);
        });
    }
    public long findLeftUNitNum(Long wordId ,Long bookId ){
        return bookWordRepository.count((root, criteriaQuery, criteriaBuilder) -> {
            return   JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(criteriaBuilder.equal(root.get("unit").get("id"),bookId));
                predicates.add(criteriaBuilder.ge(root.get("id"),wordId)) ;
            },criteriaQuery);
        });
    }

    public long findALlCount(Long wordId ,Long bookId ){
        return bookWordRepository.count((root, criteriaQuery, criteriaBuilder) -> {
            return   JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(criteriaBuilder.equal(root.get("unit").get("id"),bookId));
            },criteriaQuery);
        });
    }



    public List<PersonalLearnUnit> findByBookWord(Long  unitId  ,Long userId ,Long personalBooKId){
       return personalLearnUnitReposiory.findAll((root, criteriaQuery, criteriaBuilder) -> {
           return  JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
               predicates.add(criteriaBuilder.equal(root.get("bookUnit").get("id"),unitId)) ;
               if (userId!=null) {
                   predicates.add(criteriaBuilder.equal(root.get("personalLearnBook").get("personalLearnBooks").get("appUser").get("id"), userId));
               }
               if (personalBooKId!=null){
                   predicates.add(criteriaBuilder.equal(root.get("personalLearnBook").get("id"),personalBooKId));
               }
           },criteriaQuery);
       });
    }

    public List<PersonalLearnUnit> findByIds(Long appUserId ,String unitIds){

      return   personalLearnUnitReposiory.findAll((root, criteriaQuery, criteriaBuilder) -> {
          return  JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
              predicates.add(criteriaBuilder.isNotNull(root.get("bookUnit").get("name")));
              predicates.add(criteriaBuilder.isNotNull(root.get("personalLearnBook")));
              predicates.add(criteriaBuilder.isNotNull(root.get("personalLearnBook").get("personalLearnBooks")));
              predicates.add(criteriaBuilder.isNotNull(root.get("personalLearnBook").get("personalLearnBooks").get("appUser").get("name")));
              predicates.add(criteriaBuilder.equal(root.get("personalLearnBook").get("personalLearnBooks").get("appUser").get("id"),appUserId));
              predicates.add(root.get("bookUnit").get("id").in(new IdsMachine().deparseIds(unitIds)));
          },criteriaQuery);
        });
    }


    public Page<PersonalLearnUnit> findPersonalLearnUnits(Long userId ,Long bookId ,Long personalLearnBookId
            ,Integer isPassed,Integer isCurrentLearnedUnit ,Integer isFinished ,Integer reviewTestState
    ,Pageable pageable
    ){
       return personalLearnUnitReposiory.findAll( (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate>  predicates =new ArrayList<>() ;

            predicates.add(criteriaBuilder.isNotNull(root.get("bookUnit").get("name"))) ;
            predicates.add(criteriaBuilder.isNotNull(root.get("bookUnit").get("learnBook").get("bookName"))) ;

            //复习的状态；==不等于；
            if (reviewTestState!=null){
                predicates.add(criteriaBuilder.notEqual(root.get("reviewTestState"),reviewTestState)) ;
            }
            if (isFinished!=null){
                predicates.add(criteriaBuilder.equal(root.get("isFinished"),isFinished)) ;
            }
            if (userId!=null){
                predicates.add(criteriaBuilder.equal(root.get("personalLearnBook").get("personalLearnBooks").get("appUser").get("id"),userId)) ;
            }
            if (bookId!= null){
                predicates.add(criteriaBuilder.equal(root.get("personalLearnBook").get("learnBook").get("id"),bookId));
            }
            if (personalLearnBookId!=null){
                predicates.add(criteriaBuilder.equal(root.get("personalLearnBook").get("id"),personalLearnBookId)) ;
            }
            if (isPassed!=null){
                predicates.add(criteriaBuilder.equal(root.get("isPassed"),isPassed));
            }
            if (isCurrentLearnedUnit!=null){
                predicates.add(criteriaBuilder.equal(root.get("isCurrentLearnedUnit"),isCurrentLearnedUnit)) ;
            }
            return JpaQueryUtil.createPredicate(predicates,criteriaQuery) ;

        },pageable) ;



    }






}
