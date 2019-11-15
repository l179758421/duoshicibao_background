package com.runer.cibao.dao;

import com.runer.cibao.domain.OneWord;
import com.runer.cibao.domain.Word;
import com.runer.cibao.domain.repository.OneWordRepository;
import com.runer.cibao.domain.repository.WordRepository;
import com.runer.cibao.util.JpaQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/14
 **/
@Repository
public class OneWordDao {


    @Autowired
    OneWordRepository oneWordRepository ;

    public  Page<OneWord> findOneWords(String content, Pageable pageable){
     return    oneWordRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates =new ArrayList<>() ;

            if (!StringUtils.isEmpty(content)){
                predicates.add(criteriaBuilder.like(root.get("content"), JpaQueryUtil.getLikeStrAll(content)));
            }

            return JpaQueryUtil.createPredicate(predicates,criteriaQuery) ;

        },pageable);
    }



}
