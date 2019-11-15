package com.runer.cibao.service.impl;

import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.BookUnitDao;
import com.runer.cibao.dao.BookWordsDao;
import com.runer.cibao.domain.*;
import com.runer.cibao.domain.repository.BookWordRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import com.runer.cibao.util.ExcelUtil;
import com.runer.cibao.util.machine.IdsMachine;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.ListUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

import static com.runer.cibao.Config.PER_UNIT_WORD_NUM;
import static com.runer.cibao.exception.ResultMsg.NOT_FOUND_ERROR;
import static com.runer.cibao.exception.ResultMsg.SUCCESS;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/11
 **/
@Service
public class BookWordServiceImpl extends BaseServiceImp<BookWord, BookWordRepository> implements BookWordService {

    @Autowired
    BookWordsDao bookWordsDao ;

    @Autowired
    BookUnitDao bookUnitDao ;

    @Autowired
    BookUnitService bookUnitService ;

    @Autowired
    LearnBookService learnBookService ;

    @Autowired
    WordService wordService ;

    @Autowired
    WordNameRedisService wordNameRedisService;

    @Autowired
    IdsMachine idsMachine ;



    @Override
    public void deleteByIds(String ids) throws SmartCommunityException {

        //顺带的清除缓存
        List<BookWord> datas = findByIds(ids);
        if (datas!=null){
            for (BookWord data : datas) {
                wordNameRedisService.deleteWordName(data);
            }
        }
        super.deleteByIds(ids);
    }

    @Override
    public boolean deleteById(Long id) throws SmartCommunityException {
        //顺带的清除缓存；
        ApiResult wordResult =findByIdWithApiResult(id) ;
        if (wordResult.isSuccess()){
            wordNameRedisService.deleteWordName((BookWord) wordResult.getData());
        }
        return super.deleteById(id);
    }

    /**
     * 绝对路径
     */
    @Value("${web.upload-audioEng}")
    private String abPathEng;  // C:CiBao/audio/eng/
    @Value("${web.upload-audioUsa}")
    private String abPathUsa; // // C:CiBao/audio/usa/

    /**
     * 相对路径
     */
    @Value("${web.upload-engPath}")
    private String rePathEng;  // audio/eng/
    @Value("${web.upload-usaPath}")
    private String rePathUsa; // audio/usa/


    @Override
    public Page<BookWord> findBookWord(String name, Long bookId, Long unitId , Integer page, Integer limit) {
        Page<BookWord> pageResult = bookWordsDao.findBookWords(PageableUtil.basicPage(page, limit), bookId,name,unitId);

        //检查一下单词是否在总词库中，进行更新；
        if (!ListUtils.isEmpty(pageResult.getContent())){
            for (BookWord bookWord : pageResult.getContent()) {
                if (bookWord.getWord()==null){
                     Word word=  wordService.findByWordName(bookWord.getWordName());
                     if (word!=null){
                         ApiResult apiResult = words2BookWord(bookWord, word);
                         bookWord=r.save((BookWord) apiResult.getData());
                     }
                }
            }
        }
        for (BookWord word:pageResult.getContent()) {
            String engFilePath = abPathEng+word.getWordName()+".mp3";
            File testFileEng = new File(engFilePath);
            if(testFileEng.exists()){
                if (word.getWord()!=null) {
                    word.getWord().setWordAudioUrl(rePathEng + word.getWordName() + ".mp3");
                }
            }
            String usaFilePath = abPathUsa+word.getWordName()+".mp3";
            File testFileUsa = new File(usaFilePath);
            if(testFileUsa.exists()){
                if (word.getWord()!=null) {
                    word.getWord().setUsaAudioUrl(rePathUsa + word.getWordName() + ".mp3");
                }
            }
        }
        return  pageResult ;

    }


    @Override
    public List<BookWord> findAll(String name, Long bookId, Long unitId) {
        return bookWordsDao.findAll(bookId,name,unitId);
    }
    @Override
    public ApiResult addOrUpdateWords(Long id, Long bookId , Long unitId ,
                                      String stage, String version, String bookName, String unitName,
                                      String wordName, String interpretation, String englishExample1,
                                      String exampleTranslation1, String englishExample2, String exampleTranslation2,
                                      String assistantNotation, String rootAffixes, String aboutWords, String spare1,
                                      String spare2) {
        if (unitId==null){
            return  new ApiResult(ResultMsg.UNIT_ID_IS_NULL,null) ;
        }

        BookWord bookWord =new BookWord();

       if (id!=null){
           try {
               bookWord =findById(id);
           } catch (SmartCommunityException e) {
               e.printStackTrace();
               return  new ApiResult(e.getResultMsg(),null) ;
           }
       }else{
           //wordName重复的时候；
           if (r.findBookWordByWordName(wordName)!=null){
               return  new ApiResult("该单词已存在!");
           }
       }

        bookWord.setId(id);

        Word word =wordService.findByWordName(wordName);

        //word存在的情况下；
        if (word!=null){
            words2BookWord(bookWord,word);
        }else{
            bookWord.setInterpretation(interpretation);
            bookWord.setEnglishExample1(englishExample1);
            bookWord.setExampleTranslation1(exampleTranslation1);
            bookWord.setEnglishExample2(englishExample2);
            bookWord.setExampleTranslation2(exampleTranslation2);
            bookWord.setAssistantNotation(assistantNotation);
            bookWord.setRootAffixes(rootAffixes);
            bookWord.setAboutWords(aboutWords);
            bookWord.setSpare1(spare1);
            bookWord.setSpare2(spare2);
            bookWord.setWordName(wordName);
        }


        bookWord.setStage(stage);
        bookWord.setVersion(version);
        bookWord.setBookName(bookName);
        bookWord.setUnitName(unitName);

        if (unitId!=null){
            try {
             BookUnit bookUnit =  bookUnitService.findById(unitId);
             bookWord.setUnitName(bookUnit.getName());
             bookWord.setUnit(bookUnit);

             if (bookUnit.getLearnBook()!=null){
             bookWord.setBookName(bookUnit.getLearnBook().getBookName());
             bookWord.setVersion(bookUnit.getLearnBook().getVersion());
             bookWord.setStage(bookUnit.getLearnBook().getStage());
             }
            } catch (SmartCommunityException e) {
                e.printStackTrace();
            }
        }

        if (bookId!=null){
            try {
                LearnBook learnBook =  learnBookService.findById(bookId);
                bookWord.setStage(learnBook.getStage());
                bookWord.setVersion(learnBook.getVersion());
                bookWord.setBookName(learnBook.getBookName());
            } catch (SmartCommunityException e) {
                e.printStackTrace();
            }
        }

        try {
            bookWord =save(bookWord);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null );
        }
        return new ApiResult(SUCCESS,bookWord);
    }

    @Override
    public List<BookWordExcel> words2Excels(List<BookWord> bookWordList) {
        List<BookWordExcel>datas =new ArrayList<>() ;
        if (!ListUtils.isEmpty(bookWordList)){
            for (BookWord bookWord : bookWordList) {
                ApiResult excel = bookWord2ExcelBookWord(bookWord);
                if (excel.getData()!=null){
                    datas.add((BookWordExcel) excel.getData());
                }
            }
        }
        return datas ;
    }
    @Override
    public List<BookWord> excels2words(List<BookWordExcel> bookWordExcels){
        List<BookWord> datas =new ArrayList<>() ;
        if (!ListUtils.isEmpty(bookWordExcels)){
            for (BookWordExcel bookWordExcel : bookWordExcels) {
                ApiResult word = excelBookWord2BookWord(bookWordExcel);
                if (word.isSuccess()) {
                    datas.add((BookWord) word.getData());
                }
            }
        }
        return datas;
    }



    public ApiResult deleteBooks(String ids){
        for (Long  id:idsMachine.deparseIds(ids)) {
            deleteBook(id);
        }
        return  new ApiResult(SUCCESS,null) ;
    }

    public ApiResult deleteBook(Long  id ){
        try {
            learnBookService.deleteById(id);
            List<LearnBook> caches =new ArrayList<>() ;
            if(books != null){
                for (int i = 0; i < books.size(); i++) {
                    LearnBook lb = books.get(i);
                    if(!String.valueOf(lb.getId()).equals(String.valueOf(id))){
                        caches.add(lb);
                    }
                }
                books =caches ;
            }
            return  new ApiResult(SUCCESS,null) ;
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult("error") ;
        }


    }

    private List<LearnBook> books;

    private LearnBook checkBook(BookWordExcel bookWordExcel){
        if (!ListUtils.isEmpty(books)){
            for (LearnBook book : books) {
                if (!bookWordExcel.getVerison().equals(book.getVersion())){
                    continue;
                }
                if (!bookWordExcel.getStage().equals(book.getStage())){
                    continue;
                }
                if (!bookWordExcel.getBookName().equals(book.getBookName())){
                    continue;
                }
                return  book ;
            }
        }
        return  null ;
    }

    /**
     * 只是进行转换并没有存入数据库；
     * @param bookWordExcel
     * @return
     */
    @Override
    public ApiResult excelBookWord2BookWord(BookWordExcel bookWordExcel) {
        if (StringUtils.isEmpty(bookWordExcel.getBookName())){
            return  new ApiResult(ResultMsg.PLEASE_CHECLK_EXCEL,null);
        }
        if (StringUtils.isEmpty(bookWordExcel.getVerison())){
            return  new ApiResult(ResultMsg.PLEASE_CHECLK_EXCEL,null);
        }
        if (StringUtils.isEmpty(bookWordExcel.getStage())){
            return  new ApiResult(ResultMsg.PLEASE_CHECLK_EXCEL,null);
        }
        if (StringUtils.isEmpty(bookWordExcel.getWord())){
            return  new ApiResult(ResultMsg.PLEASE_CHECLK_EXCEL,null);
        }
        BookWord bookWord =new BookWord() ;
            /**
             * 单元存在的情况下
             */
            //判断缓存
                LearnBook learnBook = checkBook(bookWordExcel);
                //判断数据库
                if (learnBook==null){
                    //看看book是否存在
                    books = learnBookService.findBooks(bookWordExcel.getVerison(), bookWordExcel.getBookName(),
                            null, bookWordExcel.getStage(), null, null, null, null, null, null).getContent();
                    //book不存在的情况下
                    if (ListUtils.isEmpty(books)){
                        learnBook =new LearnBook() ;
                        learnBook.setVersion(bookWordExcel.getVerison());
                        learnBook.setBookName(bookWordExcel.getBookName());
                        learnBook.setStage(bookWordExcel.getStage());
                        learnBook.setPrice(Config.BOOK_PRICE);
                        learnBook.setCreateTime(new Date());
                        try {
                            //存储课本
                            learnBook=  learnBookService.saveOrUpdate(learnBook);
                        } catch (SmartCommunityException e) {
                            e.printStackTrace();
                        }
                    }else{
                        //课本是存在的，那么就是unit不存在的
                        learnBook =books.get(0);
                    }
                }

        //redis缓存判断下；
        BookWord checkWord= new BookWord() ;
        checkWord.setWordName(bookWordExcel.getWord());
        checkWord.setLearnBook(learnBook);
        boolean exist = wordNameRedisService.checkWordExist(checkWord);
        if (exist){
            bookWord.setId(wordNameRedisService.getWordId(checkWord));
        }else{
            BookWord existWord = r.findByWordNameAndLearnBookId(checkWord.getWordName(), learnBook.getId());
            if (existWord!=null){
                bookWord =existWord ;
            }
        }

        bookWord.setLearnBook(learnBook);

        //和总词库的单词进行对比
        Word word =wordService.findByWordName(bookWordExcel.getWord());
        //word存在的情况下；
        if (word!=null) {
            bookWord= (BookWord) words2BookWord(bookWord,word).getData();
        }else{
            //不存在的情况下；
            bookWord.setInterpretation(bookWordExcel.getInterpretation());
            bookWord.setEnglishExample1(bookWordExcel.getEnglishExample1());
            bookWord.setExampleTranslation1(bookWordExcel.getExampleTranslation1());
            bookWord.setEnglishExample2(bookWordExcel.getEnglishExample2());
            bookWord.setExampleTranslation2(bookWordExcel.getExampleTranslation2());
            bookWord.setAssistantNotation(bookWordExcel.getAssistantNotation());
            bookWord.setRootAffixes(bookWordExcel.getRootAffixes());
            bookWord.setAboutWords(bookWordExcel.getAboutWords());
            bookWord.setSpare1(bookWordExcel.getSpare1());
            bookWord.setSpare2(bookWordExcel.getSpare2());
            bookWord.setWordName(bookWordExcel.getWord());
        }
        //设置其他的 版本 课本  信息；
        bookWord.setStage(bookWordExcel.getStage());
        bookWord.setVersion(bookWordExcel.getVerison());
        bookWord.setBookName(bookWordExcel.getBookName());

        return  new ApiResult(SUCCESS,bookWord);

    }

    //TODO  isRIGTH ??????????????????=====!!!!!!!
    @Override
    public ApiResult bookWord2ExcelBookWord(BookWord bookWord) {
        BookWordExcel bookWordExcel =new BookWordExcel();
        bookWordExcel.setOrderNum(String.valueOf(bookWord.getId()));
        bookWordExcel.setStage(bookWord.getStage());
        bookWordExcel.setVerison(bookWord.getVersion());
        bookWordExcel.setBookName(bookWord.getBookName());
        bookWordExcel.setUnitName(bookWord.getUnitName());
        bookWordExcel.setWord(bookWord.getWordName());
        bookWordExcel.setInterpretation(bookWord.getInterpretation());
        bookWordExcel.setEnglishExample1(bookWord.getEnglishExample1());
        bookWordExcel.setExampleTranslation1(bookWord.getExampleTranslation1());
        bookWordExcel.setEnglishExample2(bookWord.getEnglishExample2());
        bookWordExcel.setExampleTranslation2(bookWord.getExampleTranslation2());
        bookWordExcel.setAssistantNotation(bookWord.getAssistantNotation());
        bookWordExcel.setRootAffixes(bookWord.getRootAffixes());
        bookWordExcel.setAboutWords(bookWord.getAboutWords());
        bookWordExcel.setSpare1(bookWord.getSpare1());
        bookWordExcel.setSpare2(bookWord.getSpare2());

        return new ApiResult(SUCCESS,bookWordExcel);
    }

    @Override
    public ApiResult importForWords1(MultipartFile file) {

        List<BookWordExcel> wordExcels =  ExcelUtil.importExcel(file,0,1, BookWordExcel.class);

        try {
            boolean bll = true;
            //不符合标准的情况下；word不能够为空！！！！
            List<String> ls = new ArrayList();
            for (BookWordExcel wordExcel : wordExcels) {
                if(!StringUtils.isEmpty(wordExcel.getBookName())){
                    List<LearnBook> learnBook= learnBookService.findAllBooks(wordExcel.getVerison(),wordExcel.getBookName(),null,wordExcel.getStage());
                    if(learnBook.size() != 0){
                        bll = false;
                    }
                }
                if (StringUtils.isEmpty(wordExcel.getBookName()) || StringUtils.isEmpty(wordExcel.getStage())
                        ||StringUtils.isEmpty(wordExcel.getVerison()) ){
                    return  new ApiResult(ResultMsg.PLEASE_CHECLK_EXCEL,null);
                }
                ls.add(wordExcel.getBookName());
            }
            if(ls!=null){
                String ls1 = ls.get(0);
                for (String ls2:ls) {
                    if(!ls1.equals(ls2)){
                        return new ApiResult(ResultMsg.CH_BN_CHONGMING,null);
                    }
                }
            }
            List<BookWord> words =excels2words(wordExcels);
            for (BookWord word:words) {
                String wordName = word.getWordName();
                wordName = wordName.trim();
                while(wordName.startsWith("　")){
                    wordName = wordName.substring(1,wordName.length()).trim();
                }
                while (wordName.endsWith("　")){
                    wordName = wordName.substring(0,wordName.length()-1).trim();
                }
                word.setWordName(wordName);
            }
            //word
            ApiResult saveReuslt = saveForWords(words);

            if (!ListUtils.isEmpty(words)){
                List<BookWord> wordsall = r.findAllByLearnBookIdOrderByUnitDesAsc(words.get(0).getLearnBook().getId());

                int curentUnitIndex =1;

                //每三十个为一组；
                for (int i = 0; i < wordsall.size(); i+=PER_UNIT_WORD_NUM) {

                    int start=(curentUnitIndex-1)*PER_UNIT_WORD_NUM;
                    int end =curentUnitIndex*PER_UNIT_WORD_NUM;

                    if (start>=wordsall.size()){
                        break;
                    }
                    if (end>wordsall.size()){
                        end =wordsall.size();
                    }
                    String currentUnitName ="Unit"+curentUnitIndex ;
                    List<BookUnit> units = bookUnitDao.findUnits(words.get(0).getLearnBook().getId(), currentUnitName);
                    if (!units.isEmpty()){
                        List<BookWord> toSaveData = wordsall.subList(start, end);
                        for (BookWord toSaveDatum : toSaveData) {
                            toSaveDatum.setUnit(units.get(0));
                            toSaveDatum.setUnitName(currentUnitName);
                            toSaveDatum.setUnitDes(curentUnitIndex);
                        }
                        r.saveAll(toSaveData);
                    }else{
                        ApiResult bookUNit = bookUnitService.addOrUpdateUnit(null,currentUnitName,words.get(0).getLearnBook().getId());
                        if (bookUNit.isSuccess()){
                            List<BookWord> toSaveData = wordsall.subList(start, end);
                            for (BookWord toSaveDatum : toSaveData) {
                                toSaveDatum.setUnit((BookUnit) bookUNit.getData());
                                toSaveDatum.setUnitName(currentUnitName);
                                toSaveDatum.setUnitDes(curentUnitIndex);
                            }
                            r.saveAll(toSaveData);
                        }
                    }
                    curentUnitIndex++ ;
                }

            }
            if(bll){
                return  new ApiResult(ResultMsg.SUCCESS,saveReuslt);
            }else{
                return  new ApiResult(ResultMsg.CHANGCHUANGENGXIN,saveReuslt);
            }
//            return  saveReuslt ;
        } catch (SmartCommunityException e){
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null);
        }
    }




    @Override
    public ApiResult importForWords(MultipartFile file , Long bookid) {

        List<BookWordExcel> wordExcels =  ExcelUtil.importExcel(file,0,1, BookWordExcel.class);
        LearnBook learnBook= null;
        try {
            learnBook = learnBookService.findById(bookid);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        //不符合标准的情况下；word不能够为空！！！！
        for (BookWordExcel wordExcel : wordExcels) {
            if(learnBook != null){
                    String bookName = learnBook.getBookName();
                    String version = learnBook.getVersion();
                    String stage = learnBook.getStage();
                    if(!wordExcel.getBookName().equals(bookName) || !wordExcel.getVerison().equals(version) ||
                    !wordExcel.getStage().equals(stage)){
                        return new ApiResult("上传文件中内容与此课本不符,请检查后重新上传!");
                    }
                }
            if (StringUtils.isEmpty(wordExcel.getBookName()) || StringUtils.isEmpty(wordExcel.getStage())
                    ||StringUtils.isEmpty(wordExcel.getVerison()) ){
                return  new ApiResult(ResultMsg.PLEASE_CHECLK_EXCEL,null);
            }
        }
        List<BookWord> words =excels2words(wordExcels);
        //word
        try {
            ApiResult saveReuslt = saveForWords(words);

            if (!ListUtils.isEmpty(words)){
                List<BookWord> wordsall = r.findAllByLearnBookIdOrderByUnitDesAsc(words.get(0).getLearnBook().getId());

                int curentUnitIndex =1;

                //每三十个为一组；
                for (int i = 0; i < wordsall.size(); i+=PER_UNIT_WORD_NUM) {

                    int start=(curentUnitIndex-1)*PER_UNIT_WORD_NUM;
                    int end =curentUnitIndex*PER_UNIT_WORD_NUM;

                    if (start>=wordsall.size()){
                        break;
                    }
                    if (end>wordsall.size()){
                        end =wordsall.size();
                    }
                    String currentUnitName ="Unit"+curentUnitIndex ;
                    List<BookUnit> units = bookUnitDao.findUnits(words.get(0).getLearnBook().getId(), currentUnitName);
                    if (!units.isEmpty()){
                        List<BookWord> toSaveData = wordsall.subList(start, end);
                        for (BookWord toSaveDatum : toSaveData) {
                            toSaveDatum.setUnit(units.get(0));
                            toSaveDatum.setUnitName(currentUnitName);
                            toSaveDatum.setUnitDes(curentUnitIndex);
                        }
                        r.saveAll(toSaveData);
                    }else{
                    ApiResult bookUNit = bookUnitService.addOrUpdateUnit(null,currentUnitName,words.get(0).getLearnBook().getId());
                    if (bookUNit.isSuccess()){
                        List<BookWord> toSaveData = wordsall.subList(start, end);
                        for (BookWord toSaveDatum : toSaveData) {
                            toSaveDatum.setUnit((BookUnit) bookUNit.getData());
                            toSaveDatum.setUnitName(currentUnitName);
                            toSaveDatum.setUnitDes(curentUnitIndex);
                        }
                        r.saveAll(toSaveData);
                    }
                    }
                    curentUnitIndex++ ;
                }

            }
            return  saveReuslt ;
//            return  new ApiResult(ResultMsg.SUCCESS,saveReuslt);
        } catch (SmartCommunityException e){
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null);
        }
    }

    @Override
    public ApiResult bathExportWords(List<BookWord> words , HttpServletResponse response , String title , String sheetname , String fileName ) {
        if (!ListUtils.isEmpty(words)){
            List<BookWordExcel> excels =words2Excels(words);
            ExcelUtil.exportExcel(excels, title, sheetname, BookWordExcel.class, fileName, response);
            return  new ApiResult(ResultMsg.SUCCESS,"ok");
        }
        return new ApiResult(ResultMsg.LIST_DATA_IS_EMPTY,null) ;
    }


    /**
     * todo!!!
     * @param words
     * @return
     * @throws SmartCommunityException
     */
    @Override
    public ApiResult saveForWords(List<BookWord> words) throws SmartCommunityException {
        //redis ;
        wordNameRedisService.saveAllWord(r.findAll());

        if (!ListUtils.isEmpty(words)){
            for (BookWord word : words){
                boolean  exist = wordNameRedisService.checkWordExist(word);
                if (exist){
                    long id = wordNameRedisService.getWordId(word);
                    if (id!=0) {
                        word.setId(wordNameRedisService.getWordId(word));
                    }
                }
            }
            words= r.saveAll(words);
        }else{
            return new ApiResult(ResultMsg.PLEASE_CHECLK_EXCEL,null);
        }
        return new ApiResult(ResultMsg.SUCCESS,words);
    }


    @Override
    public ApiResult exportWords(String ids, String title, String sheetname, String filename, HttpServletResponse response) {
        try {
            List<BookWord> words =findByIds(ids);
            return  bathExportWords(words,response,title,sheetname,filename);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null);
        }
    }




    @Override
    public ApiResult words2BookWord(BookWord bookWord, Word word) {
        if(word!=null){
            bookWord.setWord(word);
            bookWord.setInterpretation(word.getInterpretation());
            bookWord.setEnglishExample1(word.getEnglishExample1());
            bookWord.setExampleTranslation1(word.getExampleTranslation1());
            bookWord.setEnglishExample2(word.getEnglishExample2());
            bookWord.setExampleTranslation2(word.getExampleTranslation2());
            bookWord.setAssistantNotation(word.getAssistantNotation());
            bookWord.setRootAffixes(word.getRootAffixes());
            bookWord.setAboutWords(word.getAboutWords());
            bookWord.setSpare1(word.getSpare1());
            bookWord.setSpare2(word.getSpare2());
            bookWord.setWordName(word.getWord());
        }
        return new ApiResult(SUCCESS,bookWord);
    }

    @Override
    public ApiResult findByWordName(String wordName) {
        BookWord bookword = r.findBookWordByWordName(wordName);
        if (bookword!=null){
            return  new ApiResult(SUCCESS,bookword) ;
        }
        return  new ApiResult(NOT_FOUND_ERROR,null) ;
    }

    @Override
    public ApiResult getBookWordsNum(String name, Long bookId, Long unitId) {
        return new ApiResult(SUCCESS,bookWordsDao.findBookWorsCount(bookId,name,unitId));
    }

    @Override
    public List<BookWord> markBookWrod(String ids, String markIds) throws SmartCommunityException {


        List<BookWord> bookWords =findByIds(ids);

        Map<Long , BookWord> bookWordMap =new HashMap<>() ;

        //设置所有的单词集合
        bookWords.forEach(bookWord -> {
            bookWordMap.put(bookWord.getId(),bookWord) ;
        });
        List<BookWord> markWords =findByIds(markIds) ;

        //获得mark单词集合
        Map<Long, BookWord> marksMap =new HashMap<>() ;

        markWords.forEach(bookWord -> {
            marksMap.put(bookWord.getId(),bookWord) ;
        });
        bookWords.clear();
        bookWordMap.forEach((aLong, bookWord) -> {
            if (marksMap.containsKey(aLong)){
                bookWord.setRight(false);
            }
            bookWords.add(bookWord) ;
        });
        return  bookWords ;
    }


}
