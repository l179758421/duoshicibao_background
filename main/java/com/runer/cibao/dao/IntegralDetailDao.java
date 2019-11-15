package com.runer.cibao.dao;

import com.runer.cibao.domain.IntegralDetail;
import com.runer.cibao.domain.repository.IntegralDetailRepository;
import com.runer.cibao.util.JpaQueryUtil;
import com.runer.cibao.util.NormalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class IntegralDetailDao {
    @Autowired
    IntegralDetailRepository integralDetailRepository;


    /**
     * 获得详细的列表；
     * @param type
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    public List<IntegralDetail> findIntegralDetail(int type , Long userId , Date startDate , Date endDate  ){

       return integralDetailRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
           return   JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(criteriaBuilder.isNotNull(root.get("integral").get("id")));
                predicates.add(criteriaBuilder.isNotNull(root.get("integral").get("appUser").get("name"))) ;
                Date[] rangeDates = NormalUtil.rangeDate(startDate, endDate);;
                if (rangeDates!=null){
                    predicates.add(criteriaBuilder.between(root.get("createTime"),rangeDates[0],rangeDates[1]));
                }
                predicates.add(criteriaBuilder.equal(root.get("type"),type)) ;
                if (userId!=null) {
                    predicates.add(criteriaBuilder.equal(root.get("integral").get("appUser").get("id"), userId));
                }
            },criteriaQuery);
        });
    }



    public Page<IntegralDetail> findIntegralDetail(Long integralId , Pageable pageable ){
        return   integralDetailRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates =new ArrayList<>() ;
            if (integralId!=null){
                predicates.add(criteriaBuilder.equal(root.get("integral").get("id"),integralId));
            }
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")));
            return JpaQueryUtil.createPredicate(predicates,criteriaQuery) ;

        },pageable);



    }
}
