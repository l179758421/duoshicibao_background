package com.runer.cibao.dao;

import com.runer.cibao.domain.PersonalTestForUnit;
import com.runer.cibao.domain.repository.PersonalTestForUnitRepository;
import com.runer.cibao.util.JpaQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/25
 **/
@Repository
public class PersonalTestForUnitDao {

    @Autowired
    PersonalTestForUnitRepository personalTestForUnitRepository ;


    public List<PersonalTestForUnit> findBooksTestOrderByDate(Long userId , Long personalLearnUnitId){

        return personalTestForUnitRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates =new ArrayList<>() ;

            if (userId!=null){
                predicates.add(criteriaBuilder.equal(root.get("personalLearnUnit").
                        get("personalLearnBook").get("personalLearnBooks").get("appUser").get("id"),userId));
            }

            if (personalLearnUnitId!= null){
                predicates.add(criteriaBuilder.equal(root.get("personalLearnUnit").get("id"),personalLearnUnitId));
            }

            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("testDate")));
            return JpaQueryUtil.createPredicate(predicates,criteriaQuery);
        });
    }


    public Page<PersonalTestForUnit> findUnitTestOrderByDate(Long userId , Long unitId,Pageable pageable){

        return personalTestForUnitRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates =new ArrayList<>() ;

            if (userId!=null){
                predicates.add(criteriaBuilder.equal(root.get("personalLearnUnit").
                        get("personalLearnBook").get("personalLearnBooks").get("appUser").get("id"),userId));
            }

            if (unitId!= null){
                predicates.add(criteriaBuilder.equal(root.get("personalLearnUnit").
                        get("bookUnit").get("id"),unitId));
            }

            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("testDate")));
            return JpaQueryUtil.createPredicate(predicates,criteriaQuery);
        },pageable);
    }





    public Page<PersonalTestForUnit>  findPersionUnitsTest(Long unitID , Long userId ,Long personalLearnUnitId , Date bTime ,Date eTime ,Integer isPre , Pageable pageable){
      return   personalTestForUnitRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList =new ArrayList<>() ;
            if (userId!=null){
                predicateList.add(criteriaBuilder.equal(root.get("personalLearnUnit").
                        get("personalLearnBook").get("personalLearnBooks").get("appUser").get("id"),userId));
            }
            if (unitID!=null){
                predicateList.add(criteriaBuilder.equal(root.get("personalLearnUnit").
                        get("bookUnit").get("id"),unitID));
            }
            if (personalLearnUnitId!=null){
                predicateList.add(criteriaBuilder.equal(root.get("personalLearnUnit").get("id"),personalLearnUnitId));
            }

            if (isPre!=null){
                predicateList.add(criteriaBuilder.equal(root.get("isPreLearnTest"),isPre)) ;
            }
          if (bTime!=null&&eTime!=null){
              predicateList.add(criteriaBuilder.between(root.get("testDate"),bTime,eTime)) ;
          }

          return JpaQueryUtil.createPredicate(predicateList,criteriaQuery) ;
        },pageable);

    }





}
