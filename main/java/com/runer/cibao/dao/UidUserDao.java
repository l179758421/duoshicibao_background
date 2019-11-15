package com.runer.cibao.dao;

import com.runer.cibao.domain.UidUser;
import com.runer.cibao.domain.repository.UidUserRepository;
import com.runer.cibao.util.JpaQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UidUserDao {
    @Autowired
    UidUserRepository uidUserRepository;

    public Page<UidUser> findUidUser(String uid,Pageable pageable){

        Page<UidUser> page = uidUserRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!StringUtils.isEmpty(uid)) {
                predicates.add(criteriaBuilder.equal(root.get("uid"), uid));
            }

            return JpaQueryUtil.createPredicate(predicates, criteriaQuery);

        }, pageable);


        return  page;
    }

}
