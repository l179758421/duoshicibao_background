package com.runer.cibao.util;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author szhua
 * @Description:smartcommunity==
 * @Date 2018/5/19
 **/
public class JpaQueryUtil{



    public static Predicate createPredicate(List<Predicate> predicates , CriteriaQuery criteriaQuery){
        if (predicates==null){
            predicates =new ArrayList<>() ;
        }
        if (criteriaQuery==null){
            return null ;
        }
        return  criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction(); 
    }
    public static String getLikeStrAll(String str){
        return  "%"+str+"%" ;
    }
    public static String getLikeStart(String str){
        return str+"%" ;
    }
    public static String getLikeEnd(String str){
        return  "%"+str ;
    }
    /**
     * 方便jpa的查询
     */
    public interface  JpaDataInputQueryCall {
        void  inputQuery(List<Predicate> predicates, CriteriaQuery query);
    }

    public static Predicate jpaDataInputQuery( JpaDataInputQueryCall call ,CriteriaQuery query ){
        List<Predicate> predicates =new ArrayList<>();
        call.inputQuery(predicates,query);
        return   JpaQueryUtil.createPredicate(predicates,query) ;
    }


}
