package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.Word;
import com.runer.cibao.domain.WordExcel;
import com.runer.cibao.domain.repository.WordRepository;
import com.runer.cibao.exception.SmartCommunityException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/14
 **/
public interface WordService extends BaseService<Word, WordRepository> {

    /**
     * 查询words
     * @param wordName
     * @param rootAffixes
     * @param wordGetName
     * @param page
     * @param limit
     * @return
     */
     Page<Word> findWords(String wordName, String rootAffixes, String wordGetName, Integer page, Integer limit);

    /**
     * 序号	单词	音标	美式发音	英式发音	释义	英文例句1	例句翻译1	英文例句2	例句翻译2	助记法	词根词缀	相关词	备用1	备用2
     * @param word
     * @return
     */
    /**
     * 更新或者添加word
     * @param id
     * @param word
     * @param phonetic_symbol
     * @param americanPronunciation
     * @param englishPronunciation
     * @param interpretation
     * @param englishExample1
     * @param exampleTranslation1
     * @param englishExample2
     * @param exampleTranslation2
     * @param assistantNotation
     * @param rootAffixes
     * @param aboutWords
     * @param spare1
     * @param spare2
     * @return
     */
    ApiResult addOrUpdateWord(Long id, String word, String phonetic_symbol, String americanPronunciation, String englishPronunciation
            , String interpretation, String englishExample1, String exampleTranslation1, String englishExample2, String exampleTranslation2
            , String assistantNotation, String rootAffixes, String aboutWords, String spare1, String spare2);


    /**
     * excelWord转换成数据库word
     * @param excel
     * @return
     */
    Word wordExcel2Word(WordExcel excel);

    /**
     * 数据库word转换excelWord
     * @param word
     * @return
     */
    WordExcel word2wordExcel(Word word) ;


    /**
     * 批量的转换；
     * @param wordExcels
     * @return
     */
    List<Word> excelsToWords(List<WordExcel> wordExcels);

    /**
     * 批量的转换；
     * @param words
     * @return
     */
    List<WordExcel> words2Excels(List<Word> words) ;



    /**
     * 音频缺失导出
     * @return
     */
    ApiResult exportWordsAudioUrl(String title, String sheetname, String fileName, HttpServletResponse response) ;

    /**
     * 总词库缺失单词导出
     * @return
     */
    ApiResult  exportWords2(Long id, String title, String sheetname, String filename, HttpServletResponse httpResponse);

    /**
     * 导出word；
     * @param words
     * @return
     */
    ApiResult exportWords(List<Word> words,String title, String sheetname);
    /**
     * 批量的导入；
     * @param wordsFile
     * @return
     */
    ApiResult importWords(MultipartFile wordsFile);


    ApiResult saveForWords(List<Word> words) throws SmartCommunityException;


    Word findByWordName(String wordname) ;


    ApiResult uploadWordAudio(MultipartFile wordAudio, String type);

    /**
     * 获取上传音频列表
     * @return
     */
    ApiResult getWordAudioList();


    ApiResult uploadWordAudioZip(MultipartFile wordAudioZip, String type);


    ApiResult getById(Long id);

    public ApiResult exportWords(String ids, String title, String sheetname);

}
