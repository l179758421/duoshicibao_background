package com.runer.cibao.dao;

import com.runer.cibao.domain.Teacher;
import com.runer.cibao.domain.repository.TeacherRepository;
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
 * @Date 2018/7/3
 **/
@Repository
public class TeacherDao {


    @Autowired
    TeacherRepository teacherRepository ;

    public Page<Teacher> findTeachers(Long schoolId , String teacherName , String schoolUid  , Long classId , Pageable pageable){


    return     teacherRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates =new ArrayList<>() ;

            if (!StringUtils.isEmpty(teacherName)){
                predicates.add(criteriaBuilder.like(root.get("name"), JpaQueryUtil.getLikeStrAll(teacherName)));
            }
            if (schoolId!=null){
                predicates.add(criteriaBuilder.equal(root.get("school").get("id"),schoolId)) ;
            }
            if (!StringUtils.isEmpty(schoolUid)){
                predicates.add(criteriaBuilder.equal(root.get("school").get("uid"),schoolUid));
            }
            if (classId!=null){
                predicates.add(criteriaBuilder.equal(root.get("classInSchool").get("id"),classId)) ;
            }

            return JpaQueryUtil.createPredicate(predicates,criteriaQuery) ;
        },pageable);




    }





}
