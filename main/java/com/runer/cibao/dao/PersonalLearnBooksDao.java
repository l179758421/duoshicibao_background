package com.runer.cibao.dao;

import com.runer.cibao.domain.PersonalLearnBooks;
import com.runer.cibao.domain.repository.PersonalLearnBooksRepository;
import com.runer.cibao.util.JpaQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/26
 **/
@Repository
public class PersonalLearnBooksDao {


  
    @Autowired
    PersonalLearnBooksRepository personalLearnBooksRepository ;
    
    
    public PersonalLearnBooks  findByUserId(Long userId){


        Optional<PersonalLearnBooks> personalOptional = personalLearnBooksRepository.findOne((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (userId != null) {
              predicates.add(criteriaBuilder.equal(root.get("appUser").get("id"),userId)) ;
            }
            return JpaQueryUtil.createPredicate(predicates, criteriaQuery);
        });
        

        if (personalOptional.isPresent()){
            return  personalOptional.get() ;
        }else{
            return  null ;
        }


    }
    
    
    
    

     




}
