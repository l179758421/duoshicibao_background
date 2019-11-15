package com.runer.cibao.dao;

import com.runer.cibao.domain.ClassInSchool;
import com.runer.cibao.domain.repository.ClassInSchoolRepository;
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
 * @Date 2018/6/7
 **/
@Repository
public class ClassInSchoolDao {


    @Autowired
    private ClassInSchoolRepository classInSchoolRepository ;

    public Page<ClassInSchool> findClassInSchool (Long schoolId , String schoolName, String className, Pageable pageable){

     return    classInSchoolRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate>predicates =new ArrayList<>() ;
            predicates.add(criteriaBuilder.isNotNull(root.get("school").get("name")));

            if (schoolId!=null){
                predicates.add(criteriaBuilder.equal(root.get("school").get("id"),schoolId));
            }
            if (!StringUtils.isEmpty(schoolName)){
                predicates.add(criteriaBuilder.equal(root.get("school").get("name"),schoolName)) ;
            }
            if (!StringUtils.isEmpty(className)){
                predicates.add(criteriaBuilder.like(root.get("name"), JpaQueryUtil.getLikeStrAll(className)));
            }
            return JpaQueryUtil.createPredicate(predicates,criteriaQuery);

        },pageable);
    }



}
