package com.runer.cibao.dao;

import com.runer.cibao.domain.PunchCard;
import com.runer.cibao.domain.repository.PunshCardRepository;
import com.runer.cibao.util.JpaQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/29
 **/
@Repository
public class PunchCardDao {


    @Autowired
    PunshCardRepository punshCardRepository ;

    public List<PunchCard> findCard(Long userId , Date bDate ,Date eDate ){
     return    punshCardRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate>predicates=new ArrayList<>() ;
            if (userId!=null){
                predicates.add(criteriaBuilder.equal(root.get("appUser").get("id"),userId)) ;
            }
            if (bDate!=null&&eDate!=null){
                predicates.add(criteriaBuilder.between(root.get("punchDate"),bDate,eDate) );
            }
            return JpaQueryUtil.createPredicate(predicates,criteriaQuery) ;
        });
    }

    public List<PunchCard> findCardByUserId(Long userId){
        return    punshCardRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate>predicates=new ArrayList<>() ;
            if (userId!=null){
                predicates.add(criteriaBuilder.equal(root.get("appUser").get("id"),userId)) ;
            }
            return JpaQueryUtil.createPredicate(predicates,criteriaQuery) ;
        });
    }





}
