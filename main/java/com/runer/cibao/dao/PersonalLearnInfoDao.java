package com.runer.cibao.dao;

import com.runer.cibao.Config;
import com.runer.cibao.domain.PersonlLearnInfoBean;
import com.runer.cibao.domain.repository.PersonlLearnInfoRepository;
import com.runer.cibao.util.JpaQueryUtil;
import com.runer.cibao.util.NormalUtil;
import com.runer.cibao.util.machine.DateMachine;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/9/5
 **/
@Repository
public class PersonalLearnInfoDao {


    @Autowired
    DateMachine dateMachine;


    @Autowired
    PersonlLearnInfoRepository personlLearnInfoRepository;

    public List<PersonlLearnInfoBean> find(Long userId, Date date) {
        return personlLearnInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(criteriaBuilder.isNotNull(root.get("appUser").get("name")));
                predicates.add(criteriaBuilder.equal(root.get("appUser").get("id"), userId));
                if (date != null) {
                    Date[] dates = new DateMachine().getOneDayTimes(date);
                    predicates.add(criteriaBuilder.between(root.get("date"), dates[0], dates[1]));
                }
            }, criteriaQuery);
        });
    }

    public List<PersonlLearnInfoBean> findByRange(Long userId, Date startDate, Date endDate) {
        return personlLearnInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(criteriaBuilder.isNotNull(root.get("appUser").get("name")));
                predicates.add(criteriaBuilder.equal(root.get("appUser").get("id"), userId));
                predicates.add(criteriaBuilder.greaterThan(root.get("allWords"), 0));
                if (startDate != null && endDate != null) {
                    predicates.add(criteriaBuilder.between(root.get("date"), startDate, endDate));
                }
            }, criteriaQuery);
        });
    }

    public List<PersonlLearnInfoBean> findByLearnUser(Long classId, Date startDate, Date endDate) {
        return personlLearnInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(criteriaBuilder.equal(root.get("appUser").get("classInSchool").get("id"), classId));
                if (startDate != null && endDate != null) {
                    predicates.add(criteriaBuilder.between(root.get("date"), startDate, endDate));
                }
            }, criteriaQuery);
        });
    }


    /**
     * 排名！列表
     *
     * @param scholUid
     * @param provinceId
     * @param type
     * @param date
     * @return
     */
    public List<PersonlLearnInfoBean> getTopRanking(String scholUid, Long provinceId, Integer type, Date date, Pageable pageable) {
        Page<PersonlLearnInfoBean> datas = personlLearnInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(criteriaBuilder.isNotNull(root.get("appUser").get("name")));
                switchType(type, predicates, criteriaBuilder, root, provinceId, scholUid);
                if (date != null) {
                    Date[] dates = new DateMachine().getOneDayTimes(date);
                    predicates.add(criteriaBuilder.between(root.get("date"), dates[0], dates[1]));
                }
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("allWords")));
            }, criteriaQuery);
        }, pageable);
        return datas.getContent();
    }

    /**
     * 获得左右排名的人；
     *
     * @param left
     * @param scholUid
     * @param provinceId
     * @param type
     * @param date
     * @param wordNum
     * @param pageable
     * @return
     */
    public List<PersonlLearnInfoBean> getLeftOrRight(boolean left, String scholUid, Long provinceId, Integer type, Date date, Long wordNum, Pageable pageable) {

        if (type == Config.RANKING_SCHOOL_TYPE) {
            if (StringUtils.isEmpty(scholUid)) {
                return new ArrayList<>();
            }
        }
        if (type == Config.RANKING_PROVINCE_TYPE) {
            if (provinceId == null) {
                return new ArrayList<>();
            }
        }
        Page<PersonlLearnInfoBean> pageData = personlLearnInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(criteriaBuilder.isNotNull(root.get("appUser").get("name")));

                switchType(type, predicates, criteriaBuilder, root, provinceId, scholUid);

                if (date != null) {
                    Date[] dates = new DateMachine().getOneDayTimes(date);
                    predicates.add(criteriaBuilder.between(root.get("date"), dates[0], dates[1]));
                }
                if (wordNum != null && left) {
                    predicates.add(criteriaBuilder.greaterThan(root.get("allWords"), wordNum));
                    //排名靠前的情况下；
                    criteriaQuery.orderBy(criteriaBuilder.asc(root.get("allWords")));
                }
                if (wordNum != null && !left) {
                    predicates.add(criteriaBuilder.lessThan(root.get("allWords"), wordNum));
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("allWords")));
                }
            }, criteriaQuery);
        }, pageable);
        return pageData.getContent();
    }

    /**
     * 获得个人的排名；单词；
     *
     * @param date
     * @param type
     * @param wordsNum
     * @param provinceId
     * @param scholUid
     * @return
     */
    public long countOnePersonRankingWord(boolean isTime, Date date, Integer type, Long wordsNum, Long time, Long provinceId, String scholUid) {
        if (provinceId == null && type == Config.RANKING_PROVINCE_TYPE) {
            return 1;
        }
        if (StringUtils.isEmpty(scholUid) && type == Config.RANKING_SCHOOL_TYPE) {
            return 1;
        }
        return personlLearnInfoRepository.count((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(criteriaBuilder.isNotNull(root.get("appUser").get("name")));
                switchType(type, predicates, criteriaBuilder, root, provinceId, scholUid);
                if (date != null) {
                    Date[] dates = new DateMachine().getOneDayTimes(date);
                    predicates.add(criteriaBuilder.between(root.get("date"), dates[0], dates[1]));
                }
                if (isTime) {
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("learnTime")));
                    predicates.add(criteriaBuilder.greaterThan(root.get("learnTime"), time));
                } else {
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("allWords")));
                    predicates.add(criteriaBuilder.greaterThan(root.get("allWords"), wordsNum));
                }
            }, criteriaQuery);
        }) + 1;
    }

    private void switchType(Integer type, List<Predicate> predicates, CriteriaBuilder criteriaBuilder, Root root,
                            Long provinceId, String scholUid) {
        if (type == Config.RANKING_COUNTRY_TYPE) {
        } else if (type == Config.RANKING_PROVINCE_TYPE) {
            if (provinceId != null) {
                predicates.add(criteriaBuilder.equal(root.get("appUser").get("provinceId"), provinceId));
            }
        } else if (type == Config.RANKING_SCHOOL_TYPE) {
            if (!StringUtils.isEmpty(scholUid)) {
                predicates.add(criteriaBuilder.equal(root.get("appUser").get("schoolId"), scholUid));
            }
        }
    }


    //学习单词的排名；
    public List<PersonlLearnInfoBean> getTop3(Long provinceId, Long schoolId, Long classId, Date startDate, Date endDate) {
        Pageable topPage = PageableUtil.basicPage(1, 3);
        return personlLearnInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                //全国；
                //user不能为空；
                predicates.add(criteriaBuilder.isNotNull(root.get("appUser").get("name")));

                Date[] times = NormalUtil.rangeDate(startDate, endDate);
                predicates.add(criteriaBuilder.between(root.get("date"), times[0], times[1]));

                //全省;
                if (provinceId != null) {
                    predicates.add(criteriaBuilder.isNotNull(root.get("appUser").get("provinceId")));
                    predicates.add(criteriaBuilder.equal(root.get("appUser").get("provinceId"), provinceId));
                }
                if (schoolId != null) {
                    predicates.add(criteriaBuilder.isNotNull(root.get("appUser").get("classInSchool").get("name")));
                    predicates.add(criteriaBuilder.isNotNull(root.get("appUser").get("classInSchool").get("school").get("name")));
                    predicates.add(criteriaBuilder.equal(root.get("appUser").get("classInSchool").get("school").get("id"), schoolId));
                }
                if (classId != null) {
                    predicates.add(criteriaBuilder.isNotNull(root.get("appUser").get("classInSchool").get("name")));
                    predicates.add(criteriaBuilder.equal(root.get("appUser").get("classInSchool").get("school").get("id"), classId));
                }
            }, criteriaQuery);
        }, topPage).getContent();

    }


}
