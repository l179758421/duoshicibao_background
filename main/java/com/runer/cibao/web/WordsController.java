package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.ClassInSchool;
import com.runer.cibao.domain.Word;
import com.runer.cibao.domain.WordsAudio;
import com.runer.cibao.service.WordService;
import com.runer.cibao.util.NormalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author sww
 * @Date 2019/09/24
 * 总库的单词Controller
 **/
@RestController
@RequestMapping("allWords")
@CrossOrigin
public class WordsController {
    @Value("${web.upload-cibao}")
    private String abPath ;

    @Autowired
    WordService beanService ;




    @RequestMapping(value = "getWordsAudioData")
    public LayPageResult<WordsAudio> getWordsAudioData(){
        ApiResult beanResult =   beanService.getWordAudioList() ;
        LayPageResult<WordsAudio> result =new LayPageResult((List) beanResult.getData());
        return  result;
    }

    /**
     * 获取总库单词分页
     * @param wordName
     * @param rootAffixes
     * @param wordGetName
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "data_list")
    public LayPageResult<ClassInSchool> getDataList(String wordName , String rootAffixes , String wordGetName, Integer page ,Integer limit){
        Page<Word> pageResult = beanService.findWords(wordName, rootAffixes, wordGetName, page, limit);
        return NormalUtil.createLayPageReuslt(pageResult) ;
    }

    /**
     * 单词新增和修改
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
    @RequestMapping(value = "addOrUpdateData")
    public ApiResult addOrUpdateData(Long id ,String word ,String phonetic_symbol ,String americanPronunciation ,
                                     String englishPronunciation
            ,String interpretation ,String englishExample1 ,String exampleTranslation1 ,
                                     String englishExample2, String exampleTranslation2
            ,String assistantNotation ,String rootAffixes ,String aboutWords ,String spare1 ,String spare2){
        return    beanService.addOrUpdateWord(id,word,phonetic_symbol,americanPronunciation,englishPronunciation,
                interpretation,englishExample1,exampleTranslation1,
                englishExample2,exampleTranslation2,assistantNotation,rootAffixes,aboutWords,spare1,spare2);
    }

    /**
     * 删除多行
     * @param ids
     * @return
     */

    @RequestMapping("deleteByIds")
    public ApiResult deleteByIds(String ids){
        return  NormalUtil.deleteByIds(beanService,ids) ;
    }

    /**
     * 删除单行
     * @param id
     * @return
     */
    @RequestMapping("deleteById")
    public ApiResult deleteById(Long id){
        return  NormalUtil.deleteById(beanService,id);
    }

    /**
     * 通过id获取
     * @param id
     * @return
     */
    @RequestMapping("getById")
    public ApiResult getById(Long id){
        return  beanService.getById(id);
    }


//    /**
//     * 导出
//     * @param ids
//     * @param title
//     * @param sheetname
//     * @param filename
//     * @param httpResponse
//     */
//    @RequestMapping("exportWords")
//    public void exportWords(String ids, String title ,String sheetname ,String filename , HttpServletResponse httpResponse){
//        beanService.exportWords(ids, null, sheetname, filename, httpResponse);
//    }


    /**
     * 导出
     * @param ids
     * @param title
     * @param sheetname
     */
    @RequestMapping("exportWords")
    public ApiResult exportWords(String ids, String title ,String sheetname){
        ApiResult apiResult = beanService.exportWords(ids, title,sheetname);
        return apiResult;
    }


    /**
     * 音频缺失导出
     * @param
     * @return
     */
    @RequestMapping("exportWordsAudioUrl")
    public ApiResult exportWordsAudioUrl(String title ,String sheetname ,String filename , HttpServletResponse httpResponse){
         return  beanService.exportWordsAudioUrl(title,sheetname,filename,httpResponse);
    }


    /**
     * 导出总词库缺失单词
     */
    @RequestMapping("exportWords2")
    public void exportWords2(Long id, String title ,String sheetname ,
                             String filename , HttpServletResponse httpResponse){
        beanService.exportWords2(id,null,sheetname,filename,httpResponse);
    }


    @RequestMapping("improtWords")
    public ApiResult importWords(MultipartFile file){
     return    beanService.importWords(file);
    }


    @RequestMapping("uploadsWordsAudio")
    public ApiResult uploadWordsAudio(MultipartFile file,String type){
        return  beanService.uploadWordAudio(file,type);
    }


    @RequestMapping("uploadFileAudioZip")
    public ApiResult uploadFileAudioZip(MultipartFile file,String type){
        return  beanService.uploadWordAudioZip(file,type);
    }

}
