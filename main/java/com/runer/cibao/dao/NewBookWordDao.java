package com.runer.cibao.dao;

import com.runer.cibao.domain.NewBookWord;
import com.runer.cibao.domain.repository.NewBookWordRepository;
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
public class NewBookWordDao {

    @Autowired
    NewBookWordRepository newBookWordRepository ;



    public long countNewBookWords(Long userId ,Integer isNow ){
        return  newBookWordRepository.count((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates =new ArrayList<>() ;
            predicates.add(criteriaBuilder.isNotNull(root.get("bookWord").get("bookName")));
            predicates.add(criteriaBuilder.isNotNull(root.get("appUser").get("name")));
            if (userId!=null){
                predicates.add(criteriaBuilder.equal(root.get("appUser").get("id"),userId));
            }
            if (isNow!=null) {
                predicates.add(criteriaBuilder.equal(root.get("isNowWord"), isNow));
            }
            return  JpaQueryUtil.createPredicate(predicates,criteriaQuery) ;
        });
    }

    public Page<NewBookWord> findNewbookWords(Long userId , Long bookWordId ,  Integer isNow , Pageable pageable ){
      return   newBookWordRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates =new ArrayList<>() ;
            predicates.add(criteriaBuilder.isNotNull(root.get("bookWord").get("bookName")));
            predicates.add(criteriaBuilder.isNotNull(root.get("appUser").get("name")));
            if (userId!=null){
                predicates.add(criteriaBuilder.equal(root.get("appUser").get("id"),userId));
            }
            if (isNow!=null) {
                predicates.add(criteriaBuilder.equal(root.get("isNowWord"), isNow));
            }
            if (bookWordId!=null){
                predicates.add(criteriaBuilder.equal(root.get("bookWord").get("id"),bookWordId));
            }
            return JpaQueryUtil.createPredicate(predicates,criteriaQuery) ;
        },pageable);
    }


}
