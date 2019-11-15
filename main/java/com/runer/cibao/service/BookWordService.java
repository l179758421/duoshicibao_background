package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.BookWord;
import com.runer.cibao.domain.BookWordExcel;
import com.runer.cibao.domain.Word;
import com.runer.cibao.domain.repository.BookWordRepository;
import com.runer.cibao.exception.SmartCommunityException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/11
 * 9!!!!!!!
 **/
public interface BookWordService extends BaseService<BookWord,BookWordRepository> {
    /**
     * 查找单词
     * @param name
     * @param bookId
     * @param page
     * @param limit
     * @return
     */
    Page<BookWord> findBookWord(String name, Long bookId, Long unitId, Integer page, Integer limit);


    List<BookWord> findAll(String name, Long bookId, Long unitId);


    /**
     * 更新单词 (后台的更改)
     * @return
     */
    ApiResult addOrUpdateWords(Long id, Long bookId, Long unitId,
                               String stage, String version, String bookName, String unitName,
                               String wordName, String interpretation, String englishExample1,
                               String exampleTranslation1, String englishExample2, String exampleTranslation2,
                               String assistantNotation, String rootAffixes, String aboutWords, String spare1,
                               String spare2);
    /**
     * words 转换成excel类
     * @param bookWordList
     * @return
     */
    List<BookWordExcel> words2Excels(List<BookWord> bookWordList) ;


    /**
     * excel 转换成words
     * @param bookWordExcels
     * @return
     */
    List<BookWord> excels2words(List<BookWordExcel> bookWordExcels);

     ApiResult  deleteBooks(String ids);

     ApiResult  deleteBook(Long id);


    /**
     *  excel 转换成words
     * @param bookWordExcel
     * @return
     */
    ApiResult excelBookWord2BookWord(BookWordExcel bookWordExcel);
    /**
     *  words 转换成excel类
     * @param bookWord
     * @return
     */
    ApiResult bookWord2ExcelBookWord(BookWord bookWord);
    /**
     * 导入课本/单词
     * @param multipartFile
     * @return
     */
    ApiResult importForWords1(MultipartFile multipartFile);
    ApiResult importForWords(MultipartFile multipartFile, Long bookid);
    /**
     * 导出单词
     * @param boolWords
     * @param response
     * @param title
     * @param sheetname
     * @param fileName
     * @return
     */
    ApiResult bathExportWords(List<BookWord> boolWords, HttpServletResponse response, String title, String sheetname, String fileName);
    /**
     * 排除重复的word；
     * @param words
     * @return
     * @throws SmartCommunityException
     */
    ApiResult saveForWords(List<BookWord> words) throws SmartCommunityException;
    /**
     * 导出单词；
     * @param ids
     * @param title
     * @param sheetname
     * @param filename
     * @param httpResponse
     * @return
     */
    ApiResult  exportWords(String ids, String title, String sheetname, String filename, HttpServletResponse httpResponse);


    /**
     * 课本文本和单词库中的转换 ；
     * @param bookWord
     * @param word
     * @return
     */
    ApiResult words2BookWord(BookWord bookWord, Word word) ;


    /**
     * 根木wordName查找word
     */
    ApiResult findByWordName(String wordName);


    /**
     * 根据unit 或者book 获得数量
     */
    ApiResult getBookWordsNum(String name, Long bookId, Long unitId);


    /**
     *mark错误   对应的字段为isRight ；
     */
    List<BookWord> markBookWrod(String ids, String markIds) throws SmartCommunityException;

}
