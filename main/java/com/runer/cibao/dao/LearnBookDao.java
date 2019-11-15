package com.runer.cibao.dao;

import com.runer.cibao.domain.LearnBook;
import com.runer.cibao.domain.repository.LearnBookRepository;
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
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/11
 **/
@Repository
public class LearnBookDao {

    @Autowired
    LearnBookRepository learnBookRepository ;

    /**
     * 获得阶段；
     * @return
     */
    public List<LearnBook> findAllStage(){

        List<LearnBook> datas = learnBookRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.groupBy(root.get("stage"));
            criteriaQuery.select(root.get("stage"));
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
            }, criteriaQuery);
        });

        datas.forEach(learnBook -> {
            learnBook.setUser(null);
            learnBook.setBookUnitList(null);
        });

        return  datas ;

    }

    /**
     * 获得年级
     */

    public  List<LearnBook> findGrade(String stage){
        List<LearnBook> datas = learnBookRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.groupBy(root.get("grage"));
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                if (StringUtils.isEmpty(stage)){
                    predicates.add(criteriaBuilder.equal(root.get("stage"),stage)) ;
                }
            }, criteriaQuery);
        });
        datas.forEach(learnBook -> {
            learnBook.setUser(null);
            learnBook.setBookUnitList(null);
        });

        return  datas ;
    }

    /**
     * 判断 是否还有重复课本!
     * @param
     * @return
     */
    public  List<LearnBook> findByBook(String bookName , String version , String stage , String grade){
        List<LearnBook> datas = learnBookRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            return JpaQueryUtil.jpaDataInputQuery((predicates, query) -> {
                if (!StringUtils.isEmpty(version)){
                    predicates.add(criteriaBuilder.equal(root.get("version"),version)) ;
                }
                if (!StringUtils.isEmpty(bookName)){
                    predicates.add(criteriaBuilder.equal(root.get("bookName"),bookName));
                }
                if (!StringUtils.isEmpty(stage)){
                    predicates.add(criteriaBuilder.equal(root.get("stage"),stage));
                }
                if (!StringUtils.isEmpty(grade)){
                    predicates.add(criteriaBuilder.equal(root.get("grade"),grade));
                }
            }, criteriaQuery);
        });
        datas.forEach(learnBook -> {
            learnBook.setUser(null);
            learnBook.setBookUnitList(null);
        });

        return  datas ;
    }





    /**
     * 版本 课本名称 年级 课本单词数 被下载次数 创建时间 阶段
     * @param pageable
     * @return
     */
    public  Page<LearnBook> findLearnBooks(Pageable pageable , String version , String name , String grade , String stage,
                                           String likeVerison , String likeName , String likeGrage , String likeStage

    ){
      return   learnBookRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates =new ArrayList<>() ;
            predicates =createPredicates(version,name,grade,stage,likeVerison,likeName,likeGrage,likeStage,predicates,criteriaBuilder,root);
            return JpaQueryUtil.createPredicate(predicates,criteriaQuery);
        },pageable);
    }

    public Long getLearnBookCount(String version ,String name ,String grade,String stage,String likeVerison ,String likeName ,String likeGrage ,String likeStage ){
        return   learnBookRepository.count((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates =new ArrayList<>() ;
            predicates =createPredicates(version,name,stage,grade,likeVerison,likeName,likeGrage,likeGrage,predicates,criteriaBuilder,root);
            return JpaQueryUtil.createPredicate(predicates,criteriaQuery);
        });
    }


    private List<Predicate> createPredicates(String version , String name , String grade ,String stage,
                                             String likeVerison ,String likeName ,String likeGrage ,String likeStage,
                                             List<Predicate> predicates, CriteriaBuilder criteriaBuilder , Root root){
        if (!StringUtils.isEmpty(version)){
            predicates.add(criteriaBuilder.equal(root.get("version"),version)) ;
        }
        if (!StringUtils.isEmpty(name)){
            predicates.add(criteriaBuilder.equal(root.get("bookName"),name));
        }
        if (!StringUtils.isEmpty(grade)){
            predicates.add(criteriaBuilder.equal(root.get("grade"),grade));
        }

        if (!StringUtils.isEmpty(stage)){
            predicates.add(criteriaBuilder.equal(root.get("stage"),stage));
        }

        if (!StringUtils.isEmpty(likeVerison)){
            predicates.add(criteriaBuilder.like(root.get("version"), JpaQueryUtil.getLikeStrAll(likeVerison))) ;
        }
        if (!StringUtils.isEmpty(likeName)){
            predicates.add(criteriaBuilder.like(root.get("bookName"), JpaQueryUtil.getLikeStrAll(likeName)));
        }
        if (!StringUtils.isEmpty(likeGrage)){
            predicates.add(criteriaBuilder.like(root.get("grade"), JpaQueryUtil.getLikeStrAll(likeGrage)));
        }

        if (!StringUtils.isEmpty(likeStage)){
            predicates.add(criteriaBuilder.like(root.get("stage"), JpaQueryUtil.getLikeStrAll(likeStage)));
        }
        return  predicates ;


    }





}
