package com.runer.cibao.dao;

import com.runer.cibao.domain.PersonalTestForBook;
import com.runer.cibao.domain.repository.PersonalTestForBookRepository;
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
public class PersonalTestForBookDao {

    @Autowired
    PersonalTestForBookRepository personalTestForBookRepository ;


    public List<PersonalTestForBook> findBooksTestOrderByDate(Long userId ,Long personalLearnBookId){
       return personalTestForBookRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {

           List<Predicate> predicates =new ArrayList<>() ;

           if (userId!=null){
               predicates.add(criteriaBuilder.equal(root.get("personalLearnBook").get("personalLearnBooks")
                       .get("appUser").get("id"),userId));
           }

           if (personalLearnBookId!= null){
               predicates.add(criteriaBuilder.equal(root.get("personalLearnBook").get("id"),personalLearnBookId));
           }

           criteriaQuery.orderBy(criteriaBuilder.desc(root.get("testDate")));
            return JpaQueryUtil.createPredicate(predicates,criteriaQuery);
        });
    }



    public List<PersonalTestForBook> findBooksTestOrderByDate2(Long userId ,Long bookId,Integer isPre){
        return personalTestForBookRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates =new ArrayList<>() ;
            if (userId!=null){
                predicates.add(criteriaBuilder.equal(root.get("personalLearnBook").get("personalLearnBooks")
                        .get("appUser").get("id"),userId));
            }
            if (bookId!= null){
                predicates.add(criteriaBuilder.equal(root.get("personalLearnBook").get("learnBook").get("id"),bookId));
            }
            if (isPre!=null){
                predicates.add(criteriaBuilder.equal(root.get("isPreLearnTest"),isPre));
            }
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("testDate")));
            return JpaQueryUtil.createPredicate(predicates,criteriaQuery);
        });
    }



    public List<PersonalTestForBook> findByUserIdAndBookIdOrderByDate(Long userId ,Long bookId){

        return personalTestForBookRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates =new ArrayList<>();

            if (userId!=null){
                predicates.add(criteriaBuilder.equal(root.get("personalLearnBook").get("personalLearnBooks")
                        .get("appUser").get("id"),userId));
            }

            if (bookId!= null){
                predicates.add(criteriaBuilder.equal(root.get("personalLearnBook").get("learnBook").get("id"),bookId));
            }

            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("testDate")));
            return JpaQueryUtil.createPredicate(predicates,criteriaQuery);
        });
    }


    public Page<PersonalTestForBook> findBooksTest(Long userId , Long bookId ,
                                                   Long personalLearnBookId , Integer isPre
            , Date bTime ,Date eTime  , Pageable pageable ){

        return  personalTestForBookRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates =new ArrayList<>() ;

            if (userId!=null){

                predicates.add(criteriaBuilder.equal(root.get("personalLearnBook").get("personalLearnBooks")
                .get("appUser").get("id"),userId));

            }
            if (bookId!=null){
                predicates.add(criteriaBuilder.equal(root.get("personalLearnBook").get("learnBook").get("id"),bookId));
            }

            if (personalLearnBookId!= null){
                predicates.add(criteriaBuilder.equal(root.get("personalLearnBook").get("id"),personalLearnBookId));
            }

            if (isPre!=null){
                predicates.add(criteriaBuilder.equal(root.get("isPreLearnTest"),isPre));
            }

            if (bTime!=null&&eTime!=null){
                predicates.add(criteriaBuilder.between(root.get("testDate"),bTime,eTime)) ;
            }

            return JpaQueryUtil.createPredicate(predicates,criteriaQuery);
        },pageable);



    }



}
