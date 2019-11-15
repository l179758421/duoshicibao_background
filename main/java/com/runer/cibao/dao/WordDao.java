package com.runer.cibao.dao;

import com.runer.cibao.domain.Word;
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
public class WordDao  {


    @Autowired
    WordRepository wordRepository ;

    public  Page<Word> findWords(String wordName , String rootAffixes , String wordGetName, Pageable pageable){
     return    wordRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates =new ArrayList<>() ;

            if (!StringUtils.isEmpty(wordName)){
                predicates.add(criteriaBuilder.like(root.get("word"), JpaQueryUtil.getLikeStrAll(wordName)));
            }

            if (!StringUtils.isEmpty(rootAffixes)){
                predicates.add(criteriaBuilder.like(root.get("rootAffixes"), JpaQueryUtil.getLikeStrAll(rootAffixes)));
            }

            if (!StringUtils.isEmpty(wordGetName)){
                predicates.add(criteriaBuilder.equal(root.get("word"),wordGetName));
            }

            return JpaQueryUtil.createPredicate(predicates,criteriaQuery) ;

        },pageable);
    }


    public  List<Word> findWordAudioUrl(){
        return  wordRepository.findAll((root, criteriaQuery,
                                        criteriaBuilder) -> JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
            Predicate wordAudioUrl = criteriaBuilder.isNotNull(root.get("wordAudioUrl"));//英
            Predicate usaAudioUrl = criteriaBuilder.isNotNull(root.get("usaAudioUrl"));//美
            predicates.add(criteriaBuilder.or(wordAudioUrl,usaAudioUrl));
        },criteriaQuery));
    }




}
