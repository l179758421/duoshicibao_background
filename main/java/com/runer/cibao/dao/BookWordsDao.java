package com.runer.cibao.dao;

import com.runer.cibao.domain.BookWord;
import com.runer.cibao.domain.Word;
import com.runer.cibao.domain.repository.BookWordRepository;
import com.runer.cibao.util.JpaQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/11
 **/
@Repository
public class BookWordsDao {

    @Autowired
    BookWordRepository bookWordRepository ;

    /**
     * @param words
     * @return
     */
    public List<BookWord> findAllbyWordNames(List<Word> words){
        if (words!=null){
            Set<String> wordsNames =new HashSet<>() ;
            for (Word word : words) {
                wordsNames.add(word.getWord()) ;
            }
            bookWordRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
                List<Predicate> predicates =new ArrayList<>() ;
                predicates.add(root.get("wordName").in(wordsNames)) ;
                return  JpaQueryUtil.createPredicate(predicates,criteriaQuery) ;
            }) ;
        }
        return  new ArrayList<>() ;

    }


    /**
     * 根据条件进行查询单词
     * @param pageable
     * @param bookId
     * @param name
     * @return
     */
   public  Page<BookWord>  findBookWords(Pageable pageable, Long bookId , String name , Long unitId ){

      return bookWordRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {

           List<Predicate> predicates =new ArrayList<>();

           //双重判断'
           predicates.add(criteriaBuilder.isNotNull(root.get("unit").get("name")));
           predicates.add(criteriaBuilder.isNotNull(root.get("unit").get("learnBook").get("bookName"))) ;
           createQuery(bookId,name,unitId,predicates,root,criteriaBuilder);
           return JpaQueryUtil.createPredicate(predicates,criteriaQuery);

       }, pageable );
    }

    /**
     * 根据bookWord进行查询；
     * @param bookWord
     * @return
     */
    public BookWord findByBookWord(BookWord bookWord){

        bookWord.setUnit(null);
        boolean isExist =  bookWordRepository.exists(Example.of(bookWord));
     if (isExist){
         List<BookWord> datas = bookWordRepository.findAll(Example.of(bookWord));
         return  datas.get(0) ;
     }else{
         return  null ;
     }
    }



    /**
     * 查询单词的数量；
     * @param bookId
     * @param name
     * @return
     */
    public Long findBookWorsCount(Long bookId ,String name ,Long unitId ){
     return   bookWordRepository.count((root, criteriaQuery, criteriaBuilder) -> {
           List<Predicate> predicates =new ArrayList<>() ;
         createQuery(bookId,name,unitId,predicates,root,criteriaBuilder);
           return JpaQueryUtil.createPredicate(predicates,criteriaQuery);
       });
    }
    /**
     * 查询所有的单词；
     * @param bookId
     * @param name
     * @return
     */
    public List<BookWord> findAll(Long bookId , String name , Long unitId){
        return   bookWordRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates =new ArrayList<>() ;
             createQuery(bookId,name,unitId,predicates,root,criteriaBuilder);
            return JpaQueryUtil.createPredicate(predicates,criteriaQuery);
        });
    }


    private List<Predicate> createQuery(Long bookId , String name , Long unitId , List<Predicate> predicates , Root root ,
                                        CriteriaBuilder criteriaBuilder){
        if (bookId!=null){
            predicates.add(criteriaBuilder.equal(root.get("unit").get("learnBook").get("id"),bookId));
        }
        if (unitId!=null){
            predicates.add(criteriaBuilder.equal(root.get("unit").get("id"),unitId));
        }
        if (!StringUtils.isEmpty(name)){
//            predicates.add(criteriaBuilder.like(root.get("word").get("word"),JpaQueryUtil.getLikeStrAll(name)));
            predicates.add(criteriaBuilder.like(root.get("wordName"), JpaQueryUtil.getLikeStrAll(name)));

        }

        return  predicates ;
    }





}
