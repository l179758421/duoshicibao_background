package com.runer.cibao.dao;

import com.runer.cibao.domain.OnlineTime;
import com.runer.cibao.domain.repository.OnlineTimeRepository;
import com.runer.cibao.util.JpaQueryUtil;
import com.runer.cibao.util.machine.DateMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class OnlineTimeDao {
    @Autowired
    OnlineTimeRepository onlineTimeRepository;

    public List<OnlineTime> findOnlineByUserAndDate(Long userId, Date date ){

        return   onlineTimeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(criteriaBuilder.equal(root.get("appUser").get("id"),userId));
                if (date!=null){
                    Date[] dates = new DateMachine().getOneDayTimes(date);
                    predicates.add(criteriaBuilder.between(root.get("onlineDate"), dates[0], dates[1]));
                }
            },criteriaQuery);
        });
    }


    public List<OnlineTime> findOnlineByUser(Long userId){
        return   onlineTimeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(criteriaBuilder.equal(root.get("appUser").get("id"),userId));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("onlineDate")));
            },criteriaQuery);
        });
    }



}
