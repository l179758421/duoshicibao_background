package com.runer.cibao.dao;

import com.runer.cibao.domain.Permission;
import com.runer.cibao.domain.repository.PermissionsRepository;
import com.runer.cibao.service.RolesService;
import com.runer.cibao.util.JpaQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/2
 **/
@Repository
public class PermissionsDao {

    @Autowired
    PermissionsRepository permissionsRepository;


    @Autowired
    RolesService rolesService ;





    public List<Permission> findMenusPemissions(){
        List<Permission> pemissions = permissionsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isNotNull(root.get("menuId")));
            predicates.add(criteriaBuilder.notEqual(root.get("menuId"), 0));
            return JpaQueryUtil.createPredicate(predicates, criteriaQuery);
        });
        return  pemissions ;
    }

    public List<Permission> findMenusPemissionsWithOutChild(){
        List<Permission> pemissions = permissionsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isNotNull(root.get("menuId")));
            predicates.add(criteriaBuilder.notEqual(root.get("menuId"), 0));
            predicates.add(criteriaBuilder.isNull(root.get("parentId")));
            return JpaQueryUtil.createPredicate(predicates, criteriaQuery);
        });
        return  pemissions ;
    }

    public List<Permission> findMenusByPrantId(Long pranteId ){
        List<Permission> pemissions = permissionsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isNotNull(root.get("menuId")));
            predicates.add(criteriaBuilder.equal(root.get("parentId"),pranteId));
            return JpaQueryUtil.createPredicate(predicates, criteriaQuery);
        });
        return  pemissions ;
    }



}
