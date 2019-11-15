package com.runer.cibao.dao;

import com.runer.cibao.domain.Admin;
import com.runer.cibao.domain.repository.AdminRepository;
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
 * @Date 2018/7/4
 **/
@Repository
public class AdminDao {

    @Autowired
    AdminRepository adminRepository;

    public Page<Admin>  findAdmins(Integer isMaster , Pageable pageable){
      return   adminRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates =new ArrayList<>() ;
            if (isMaster!=null) {
                predicates.add(criteriaBuilder.equal(root.get("isMaster"), isMaster));
            }
            return JpaQueryUtil.createPredicate(predicates,criteriaQuery) ;
        },pageable);
    }
}
