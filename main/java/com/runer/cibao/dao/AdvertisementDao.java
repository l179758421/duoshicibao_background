package com.runer.cibao.dao;

import com.runer.cibao.domain.Advertisement;
import com.runer.cibao.domain.repository.AdvertisementRepository;
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
 * @Date 2018/6/23
 **/
@Repository
public class AdvertisementDao {

   @Autowired
   AdvertisementRepository advertisementRepository ;


   public List<Advertisement> findAds(String title , Integer type ,boolean  containsOutDate ){
      return   advertisementRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {

           List<Predicate> predicates =new ArrayList<>() ;

           if (type!=null) {
               predicates.add(criteriaBuilder.equal(root.get("type"), type));
           }
           if (!StringUtils.isEmpty(title)){
               predicates.add(criteriaBuilder.like(root.get("title"), JpaQueryUtil.getLikeStrAll(title)));
           }

           if (containsOutDate){
               predicates.add(criteriaBuilder.isNotNull(root.get("endTime"))) ;
               predicates.add(criteriaBuilder.greaterThan(root.get("endTime"),new Date()));
           }
           return JpaQueryUtil.createPredicate(predicates,criteriaQuery);


       });

   }

    public Page<Advertisement> findAds2(String title , Integer type , boolean  containsOutDate , Pageable pageable ){
        Page<Advertisement> page = (Page<Advertisement>) advertisementRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates =new ArrayList<>() ;

            if (type!=null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), type));
            }
            if (!StringUtils.isEmpty(title)){
                predicates.add(criteriaBuilder.like(root.get("title"), JpaQueryUtil.getLikeStrAll(title)));
            }

            if (containsOutDate){
                predicates.add(criteriaBuilder.isNotNull(root.get("endTime"))) ;
                predicates.add(criteriaBuilder.greaterThan(root.get("endTime"),new Date()));
            }
            return JpaQueryUtil.createPredicate(predicates, criteriaQuery);

        }, pageable);

        return page;

    }





}
