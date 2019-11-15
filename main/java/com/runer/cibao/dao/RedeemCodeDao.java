package com.runer.cibao.dao;

import com.runer.cibao.Config;
import com.runer.cibao.domain.RedeemCode;
import com.runer.cibao.domain.repository.RedeemCodeRepository;
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
 * @Date 2018/6/7
 **/

@Repository
public class RedeemCodeDao {

    @Autowired
    RedeemCodeRepository redeemCodeRepository ;


    public long count(Integer state){
     return   redeemCodeRepository.count((root, criteriaQuery, criteriaBuilder) -> {
         return    JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
             if (state!=null) {
                 predicates.add(criteriaBuilder.equal(root.get("state"),state)) ;
             }
         },criteriaQuery);
        });
    }

    /**
     * 获得需要检验过期的列表
     * @return
     */
    public List<RedeemCode> findToJugeTimeOut(){
       return redeemCodeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
           return  JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
               predicates.add(criteriaBuilder.notEqual(root.get("state"), Config.CODE_ACTIVE)) ;
               predicates.add(criteriaBuilder.notEqual(root.get("state"), Config.CODE_OUT_TIME));
           },criteriaQuery) ;
        });
    }



   public Page<RedeemCode> findRedeemCodes(Long activeUserId , String activeUserName, String activeSchool, Long activeSchoolId ,
                                           Date beginDate , Date endDate , Integer state , Date activeTime , Long userId, Pageable pageable){

     return   redeemCodeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
           List<Predicate> predicates =new ArrayList<>() ;

           if (activeUserId!=null){
               predicates.add(criteriaBuilder.isNotNull(root.get("user").get("name")));
               predicates.add(criteriaBuilder.equal(root.get("user").get("id"),activeUserId));
           }

           if (activeSchoolId!=null){
               predicates.add(criteriaBuilder.isNotNull(root.get("school").get("name")));
               predicates.add(criteriaBuilder.equal(root.get("school").get("id"),activeSchoolId));
           }
           if (!StringUtils.isEmpty(activeUserName)){
               predicates.add(criteriaBuilder.like(root.get("user").get("name"),activeUserName));
           }
           if (!StringUtils.isEmpty(activeSchool)){
               predicates.add(criteriaBuilder.like(root.get("school").get("name"),activeSchool));
           }

           if (beginDate!=null){
               if (endDate==null) {
                   predicates.add(criteriaBuilder.between(root.get("activeTime"), beginDate, new Date()));
               }
           }
           if (endDate!=null){
               if (beginDate==null) {
                   predicates.add(criteriaBuilder.between(root.get("activeTime"), null, endDate));
               }
           }
           if (beginDate!=null&&endDate!=null){
               predicates.add(criteriaBuilder.between(root.<Date>get("activeTime"),beginDate,endDate));
           }
           if (state!=null){
               predicates.add(criteriaBuilder.equal(root.get("state"),state));
           }
           if (activeTime!=null){
               predicates.add(criteriaBuilder.equal(root.get("activeTime"),activeTime));
           }
           if (userId!=null){
               predicates.add(criteriaBuilder.equal(root.get("upLoadUser").get("id"),userId));
           }
           //根据创建的时间进行排序；
           criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")));

           return JpaQueryUtil.createPredicate(predicates,criteriaQuery);

       },pageable);

   }




}
