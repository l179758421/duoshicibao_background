package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.LearnBook;
import com.runer.cibao.domain.repository.LearnBookRepository;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/11
 *
 *课本相关的业务逻辑
 **/
public interface LearnBookService extends BaseService<LearnBook,LearnBookRepository> {


    List<LearnBook> findAllBooks(String version, String bookName, String grage, String stage);
    /**
     *
     * @param version
     * @param bookName
     * @param grade
     * @param stage
     * @param likeVerison
     * @param likeName
     * @param likeGrage
     * @param likeStage
     * @param page
     * @param limit
     * @return
     */
    Page<LearnBook> findBooks(String version, String bookName, String grade, String stage,
                              String likeVerison, String likeName, String likeGrage, String likeStage, Integer page, Integer limit);
    /**
     * 获得书本的数量
     * @param bookName
     * @param version
     * @param grade
     * @return
     */
     Long  findBooksNum(String version, String bookName, String grade, String stage,
                        String likeVerison, String likeName, String likeGrage, String likeStage) ;

    /**
     * 添加或者更新book；
     * @param id
     * @param version
     * @param boolName
     * @param grade
     * @param wordsNum
     * @param userId
     * @return
     */
      ApiResult addOrUpadateBook(Long id, Integer price, String version, String boolName,
                                 String grade, String wordsNum, String stage, String imgUrl, Long userId);
    /**
     * 更新下载的次数
     * @param id
     * @param userId
     * @return
     */
      ApiResult addDownLoadNum(Long id, Long userId) ;

    /**
     * 获得课本的单词数量；
     * @param bookId
     * @return
     */
    ApiResult findBookWordsNum(Long bookId);
    /**
     * 为课本添加封面
     * @param file
     * @param
     * @return
     */
      ApiResult uploadBookCover(MultipartFile file) ;
    ApiResult createBookMird(Long bookId);
    /**
     * 课本的打包
     */
     ApiResult zipBookForApp(Long bookId) ;
    /**
     * 课本中的单词育婴
     */
    Map<String,String> findWordAudiosPathes(Long bookId);

    /**
     * 下载课本的打包
     * @param bookId
     * @return
     */
     ApiResult downLoadWordsZip(Long bookId);


     ApiResult downlowdWordsZip(List<String> bookIds) ;

    ApiResult downlowdWordsZipWithinNoZip(List<String> bookIds) ;

     ApiResult findAllStage() ;

     ApiResult findGrages(String stage) ;

    /**
     * 添加时:查询课本是否重复
     * @param
     * @return
     */
    ApiResult findByBook(String bookName, String version, String stage, String grade);

}
