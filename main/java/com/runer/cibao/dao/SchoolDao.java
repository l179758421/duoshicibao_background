package com.runer.cibao.dao;
import com.runer.cibao.domain.School;
import com.runer.cibao.domain.repository.SchoolRepository;
import com.runer.cibao.util.JpaQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/6
 **/
@Repository
public class SchoolDao {

    @Autowired
    SchoolRepository schoolRepository ;

    public List<School> findSchoolsCanAdd() {

     return   schoolRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
         return    JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
              predicates.add(criteriaBuilder.isNull(root.get("schoolMaster").get("name")));
         },criteriaQuery) ;

        }) ;

    }




    public  Page<School> findSchool (String schoolName, Long cityId, Long provinceId, Long areaId, String address, String schoolMasterName,
                                     Date startTiem, Date endTime , Long agentsId , Pageable pageable) {


        Page<School> page = schoolRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            /**
             * 学校的校长
             */
            //predicates.add(criteriaBuilder.isNotNull(root.get("schoolMaster").get("id")));
            if (!StringUtils.isEmpty(schoolName)) {
                predicates.add(criteriaBuilder.like(root.get("name"), JpaQueryUtil.getLikeStrAll(schoolName)));
            }
            if (areaId!=null){
                predicates.add(criteriaBuilder.equal(root.get("areaId"),areaId));
            }
            if (cityId!=null){
                predicates.add(criteriaBuilder.equal(root.get("cityId"),cityId)) ;
            }
            if (provinceId!=null){
                predicates.add(criteriaBuilder.equal(root.get("provinceId"),provinceId));
            }
            if (!StringUtils.isEmpty(address)){
                predicates.add(criteriaBuilder.like(root.get("address"), JpaQueryUtil.getLikeStrAll(address)));
            }
            if (!StringUtils.isEmpty(schoolMasterName)){
                predicates.add(criteriaBuilder.isNotNull(root.get("schoolMaster").get("id")));
                predicates.add(criteriaBuilder.like(root.get("schoolMaster").get("name"), JpaQueryUtil.getLikeStrAll(schoolMasterName)));
            }

            if (startTiem!=null){
                if (endTime==null) {
                    predicates.add(criteriaBuilder.between(root.get("createTime"), startTiem, new Date()));
                }
            }
            if (endTime!=null){
                if (startTiem==null) {
                    predicates.add(criteriaBuilder.between(root.get("createTime"), null, endTime));
                }
            }
            if (startTiem!=null&&endTime!=null){
                predicates.add(criteriaBuilder.between(root.get("createTime"),startTiem,endTime));
            }

            if (agentsId!=null){
                predicates.add(criteriaBuilder.equal(root.get("agents").get("id"),agentsId)) ;
            }
            return JpaQueryUtil.createPredicate(predicates, criteriaQuery);

        }, pageable);


        return  page;

    }


    public List getAll() {
     return schoolRepository.findAll();
    }
}
