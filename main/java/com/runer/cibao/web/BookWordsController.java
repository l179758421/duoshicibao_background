package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.BookUnit;
import com.runer.cibao.domain.BookWord;
import com.runer.cibao.domain.LearnBook;
import com.runer.cibao.domain.PersonalLearnBook;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import com.runer.cibao.util.NormalUtil;
import com.runer.cibao.util.machine.IdsMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author sww
 * @Date 2019/9/26
 **/
@RestController
@RequestMapping(value = "bookwords")
public class BookWordsController {

    @Autowired
    BookWordService beanService ;

    @Autowired
    UserLoginService userLoginService ;


    @Autowired
    BookUnitService unitService ;

    @Autowired
    LearnBookService learnBookService ;

    @Autowired
    PersonalLearnBookService personalLearnBookService;

    @Autowired
    IdsMachine idsMachine;

    /**
     * 数据列表
     * @param wordName
     * @param bookId
     * @param page
     * @return
     */
    @RequestMapping(value = "data_list")
    public LayPageResult<BookWord> getDataList(String wordName , Long  bookId ,Integer page , Integer limit){
        Page<BookWord> pageResult = beanService.findBookWord(wordName, bookId,null, page,limit);
        return NormalUtil.createLayPageReuslt(pageResult) ;
    }

    /**
     * 跟新或者添加数据
     * @param id
     * @param unitId
     * @param wordName
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
    public ApiResult addOrUpdateData(Long id,Long unitId ,
                                     String wordName, String interpretation, String englishExample1,
                                     String exampleTranslation1, String englishExample2, String exampleTranslation2,
                                     String assistantNotation, String rootAffixes, String aboutWords, String spare1,
                                     String spare2){
        return    beanService.addOrUpdateWords(id,null,unitId,null,null,null,null,
                wordName,interpretation,englishExample1,exampleTranslation1
        ,englishExample2,exampleTranslation2,assistantNotation,rootAffixes,aboutWords,spare1,spare2);
    }

    /**
     * 根据ids批量删除数据
     * @param ids
     * @return
     */
    @RequestMapping("deleteByIds")
    public ApiResult deleteByIds(String ids){
        return  NormalUtil.deleteByIds(beanService,ids) ;
    }

    /**
     * 根据id删除数据
     * @param id
     * @return
     */
    @RequestMapping("deleteById")
    public ApiResult deleteById(Long id){
        return  NormalUtil.deleteById(beanService,id);
    }


    /**
     * 导出单词
     */
    @RequestMapping("exportWords")
    public void exportWords(String ids, String title ,String sheetname ,
                            String filename , HttpServletResponse httpResponse){
        beanService.exportWords(ids,null,sheetname,filename,httpResponse);
    }

    /**
     * 导入课本
     * @param file
     * @return
     */
    @RequestMapping("improtWords1")
    public ApiResult importWords1(MultipartFile file ){
        return    beanService.importForWords1(file);
    }


    /**
     * 导入danci
     * @param file
     * @return
     */
    @RequestMapping("improtWords")
    public ApiResult importWords(MultipartFile file ,Long bookid){
        return    beanService.importForWords(file,bookid);
    }


    @RequestMapping("deleteBookIds")
    public ApiResult deleteBookIds(String ids){
        for (Long id:idsMachine.deparseIds(ids)) {
            ApiResult apiResult = personalLearnBookService.findByBook(id);
            List<PersonalLearnBook> personalLearnBooks = (List<PersonalLearnBook>) apiResult.getData();
            if(personalLearnBooks.size() != 0){
                return new ApiResult("存在学员学习该课本!");
            }
        }
      return   beanService.deleteBooks(ids);
    }

    @RequestMapping("deleteBookId")
    public ApiResult deleteBookId(Long id){
        ApiResult apiResult = personalLearnBookService.findByBook(id);
        List<PersonalLearnBook> personalLearnBooks = (List<PersonalLearnBook>) apiResult.getData();
        if(personalLearnBooks.size() != 0){
            return new ApiResult("存在学员学习该课本!");
        }
       return beanService.deleteBook(id);
    }
}
