package com.runer.cibao.dao;

import com.runer.cibao.domain.SchoolMaster;
import com.runer.cibao.domain.repository.SchoolMasterRepository;
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
 * @Date 2018/6/6
 **/
@Repository
public class SchoolMasterDao {

    @Autowired
    SchoolMasterRepository schoolRepository ;

    /**
     * 查询学校 ---
     * @param schoolName
     * @param masterName
     * @param address
     * @param pageable
     * @return
     */
     public Page<SchoolMaster> findSchools (String schoolName, String masterName, String address , Pageable pageable){
       return   schoolRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
                   List<Predicate> predicates = new ArrayList<>();

                   predicates.add(criteriaBuilder.isNotNull(root.get("school").get("name")));


                   Predicate schoolPredicate = null ;

                   Predicate namePredicate = null ;

                   if (!StringUtils.isEmpty(schoolName)){
                       schoolPredicate =criteriaBuilder.like(root.get("school").get("name"), JpaQueryUtil.getLikeStrAll(schoolName));
                   }
                   if (!StringUtils.isEmpty(masterName)){
                       namePredicate =criteriaBuilder.like(root.get("name"), JpaQueryUtil.getLikeStrAll(masterName));
                   }
                   if (!StringUtils.isEmpty(address)) {
                       predicates.add(criteriaBuilder.like(root.get("address"), JpaQueryUtil.getLikeStrAll(address)));
                   }

                   if (schoolPredicate != null && namePredicate != null){
                        predicates.add(criteriaBuilder.or(schoolPredicate,namePredicate));
                   }else{
                        if (schoolPredicate!=null){
                            predicates.add(schoolPredicate);
                        }
                        if (namePredicate!=null){
                            predicates.add(namePredicate) ;
                        }
                   }

                   return JpaQueryUtil.createPredicate(predicates,criteriaQuery) ;

               },
                 pageable) ;
     }

}
