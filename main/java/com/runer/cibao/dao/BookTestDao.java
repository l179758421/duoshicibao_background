package com.runer.cibao.dao;

import com.runer.cibao.domain.TestRecords;
import com.runer.cibao.domain.repository.BookTestRepository;
import com.runer.cibao.util.JpaQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/19
 **/
@Repository
public class BookTestDao {

    @Autowired
    BookTestRepository bookTestRepository ;


    /**
     * 单词名称
     * 书名
     * 书id
     * 根据单元进行查询
     * @param wordName
     * @param bookName
     * @param bookid
     * @param unitId
     * @param pageable
     * @return
     */
     public   Page<TestRecords> findBookTest(String wordName , String bookName , Long bookid , Long unitId , Pageable pageable){
        Page<TestRecords> page = bookTestRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.isNotNull(root.get("learnBook").get("bookName")));

            predicates.add(criteriaBuilder.isNotNull(root.get("bookWord").get("wordName"))) ;

            if (unitId!=null){
                predicates.add(criteriaBuilder.equal(root.get("bookWord").get("unit").get("id"),unitId));
            }

            if (!StringUtils.isEmpty(bookName)) {
                predicates.add(criteriaBuilder.like(root.get("learnBook").get("bookName"), JpaQueryUtil.getLikeStrAll(bookName)));
            }

            if (!StringUtils.isEmpty(wordName)){
                predicates.add(criteriaBuilder.like(root.get("bookWord").get("wordName"), JpaQueryUtil.getLikeStrAll(wordName)));
            }

            if (bookid != null) {
                predicates.add(criteriaBuilder.equal(root.get("learnBook").get("id"), bookid));
            }
            return JpaQueryUtil.createPredicate(predicates, criteriaQuery);
        }, pageable);
        return  page ;
    }








}
