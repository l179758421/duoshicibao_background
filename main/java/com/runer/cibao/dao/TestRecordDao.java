package com.runer.cibao.dao;

import com.runer.cibao.domain.TestRecords;
import com.runer.cibao.domain.repository.TestRecordRepository;
import com.runer.cibao.util.JpaQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/11/1
 **/
@Repository
public class TestRecordDao {

    @Autowired
    TestRecordRepository testRecordRepository ;


    public List<TestRecords> find(Long unitTestId , Long bookTestId , Integer type){

       return testRecordRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (bookTestId != null) {
                predicates.add(criteriaBuilder.equal(root.get("bookTestId"), bookTestId));
            }
            if (unitTestId != null) {
                predicates.add(criteriaBuilder.equal(root.get("unitTestId"), unitTestId));
            }
            if (type != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), type));
            }
            return JpaQueryUtil.createPredicate(predicates, criteriaQuery);
        });

    }






}
