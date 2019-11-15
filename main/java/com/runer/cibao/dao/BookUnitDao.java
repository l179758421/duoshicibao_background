package com.runer.cibao.dao;

import com.runer.cibao.domain.BookUnit;
import com.runer.cibao.domain.repository.BookUnitRepository;
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
 * @Date 2018/6/15
 *
 * 单元的查询
 *
 *
 **/
@Repository
public class BookUnitDao {



    @Autowired
    BookUnitRepository bookUnitRepository ;

    public List<BookUnit> findUnits(Long bookId , String unitName){
       return bookUnitRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
         return    JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
             //book is not allowed null ;
             predicates.add(criteriaBuilder.isNotNull(root.get("learnBook").get("bookName")));
             predicates.add(criteriaBuilder.equal(root.get("learnBook").get("id"),bookId)) ;
             predicates.add(criteriaBuilder.equal(root.get("name"),unitName)) ;
         },criteriaQuery) ;
        });
    }


                                    /**
                                     *
                                     * @param bookId   根据BOOK——ID查询
                                     * @param bookversion 根据 book的版本进行查询
                                     * @param bookStage 根据book的阶段进行查询
                                     * @param bookName 根据book的name进行查询
                                     * @param unitId 根据单元的id进行查询
                                     * @param unitName 根据单元的名称进行查询
                                     * @param bookversionLikeName 版本的模糊查询
                                     * @param booklikeName 课本名称的模糊查询
                                     * @param unitLikeName 单元名称的模糊查询
                                     * @return
                                     */
    public List<BookUnit> findUnits(Long bookId , String bookversion , String bookStage , String bookName ,
                                    Long unitId  , String unitName , String bookversionLikeName , String booklikeName , String unitLikeName){
      return   bookUnitRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates =new ArrayList<>() ;

            //book is not allowed null ;
            predicates.add(criteriaBuilder.isNotNull(root.get("learnBook").get("bookName")));

            if (bookId!=null){
                predicates.add(criteriaBuilder.equal(root.get("learnBook").get("id"),bookId));
            }
            if (!StringUtils.isEmpty(bookversion)){
                predicates.add(criteriaBuilder.equal(root.get("learnBook").get("version"),bookversion));
            }
            if (!StringUtils.isEmpty(bookStage)){
                predicates.add(criteriaBuilder.equal(root.get("learnBook").get("stage"),bookStage));
            }
            if (!StringUtils.isEmpty(bookName)){
                predicates.add(criteriaBuilder.equal(root.get("learnBook").get("bookName"),bookName));
            }
            if (unitId!=null){
                predicates.add(criteriaBuilder.equal(root.get("id"),unitId));
            }
            if (!StringUtils.isEmpty(unitName)){
                predicates.add(criteriaBuilder.equal(root.get("name"),unitName));
            }

          if (!StringUtils.isEmpty(bookversionLikeName)){
              predicates.add(criteriaBuilder.like(root.get("learnBook").get("version"), JpaQueryUtil.getLikeStrAll(bookversionLikeName)));
          }
          if (!StringUtils.isEmpty(booklikeName)){
              predicates.add(criteriaBuilder.like(root.get("learnBook").get("bookName"), JpaQueryUtil.getLikeStrAll(booklikeName)));
          }
          if (!StringUtils.isEmpty(unitLikeName)){
              predicates.add(criteriaBuilder.equal(root.get("name"), JpaQueryUtil.getLikeStrAll(unitLikeName)));
          }
            return JpaQueryUtil.createPredicate(predicates,criteriaQuery);

        });




    }


    public Page<BookUnit> findByBookId(Long bookId, String unitName, Pageable pageable){
        return    bookUnitRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates =new ArrayList<>() ;

            if (bookId!=null){
                predicates.add(criteriaBuilder.equal(root.get("learnBook").get("id"),bookId));
            }

            if (!StringUtils.isEmpty(unitName)){
                predicates.add(criteriaBuilder.equal(root.get("name"),unitName));
            }
            return JpaQueryUtil.createPredicate(predicates,criteriaQuery) ;

        },pageable);
    }







}
