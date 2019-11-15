package com.runer.cibao.dao;

import com.runer.cibao.domain.MailAddres;
import com.runer.cibao.domain.repository.MailAddressRepository;
import com.runer.cibao.util.JpaQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/21
 **/
@Repository
public class MailAddressDao {

    @Autowired
    MailAddressRepository mailAddressRepository ;
    /**
     * 根据userId查询地址；
     * @param userId
     * @param pageable
     * @return
     */
    public Page<MailAddres> findMailAddress (Long userId , Pageable pageable){

       return mailAddressRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
           List<Predicate> predicates =new ArrayList<>() ;
           predicates.add(criteriaBuilder.isNotNull(root.get("appUser").get("name")));
           if (userId!=null){
               predicates.add(criteriaBuilder.equal(root.get("appUser").get("id"),userId));
           }
           return JpaQueryUtil.createPredicate(predicates,criteriaQuery);
        },pageable);


    }



}
