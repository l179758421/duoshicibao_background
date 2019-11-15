package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.PersonalLearnBook;
import com.runer.cibao.domain.repository.PersonalLearnBookRepository;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/25
 **/
public interface PersonalLearnBookService extends BaseService<PersonalLearnBook, PersonalLearnBookRepository>{

    /**
     * 添加或者更细个人的课本
     * @param id
     * @param userId
     * @param bookId
     * @param currentWord
     * @param boughtTime
     * @param isCurrentBook
     * @param score
     * @param isPassed
     * @return
     */
    ApiResult addOrUpdateLearnBook(Long id, Long userId, Long bookId, Long currentWord,
                                   Date boughtTime, Integer isCurrentBook,
                                   Integer score, Integer isPassed);
    /**
     * 根据条件查询个人的课本；
     * @param userId
     * @param bookId
     * @param isPassed
     * @param isCurrentBook
     * @param page
     * @param limit
     * @return
     */
    Page<PersonalLearnBook> findPersonalLearnBooks(Long userId, Long bookId, Integer isPassed, Integer isCurrentBook, Integer page, Integer limit) ;



    /**
     * 根据条件查询个人课本；
     * @param userId
     * @param bookName
     * @param page
     * @param limit
     * @return
     */
    Page<PersonalLearnBook> findPersonalLearnBooksByBookName(Long userId, String bookName, Integer page, Integer limit) ;


    /**
     * 设置当前课本为
     * @param personalBookid
     * @return
     */
    ApiResult setLearnBookToCurrent(Long personalBookid) ;

    /**
     * 获得当前课本；
     * @param userId
     * @return
     */
    ApiResult getCurrentBook(Long userId);

    /**
     * 获得除了当前课本的书本
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    Page<PersonalLearnBook>  getBooksWithOutCurrentBook(Long userId, Integer page, Integer limit) ;


    ApiResult getSinglePersonalLearnBook(Long userId, Long book_id);


    List<PersonalLearnBook> getSinglePersonalLearnBook2(Long userId, Long book_id);

    /**
     * 购买图书；
     * @param bookId
     * @param userId
     * @return
     */
    ApiResult buyPersonalBook(Long bookId, Long userId, boolean isAll);


    /**
     *体验购买图书；
     * @param bookId
     * @param userId
     * @return
     */
    ApiResult buyPersonalBookSup(Long bookId, Long userId);






    ApiResult findAllByUserId(Long userId) ;

    ApiResult findAllByUserIdAndBookName(Long userId, String bookName) ;

    Page<PersonalLearnBook>  findBySchoolUID(String uid, Integer page, Integer limit) ;

    /**
     * 设置当前学习的单词；
     * @param wordId
     * @param personalBookId
     * @return
     */

    ApiResult setCurrentWord(Long wordId, Long personalBookId) ;

    Map<String,Object> numsInfo(Long wordId, Long bookId) ;

    /**
     * 根据激活时间范围查找
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
   Page<PersonalLearnBook> findByBoughtTimeAndUserId(Long userId, Date startTime, Date endTime, Integer page, Integer limit);

   long findBooksCountByAddressAndDate(Long agentId, String rangDate, String province, String city, String schoolName);

   Page<PersonalLearnBook> findBooksByAddressAndDate(Long agentId, String rangDate, String province, String city, String schoolName, Integer page, Integer limit);
   long findBooksByUserIdAndBookStage(Long userId, String stage);


   int getBookPrice(Long userId, Long bookId);


    /**
     * 根据bookId获取
     */
    ApiResult findByBook(Long bookId);


    ApiResult syncBooksData(Long userId) throws Exception ;


    List<PersonalLearnBook> personalLearnBookList(Long userId);

}
