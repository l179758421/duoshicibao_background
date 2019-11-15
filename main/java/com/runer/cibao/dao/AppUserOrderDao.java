package com.runer.cibao.dao;

import com.runer.cibao.Config;
import com.runer.cibao.domain.AppUserOrder;
import com.runer.cibao.domain.repository.AppUserOrderRepository;
import com.runer.cibao.domain.repository.PersonalLearnBookRepository;
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
 * @Date 2018/6/27
 **/
@Repository
public class AppUserOrderDao {

    @Autowired
    private AppUserOrderRepository appUserOrderRepository ;
    @Autowired
    PersonalLearnBookRepository personalLearnBookDao ;

    /**
     * 获得代理商的激活课本统计；
     * @return
     */
    public Page<AppUserOrder> findAgentsOrders(Long schoolId , Long classSchoolId , Long bookId , Date startDate , Date endDate  , Pageable pageable){
        Page<AppUserOrder> pages = appUserOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(criteriaBuilder.equal(root.get("type"), Config.ORDER_TPYE_BUY_BOOKS)) ;
                //班级不能够为空；
                if (schoolId!=null||classSchoolId!=null) {
                    predicates.add(criteriaBuilder.isNotNull(root.get("appUserAccount").get("appUser").get("classInSchool").get("name")));
                }
                //更具schoolId进行查询；
                if (schoolId!=null) {
                    predicates.add(criteriaBuilder.equal(root.get("appUserAccount").get("appUser").get("classInSchool").get("school").get("id"), schoolId));
                }
                //根据classId进行查询；
                if (classSchoolId!=null){
                    predicates.add(criteriaBuilder.equal(root.get("appUserAccount").get("appUser").get("classInSchool").get("id"),classSchoolId));
                }
                if (bookId!=null){
                    predicates.add(criteriaBuilder.isNotNull(root.get("personalLearnBook").get("id"))) ;
                    predicates.add(criteriaBuilder.equal(root.get("personalLearnBook").get("learnBook").get("id"),bookId)) ;
                }
                if (startDate!=null&&endDate!=null){
                    predicates.add(criteriaBuilder.between(root.get("createDate"),startDate,endDate)) ;
                }

            }, criteriaQuery);
        }, pageable);;


        return  pages ;
    }


    /**
     * 充值充值码的统计；
     * @param schoolId
     * @param statrtDate
     * @param endDate
     * @param pageable
     * @return
     */
    public Page<AppUserOrder> findReeedemOrders(Long schoolId , Long userSchoolId , Date statrtDate , Date endDate , Pageable pageable ){
        return  appUserOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) ->{
                 //类型为充值；
                 predicates.add(criteriaBuilder.equal(root.get("type"), Config.ORDER_TYPE_RECHARGE));
                 predicates.add(criteriaBuilder.isNotNull(root.get("redeemCode").get("scholl").get("id"))) ;
                 predicates.add(criteriaBuilder.equal(root.get("redeemCode").get("school").get("id"),schoolId)) ;
                 if (userSchoolId!=null){
                     //班级不能够为空；
                     predicates.add(criteriaBuilder.isNotNull(root.get("appUserAccount").get("appUser").get("classInSchool").get("name")));
                     predicates.add(criteriaBuilder.equal(root.get("appUserAccount").get("appUser").get("classInSchool").get("school").get("id"),schoolId)) ;
                 }
            },criteriaQuery);
        },pageable) ;
    }






    public Page<AppUserOrder> findOrders(Integer type , Long userId  , String title , Pageable pageable ){

     return   appUserOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
           List<Predicate> predicates =new ArrayList<>();

           if (userId!=null) {
               predicates.add(criteriaBuilder.equal(root.get("appUserAccount").get("appUser").get("id"), userId));
           }

           if (type!=null){
               predicates.add(criteriaBuilder.equal(root.get("type"),type)) ;
           }

           if (!StringUtils.isEmpty(title)){
               predicates.add(criteriaBuilder.like(root.get("title"), JpaQueryUtil.getLikeStrAll(title)));
           }
           return JpaQueryUtil.createPredicate(predicates,criteriaQuery) ;

       },pageable) ;

    }





}
