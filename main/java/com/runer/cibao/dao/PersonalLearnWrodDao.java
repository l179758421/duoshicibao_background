package com.runer.cibao.dao;

import com.runer.cibao.domain.person_word.PersonalLearnWord;
import com.runer.cibao.domain.repository.PersonalLearnWordRepository;
import com.runer.cibao.util.JpaQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/17
 **/
@Repository
public class PersonalLearnWrodDao {


    @Autowired
    PersonalLearnWordRepository personalLearnWordRepository ;

    public PersonalLearnWord findByWordId(Long appUserId ,Long wordId){
        if (appUserId==null||wordId==null){
            return  null ;
        }
        Optional<PersonalLearnWord> optonal = personalLearnWordRepository.findOne((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(criteriaBuilder.equal(root.get("appUser").get("id"),appUserId)) ;
                predicates.add(criteriaBuilder.equal(root.get("bookWord").get("id"),wordId)) ;
            }, criteriaQuery);
        });
        if (optonal.isPresent()){
            return optonal.get() ;
        }
        return  null ;
    }

}
