package com.runer.cibao.dao;

import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.AppUser;
import com.runer.cibao.domain.Roles;
import com.runer.cibao.domain.Teacher;
import com.runer.cibao.domain.User;
import com.runer.cibao.domain.repository.AppUserRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.TeacherService;
import com.runer.cibao.util.JpaQueryUtil;
import com.runer.cibao.util.machine.IdsMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/21
 **/
@Repository
public class AppUserDao {


    @Autowired
    AppUserRepository appUserRepository;


    @Autowired
    TeacherService teacherDao;


    /**
     * 各种条件下查询；
     *
     * @param schoolUid
     * @param schoolId
     * @param classInSchoolId
     * @param userName
     * @param teacherId
     * @param pageable
     * @return
     */

    public Page<AppUser> findAppUsers(String schoolUid, Long schoolId,
                                      Long classInSchoolId, String userName, Long teacherId, String uid, Integer isbatchCreated, Pageable pageable) {

        String classIds = "";
        ApiResult teacherResult = new ApiResult("fff");
        if (teacherId != null) {
            teacherResult = teacherDao.findByIdWithApiResult(teacherId);
            if (teacherResult.isSuccess()) {
                Teacher teacher = (Teacher) teacherResult.getData();
                classIds = teacher.getClassIds();
            }

        }
        String finalClassIds = classIds;
        ApiResult finalTeacherResult = teacherResult;

        Specification<AppUser> specification = new Specification<AppUser>() {
            @Override
            public Predicate toPredicate(Root<AppUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(schoolUid)) {
                    predicates.add(criteriaBuilder.equal(root.get("schoolId"), schoolUid));
                }
                if (schoolId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("classInSchool").get("school").get("id"), schoolId));
                }


                if (!StringUtils.isEmpty(userName)) {
                    if (teacherId != null) {
                        predicates.add(criteriaBuilder.like(root.get("realNameForInfo"), JpaQueryUtil.getLikeStrAll(userName)));
                    } else {
                        predicates.add(criteriaBuilder.like(root.get("schoolName"), JpaQueryUtil.getLikeStrAll(userName)));
                    }
                }
                if (classInSchoolId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("classInSchool").get("id"), classInSchoolId));
                }
                //in的操作；
                if (finalTeacherResult.isSuccess()) {
                    predicates.add(criteriaBuilder.isNotNull(root.get("classInSchool").get("name")));
                    if (org.apache.commons.lang3.StringUtils.isEmpty(finalClassIds)) {
                        predicates.add(criteriaBuilder.equal(root.get("classInSchool").get("id"), 0));
                    } else {
                        predicates.add(root.get("classInSchool").get("id").in(finalClassIds.split(",")));
                    }
                }
                if (isbatchCreated != null) {
                    predicates.add(criteriaBuilder.equal(root.get("isBatchCreate"), isbatchCreated));
                }
                if (!StringUtils.isEmpty(uid)) {
                    predicates.add(criteriaBuilder.equal(root.get("uid"), uid));
                }
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("registerDate")));
                return JpaQueryUtil.createPredicate(predicates, criteriaQuery);
            }
        };

        Page<AppUser> datas = appUserRepository.findAll(specification, pageable);

        return datas;
    }


    public Page<AppUser> findAppUsers2(String schoolUid, Long schoolId,
                                       Long classInSchoolId, String userName, Long teacherId, String uid, Integer isbatchCreated, Pageable pageable) {

        String classIds = "";
        ApiResult teacherResult = new ApiResult("fff");
        if (teacherId != null) {
            teacherResult = teacherDao.findByIdWithApiResult(teacherId);
            if (teacherResult.isSuccess()) {
                Teacher teacher = (Teacher) teacherResult.getData();
                classIds = teacher.getClassIds();
            }
        }
        String finalClassIds = classIds;
        ApiResult finalTeacherResult = teacherResult;
        Specification<AppUser> specification = new Specification<AppUser>() {
            @Override
            public Predicate toPredicate(Root<AppUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(schoolUid)) {
                    predicates.add(criteriaBuilder.equal(root.get("schoolId"), schoolUid));
                }
                Predicate namePredicate = null;
                Predicate realNamePredicate = null;
                if (!StringUtils.isEmpty(userName)) {
                    namePredicate = criteriaBuilder.like(root.get("name"), JpaQueryUtil.getLikeStrAll(userName));
                    realNamePredicate = criteriaBuilder.like(root.get("realNameForInfo"), JpaQueryUtil.getLikeStrAll(userName));
                }
                if (namePredicate != null && realNamePredicate != null) {
                    predicates.add(criteriaBuilder.or(namePredicate, realNamePredicate));
                } else {
                    if (namePredicate != null) {
                        predicates.add(namePredicate);
                    }
                    if (realNamePredicate != null) {
                        predicates.add(realNamePredicate);
                    }
                }
                if (classInSchoolId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("classInSchool").get("id"), classInSchoolId));
                }
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("registerDate")));
                return JpaQueryUtil.createPredicate(predicates, criteriaQuery);
            }
        };

        Page<AppUser> datas = appUserRepository.findAll(specification, pageable);

        return datas;
    }


    /**
     * 根据openID获得user ；
     *
     * @param type
     * @param openId
     * @return
     * @throws SmartCommunityException
     */
    public AppUser findAppUserByOpenId(Integer type, String openId) throws SmartCommunityException {

        AppUser appUser = null;

        if (type != Config.WX && type != Config.WEIBO && type != Config.QQ) {
            throw new SmartCommunityException(ResultMsg.THIRD_TYPE_IS_NULL);
        }
        Optional<AppUser> appUserOptional = appUserRepository.findOne((root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (type == Config.QQ) {
                predicates.add(criteriaBuilder.equal(root.get("qqOpenId"), openId));
            } else if (type == Config.WEIBO) {
                predicates.add(criteriaBuilder.equal(root.get("weiboOpenId"), openId));
            } else if (type == Config.WX) {
                predicates.add(criteriaBuilder.equal(root.get("wechatOpenId"), openId));
            }
            return JpaQueryUtil.createPredicate(predicates, criteriaQuery);
        });

        if (appUserOptional.isPresent()) {
            appUser = appUserOptional.get();
        }
        return appUser;
    }


    public long countUserCount(Long classId, Long schoolId) {
        return appUserRepository.count((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {

                if (schoolId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("classInSchool").get("school").get("id"), schoolId));
                }
                if (classId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("classInSchool").get("id"), classId));
                }
            }, criteriaQuery);
        });
    }

    public AppUser userExist(String name) {
        AppUser user = appUserRepository.findAppUserByPhoneNum(name);
        if (user == null) {
            return null;
        }
        return user;
    }

    public AppUser findByUid(String uid) {

        return appUserRepository.findAppUserByUid(uid);
    }

    public List<AppUser> findUserByIds(String ids, Integer isBatchCreated) {
        return appUserRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                if (!StringUtils.isEmpty(ids)) {
                    predicates.add(root.get("id").in(new IdsMachine().deparseIds(ids)));
                }
                if (isBatchCreated != null) {
                    predicates.add(criteriaBuilder.equal(root.get("isBatchCreate"), isBatchCreated));
                }
            }, criteriaQuery);
        });
    }

    public Page<AppUser> findBySchoolUid(String schoolUid, String userName, Pageable pageable) {
        return appUserRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!StringUtils.isEmpty(schoolUid)) {
                predicates.add(criteriaBuilder.equal(root.get("schoolId"), schoolUid));
            }

            if (!StringUtils.isEmpty(userName)) {
                predicates.add(criteriaBuilder.like(root.get("name"), JpaQueryUtil.getLikeStrAll(userName)));
            }
            return JpaQueryUtil.createPredicate(predicates, criteriaQuery);

        }, pageable);
    }


}
