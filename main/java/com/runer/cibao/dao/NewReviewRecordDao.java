package com.runer.cibao.dao;

import com.runer.cibao.domain.person_word.NewReviewRecord;
import com.runer.cibao.domain.repository.NewReviewRecordRepository;
import com.runer.cibao.util.JpaQueryUtil;
import com.runer.cibao.util.machine.DateMachine;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/9/3
 **/
@Repository
public class NewReviewRecordDao {

    @Autowired
    NewReviewRecordRepository newReviewRecordRepository ;

    public List<NewReviewRecord> findNewReviewRecords(Long userId , Date date  ){
        if (userId==null){
            return  new ArrayList<>() ;
        }
       return newReviewRecordRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
           return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
               predicates.add(criteriaBuilder.equal(root.get("userId"),userId));
               if (date!=null){
                   Date[] dates =new DateMachine().getOneDayTimes(date) ;
                   predicates.add(criteriaBuilder.between(root.get("uploadTime"),dates[0],dates[1]));
               }
           },criteriaQuery) ;
        });
    }



    public List<NewReviewRecord> findNewReviewRecordsWithDates(Long userId , Date startTime ,Date endTime   ){
        if (userId==null){
            return  new ArrayList<>() ;
        }
        return newReviewRecordRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(criteriaBuilder.equal(root.get("userId"),userId));
                if (startTime!=null&&endTime==null){
                    predicates.add(criteriaBuilder.between(root.get("uploadTime"),startTime,new Date()));
                }
                if (endTime!=null&&startTime==null){
                    Date start =DateUtils.addDays(endTime,Integer.MAX_VALUE+1);
                    predicates.add(criteriaBuilder.between(root.get("uploadTime"),start,endTime));
                }
                if (startTime!=null&&endTime!=null){
                    predicates.add(criteriaBuilder.between(root.get("uploadTime"),startTime,endTime)) ;
                }
            },criteriaQuery) ;
        });
    }


}
