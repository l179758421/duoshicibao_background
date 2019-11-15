package com.runer.cibao.dao;

import com.runer.cibao.domain.AppUserAccount;
import com.runer.cibao.domain.repository.AppUserAccountRepository;
import com.runer.cibao.util.JpaQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/27
 **/
@Repository
public class AppUserAccountDao {



    @Autowired
    AppUserAccountRepository appUserAccountRepository ;


    public AppUserAccount findByUserId(Long userId )  {

        Optional<AppUserAccount> optional = appUserAccountRepository.findOne((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            predicateList.add(criteriaBuilder.equal(root.get("appUser").get("id"), userId));

            return JpaQueryUtil.createPredicate(predicateList, criteriaQuery);
        });;

        if (optional.isPresent()){
         return  optional.get() ;
        }else{
            return  null ;
        }
    }




}
