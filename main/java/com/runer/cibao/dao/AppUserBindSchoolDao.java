package com.runer.cibao.dao;

import com.runer.cibao.domain.AppUserBindSchool;
import com.runer.cibao.domain.repository.AppUserBindSchoolRepository;
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
 * @Date 2018/7/2
 **/
@Repository
public class AppUserBindSchoolDao {


    @Autowired
    AppUserBindSchoolRepository appUserBindSchoolRepository ;


   public Page<AppUserBindSchool> findAppUserBinds(String studentName ,Long schoolId , Long userId , Integer state , Pageable pageable){

    return     appUserBindSchoolRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {

        List<Predicate> predicates =new ArrayList<>() ;
        predicates.add(criteriaBuilder.isNotNull(root.get("appUser").get("name")));

        if (!StringUtils.isEmpty(studentName)){
            predicates.add(criteriaBuilder.like(root.get("appUser").get("name"), JpaQueryUtil.getLikeStrAll(studentName)));
        }
        if (schoolId!=null){
            predicates.add(criteriaBuilder.equal(root.get("school").get("id"),schoolId)) ;
        }
        if (userId!=null){
            predicates.add(criteriaBuilder.equal(root.get("appUser").get("id"),userId)) ;
         }
     if (state!=null){
            predicates.add(criteriaBuilder.equal(root.get("state"),state)) ;
     }

        return JpaQueryUtil.createPredicate(predicates,criteriaQuery) ;

    },pageable) ;


    }




}
