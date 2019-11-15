package com.runer.cibao.dao;

import com.runer.cibao.domain.WordCount;
import com.runer.cibao.domain.repository.WordCountRepository;
import com.runer.cibao.util.JpaQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author k
 * @Date: Created in 16:26 2018/8/23
 * @Description:
 */
@Repository
public class WordCountDao {

    @Autowired
    private WordCountRepository wordCountRepository;


    public Long  getUnitCount(Long appUserId, Long unitId, Integer status){
        return    wordCountRepository.count((root, criteriaQuery, criteriaBuilder) ->
                JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                if (appUserId!=null) {
                    predicates.add(criteriaBuilder.equal(root.get("appUserId"), appUserId));
                }
                if (unitId!=null){
                    predicates.add(criteriaBuilder.equal(root.get("bookUnitId"),unitId)) ;
                }

                if (status!=null){
                    predicates.add(criteriaBuilder.equal(root.get("status"),status)) ;
                }
            },criteriaQuery)
        );
    }

    public WordCount getTodayNewWordsCount(Long appUserId) {
        Calendar todayStart = Calendar.getInstance();
        Calendar todayEnd = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        Date start = todayEnd.getTime();
        Date end = todayEnd.getTime();

        List<WordCount> var =   wordCountRepository.findAll((root, criteriaQuery, criteriaBuilder) ->
                JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                    if (appUserId!=null) {
                        predicates.add(criteriaBuilder.equal(root.get("appUserId"), appUserId));
                    }
                    predicates.add(criteriaBuilder.between(root.get("createDate"),start,end));
                    predicates.add(criteriaBuilder.isNotNull(root.get("newWordsCount")));
                },criteriaQuery)
        );
        if (var.size() == 0){
            return null;
        }
       return var.get(0);
    }

    public void createTodayNewWordsCount(Long appUserId) {
        WordCount var = new WordCount();
        var.setAppUserId(appUserId);
        var.setCreateDate(new Date());
        var.setNewWordsCount(0L);
        wordCountRepository.save(var);
    }
}