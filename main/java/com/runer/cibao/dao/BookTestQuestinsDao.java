package com.runer.cibao.dao;

import com.runer.cibao.domain.BookTestQuetions;
import com.runer.cibao.domain.repository.BookTestQuestionsRepository;
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
public class BookTestQuestinsDao {

    @Autowired
    BookTestQuestionsRepository bookTestQuestionsRepository ;

    public  Page<BookTestQuetions> findBookTestQuestions(Long bookId , Long unitId , Long onlyBookId, String testName , Pageable pageable){

      return   bookTestQuestionsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicateList =new ArrayList<>() ;

            if (bookId!=null){
                System.err.println(bookId);
                /**
                 * 多重的查询条件；
                 */
                predicateList.add(
                        criteriaBuilder.equal(root.get("learnBook").get("id"),bookId));
            }
            if (onlyBookId!=null){
                predicateList.add(criteriaBuilder.equal(root.get("learnBook").get("id"),bookId));
                predicateList.add(criteriaBuilder.isNull(root.get("bookUnit").get("name")));
            }

            if (unitId!=null){
                predicateList.add(criteriaBuilder.equal(root.get("bookUnit").get("id"),unitId));
            }

            if (!StringUtils.isEmpty(testName)){
                predicateList.add(criteriaBuilder.like(root.get("testName"), JpaQueryUtil.getLikeStrAll(testName)));
            }

            return JpaQueryUtil.createPredicate(predicateList,criteriaQuery);



        },pageable);



    }




}
