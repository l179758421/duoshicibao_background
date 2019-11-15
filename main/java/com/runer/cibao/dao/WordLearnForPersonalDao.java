package com.runer.cibao.dao;

import com.runer.cibao.domain.WordLearnForPersonal;
import com.runer.cibao.domain.repository.WordLearnForPersonalRepostory;
import com.runer.cibao.util.JpaQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/27
 **/
@Repository
public class WordLearnForPersonalDao  {

    @Autowired
    WordLearnForPersonalRepostory wordLearnForPersonalRepostory ;

     public List<WordLearnForPersonal>  findByBookIds(List<Long> bookIds){
       return wordLearnForPersonalRepostory.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(root.get("bookId").in(bookIds));
            },criteriaQuery);
        });
    }


}
