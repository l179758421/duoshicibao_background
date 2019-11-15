package com.runer.cibao.dao;

import com.runer.cibao.domain.HelpToUser;
import com.runer.cibao.domain.repository.HelpToUserRepository;
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
 * @Date 2018/6/12
 **/
@Repository
public class HelpToUserDao {

    @Autowired
    HelpToUserRepository helpToUserRepository ;

    public Page<HelpToUser> findHelpToUser(String theme , Pageable pageable){
      return   helpToUserRepository.findAll((root, criteriaQuery, criteriaBuilder) ->{
          List<Predicate> predicates =new ArrayList<>() ;
          if (!StringUtils.isEmpty(theme)){
              predicates.add(criteriaBuilder.like(root.get("theme"), JpaQueryUtil.getLikeStrAll(theme)));
          }
          return JpaQueryUtil.createPredicate(predicates,criteriaQuery);
        },pageable );
    }



}
