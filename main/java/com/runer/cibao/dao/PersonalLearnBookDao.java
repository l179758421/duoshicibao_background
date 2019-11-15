package com.runer.cibao.dao;

import com.runer.cibao.domain.PersonalLearnBook;
import com.runer.cibao.domain.repository.BookWordRepository;
import com.runer.cibao.domain.repository.PersonalLearnBookRepository;
import com.runer.cibao.util.JpaQueryUtil;
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
import java.util.Optional;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/25
 **/
@Repository
public class PersonalLearnBookDao {


    @Autowired
    PersonalLearnBookRepository personalLearnBookRepository;

    @Autowired
    BookWordRepository bookWordRepository;


    public long findBooksCountByUser(Long userId, String stage, Date startDate, Date endDate) {
        return personalLearnBookRepository.count((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(criteriaBuilder.isNotNull(root.get("personalLearnBooks").get("appUser").get("id")));

                if (userId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("personalLearnBooks").get("appUser"), userId));
                }
                if (!StringUtils.isEmpty(stage)) {
                    predicates.add(criteriaBuilder.equal(root.get("learnBook").get("stage"), stage));
                }

                if (startDate != null && endDate != null) {
                    predicates.add(criteriaBuilder.between(root.get("boughtTime"), startDate, endDate));
                }


            }, criteriaQuery);

        });
    }

    /**
     * 获得列表；
     *
     * @param userId
     * @param stage
     * @param startDate
     * @param endDate
     * @param pageable
     * @return
     */
    public Page<PersonalLearnBook> findBooksByUser(Long userId, String stage, Date startDate, Date endDate, Pageable pageable) {
        return personalLearnBookRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(criteriaBuilder.isNotNull(root.get("personalLearnBooks").get("appUser").get("id")));
                predicates.add(criteriaBuilder.isNotNull(root.get("learnBook").get("bookName")));
                if (userId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("personalLearnBooks").get("appUser"), userId));
                }
                if (!StringUtils.isEmpty(stage)) {
                    predicates.add(criteriaBuilder.equal(root.get("learnBook").get("stage"), stage));
                }

                if (startDate != null && endDate != null) {
                    predicates.add(criteriaBuilder.between(root.get("boughtTime"), startDate, endDate));
                }
            }, criteriaQuery);
        }, pageable);
    }


    /**
     * 获得购买的统计
     *
     * @param schoolId
     * @param classSchoolId
     * @param stage
     * @param bookId
     * @param startDate
     * @param endDate
     * @return
     */
    public long findAgentsOrdersCount(Long schoolId, Long classSchoolId, String stage,
                                      Long bookId, Date startDate, Date endDate) {
        return personalLearnBookRepository.count(
                (root, criteriaQuery, criteriaBuilder) -> {
                    return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                        createQuery(schoolId, classSchoolId, stage, bookId, startDate, endDate, predicates, criteriaBuilder, root);
                    }, criteriaQuery);
                }
        );
    }


    private void createQuery(Long schoolId, Long classSchoolId, String stage,
                             Long bookId, Date startDate, Date endDate, List<Predicate> predicates, CriteriaBuilder criteriaBuilder, Root root) {


        if (schoolId != null || classSchoolId != null) {
            predicates.add(criteriaBuilder.isNotNull(root.get("personalLearnBooks").get("appUser").get("classInSchool").get("id")));
        }
        if (schoolId != null) {
            predicates.add(criteriaBuilder.equal(root.get("personalLearnBooks").get("appUser").get("classInSchool").get("school").get("id"), schoolId));
        }
        if (classSchoolId != null) {
            predicates.add(criteriaBuilder.equal(root.get("personalLearnBooks").get("appUser").get("classInSchool").get("id"), classSchoolId));
        }
        if (bookId != null) {
            predicates.add(criteriaBuilder.equal(root.get("leanrBook").get("id"), bookId));
        }
        if (startDate != null && endDate != null) {
            predicates.add(criteriaBuilder.between(root.get("boughtTime"), startDate, endDate));
        }
        if (!StringUtils.isEmpty(stage)) {
            predicates.add(criteriaBuilder.equal(root.get("learnBook").get("stage"), stage));
        }

    }


    /**
     * 获得激活数量的统计；
     *
     * @param schoolId
     * @param classSchoolId
     * @param bookId
     * @param startDate
     * @param endDate
     * @param pageable
     * @return
     */
    public Page<PersonalLearnBook> findAgentsOrders(Long schoolId, Long classSchoolId, String stage,
                                                    Long bookId, Date startDate, Date endDate, Pageable pageable) {
        return personalLearnBookRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                createQuery(schoolId, classSchoolId, stage, bookId, startDate, endDate, predicates, criteriaBuilder, root);
            }, criteriaQuery);
        }, pageable);
    }


    /**
     * 获得当前的wordNum；
     *
     * @param wordId
     * @param bookId
     * @return
     */
    public long findCurrentbookNum(Long wordId, Long bookId) {
        return bookWordRepository.count((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(criteriaBuilder.equal(root.get("unit").get("learnBook").get("id"), bookId));
                predicates.add(criteriaBuilder.le(root.get("id"), wordId));
            }, criteriaQuery);
        });
    }

    /**
     * 获得剩余的wordNum
     *
     * @param wordId
     * @param bookId
     * @return
     */
    public long findLeftbookNum(Long wordId, Long bookId) {
        return bookWordRepository.count((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(criteriaBuilder.equal(root.get("unit").get("learnBook").get("id"), bookId));
                predicates.add(criteriaBuilder.ge(root.get("id"), wordId));
            }, criteriaQuery);
        });
    }

    /**
     * 获得所有的wordnum ；
     *
     * @param wordId
     * @param bookId
     * @return
     */
    public long findALlCount(Long wordId, Long bookId) {
        return bookWordRepository.count((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(criteriaBuilder.equal(root.get("unit").get("learnBook").get("id"), bookId));
            }, criteriaQuery);
        });
    }


    /**
     * 获得个人的列表；
     *
     * @param userId
     * @param bookId
     * @param isPassed
     * @param isCurrentBook
     * @param pageable
     * @return
     */
    public Page<PersonalLearnBook> findPersonalLearnBooks(Long userId, Long bookId, Integer isPassed, Integer isCurrentBook, Pageable pageable) {

        return personalLearnBookRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            //排除课本删除；
            predicates.add(criteriaBuilder.isNotNull(root.get("learnBook").get("bookName")));
            predicates.add(criteriaBuilder.isNotNull(root.get("personalLearnBooks").get("appUser").get("name")));
            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("personalLearnBooks").get("appUser").get("id"), userId));
            }
            if (bookId != null) {
                predicates.add(criteriaBuilder.equal(root.get("learnBook").get("id"), bookId));
            }

            if (isPassed != null) {
                predicates.add(criteriaBuilder.equal(root.get("isPassed"), isPassed));
            }

            if (isCurrentBook != null) {
                predicates.add(criteriaBuilder.equal(root.get("isCurrentBook"), isCurrentBook));
            }

            return JpaQueryUtil.createPredicate(predicates, criteriaQuery);
        }, pageable);


    }

    public List<PersonalLearnBook> findPersonalLearnBooksByUserId(Long userId) {

        return personalLearnBookRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            //排除课本删除；
            predicates.add(criteriaBuilder.isNotNull(root.get("learnBook").get("bookName")));
            predicates.add(criteriaBuilder.isNotNull(root.get("personalLearnBooks").get("appUser").get("name")));
            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("personalLearnBooks").get("appUser").get("id"), userId));
            }

            return JpaQueryUtil.createPredicate(predicates, criteriaQuery);
        });


    }

    public List<PersonalLearnBook> findPersonalLearnBooksByUserIdAndBookName(Long userId, String bookName) {

        return personalLearnBookRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            //排除课本删除；
            predicates.add(criteriaBuilder.isNotNull(root.get("learnBook").get("bookName")));
            predicates.add(criteriaBuilder.isNotNull(root.get("personalLearnBooks").get("appUser").get("name")));
            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("personalLearnBooks").get("appUser").get("id"), userId));
            }
            if (bookName != null) {
                predicates.add(criteriaBuilder.like(root.get("learnBook").get("bookName"),JpaQueryUtil.getLikeStrAll(bookName)));
            }
            return JpaQueryUtil.createPredicate(predicates, criteriaQuery);
        });


    }


    public Page<PersonalLearnBook> findPersonalLearnBookByBookName(Long userId, String bookName, Pageable pageable) {

        return personalLearnBookRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            //排除课本删除；
            predicates.add(criteriaBuilder.isNotNull(root.get("learnBook").get("bookName")));
            predicates.add(criteriaBuilder.isNotNull(root.get("personalLearnBooks").get("appUser").get("name")));
            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("personalLearnBooks").get("appUser").get("id"), userId));
            }
            if (bookName != null) {
                predicates.add(criteriaBuilder.like(root.get("learnBook").get("bookName"), JpaQueryUtil.getLikeStrAll(bookName)));
            }

            return JpaQueryUtil.createPredicate(predicates, criteriaQuery);
        }, pageable);


    }


    public PersonalLearnBook findByUserIdAndBookId(Long userId, Long bookId) {
        Optional<PersonalLearnBook> one = personalLearnBookRepository.findOne((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                predicates.add(criteriaBuilder.isNotNull(root.get("personalLearnBooks").get("appUser").get("name")));
                predicates.add(criteriaBuilder.equal(root.get("learnBook").get("id"), bookId));
                predicates.add(criteriaBuilder.equal(root.get("personalLearnBooks").get("appUser").get("id"), userId));
            }, criteriaQuery);
        });

        if (one.isPresent()) {
            return one.get();
        } else {
            return null;
        }
    }

    public Page<PersonalLearnBook> findBySchoolUID(String uid , Pageable pageable){

        return  personalLearnBookRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(StringUtils.isEmpty(uid)){
                predicates.add(criteriaBuilder.equal(root.get("personalLearnBooks").get("appUser").get("schooId"),uid));
            }
            return JpaQueryUtil.createPredicate(predicates, criteriaQuery);
        }, pageable);
    }

    public List<PersonalLearnBook> findByBookId(Long bookId){
        return personalLearnBookRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (bookId != null) {
                predicates.add(criteriaBuilder.equal(root.get("learnBook").get("id"), bookId));
            }
            return JpaQueryUtil.createPredicate(predicates, criteriaQuery);
        });
    }

}
