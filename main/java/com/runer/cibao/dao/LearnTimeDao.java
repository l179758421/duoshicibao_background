package com.runer.cibao.dao;

import com.runer.cibao.domain.LearnTime;
import com.runer.cibao.domain.repository.LearnTimeRepository;
import com.runer.cibao.service.OnlineTimeService;
import com.runer.cibao.util.JpaQueryUtil;
import com.runer.cibao.util.NormalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/11/3
 **/
@Repository
public class LearnTimeDao {


    @Autowired
    LearnTimeRepository learnTimeRepository;

    @Autowired
    private OnlineTimeService onlineTimeService;


    /**
     * 获得learnTime；
     *
     * @param startTime
     * @param endTime
     * @param appUserId
     * @return
     */
    public List<LearnTime> findByDateUser(Date startTime, Date endTime, Long appUserId) {

        if (appUserId == null) {
            return new ArrayList<>();
        }

        return learnTimeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(criteriaBuilder.equal(root.get("appUserId"), appUserId));
                if (NormalUtil.rangeDate(startTime, endTime) != null) {
                    Date[] ranges = NormalUtil.rangeDate(startTime, endTime);
                    predicates.add(criteriaBuilder.between(root.get("date"), ranges[0], ranges[1]));
                }

            }, criteriaQuery);
        });
    }


    public List<LearnTime> findByLearnTime(Long appUserId) {
        if (appUserId == null) {
            return new ArrayList<>();
        }
        List<LearnTime> learnTimes = learnTimeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(criteriaBuilder.equal(root.get("appUserId"), appUserId));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("date")));
            }, criteriaQuery);
        });
//        List<OnlineTime> onlineTimeList = onlineTimeService.findByUserId(appUserId);
//        Iterator<LearnTime> iterator = learnTimes.iterator();
//        for (OnlineTime online : onlineTimeList) {
//            while (iterator.hasNext()) {
//                if (!iterator.next().getStringDate().equals(online.getStringDate())) {
//                    iterator.remove();
//                }
//            }
//        }
        return learnTimes;
    }


}
