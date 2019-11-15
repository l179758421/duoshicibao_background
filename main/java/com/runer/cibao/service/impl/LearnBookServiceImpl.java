package com.runer.cibao.service.impl;

import com.alibaba.fastjson.JSON;
import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.LearnBookDao;
import com.runer.cibao.domain.*;
import com.runer.cibao.domain.repository.LearnBookRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.BookWordService;
import com.runer.cibao.service.LearnBookService;
import com.runer.cibao.util.DownloadUtil;
import com.runer.cibao.util.NormalUtil;
import com.runer.cibao.util.machine.JsonMachine;
import com.runer.cibao.util.machine.ZipMachine;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.ListUtils;

import java.io.File;
import java.util.*;


/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/11
 *
 *
 *
 *
 *
 **/

@Service
public class LearnBookServiceImpl extends BaseServiceImp<LearnBook, LearnBookRepository> implements LearnBookService {


    @Autowired
    LearnBookDao learnBookDao ;

    @Autowired
    BookWordService bookWordService;

    @Autowired
    JsonMachine jsonMachine ;

    @Autowired
    ZipMachine zipMachine ;


    @Override
    public LearnBook findById(Long id) throws SmartCommunityException {
        LearnBook learnBook = super.findById(id);
        Long count = (Long) findBookWordsNum(learnBook.getId()).getData();
        if (learnBook.getWordsNum()!=count){
            learnBook.setWordsNum(count);
            r.save(learnBook);
        }
        return  learnBook ;
    }

    @Override
    public List<LearnBook> findAllBooks(String version, String bookName, String grage, String stage) {
        return findBooks(version,bookName,grage,stage,null,null,null,null,1,Integer.MAX_VALUE).getContent();
    }

    @Override
    public Page<LearnBook> findBooks(String version, String bookName, String grade, String stage, String likeVerison, String likeName, String likeGrage, String likeStage, Integer page, Integer limit) {
        Page<LearnBook> result = learnBookDao.findLearnBooks(PageableUtil.basicPage(page, limit), version, bookName, grade, stage, likeVerison, likeName, likeGrage, likeStage);
        if (result.getContent()!=null&&!result.getContent().isEmpty()){
            for (LearnBook learnBook : result.getContent()) {
             Long count = (Long) findBookWordsNum(learnBook.getId()).getData();
             if (learnBook.getWordsNum()!=count){
                 learnBook.setWordsNum(count);
                 r.save(learnBook);
             }
            }
        }
        return  result ;
    }

    @Override
    public Long findBooksNum(String version, String bookName, String grade, String stage, String likeVerison, String likeName, String likeGrage, String likeStage) {
        return learnBookDao.getLearnBookCount(version,bookName,grade,stage,likeVerison,likeName,likeGrage,likeStage);
    }


    @Override
    public ApiResult addOrUpadateBook(Long id,Integer price , String version, String bookName,
                                      String grade, String wordsNum, String stage ,String imgUrl ,Long userId) {

        if (id==null) {
            ApiResult apiResult = findByBook(bookName, version, stage, grade);
            List<LearnBook> learnBooks = (List<LearnBook>) apiResult.getData();
            if (learnBooks.size() != 0) {
                return new ApiResult("该课本已存在!");
            }
        }

        LearnBook learnBook =new LearnBook() ;

        if (id!=null){
           ApiResult dataResult =    findByIdWithApiResult(id) ;
           if (dataResult.isFailed()){
               return  new ApiResult("课本不存在");
           }
           learnBook = (LearnBook) dataResult.getData();
        }

        learnBook.setId(id);
        learnBook.setVersion(version);
        learnBook.setBookName(bookName);
        learnBook.setGrade(grade);
        learnBook.setStage(stage);
        if(price==null){
            learnBook.setPrice(Config.BOOK_PRICE);
        }else{
            learnBook.setPrice(price);
        }

        learnBook.setImgUrl(imgUrl);
        if (id==null) {
            learnBook.setCreateTime(new Date());

        }

        if (userId==null){
            return  new ApiResult(ResultMsg.USER_ID_IS_NOT_ALLOWED_NULL,null);
        }
        User user =new User() ;
        user.setId(userId);
        learnBook.setUser(user);

        try {
            learnBook= saveOrUpdate(learnBook) ;
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null) ;
        }
        return new ApiResult(ResultMsg.SUCCESS,learnBook);
    }

    @Override
    public ApiResult addDownLoadNum(Long id, Long userId) {

       LearnBook learnBook ;

        try {
            learnBook=findById(id);

            if (learnBook.getDownLoadNum()==null){
                learnBook.setDownLoadNum(1L);
            }else{
                learnBook.setDownLoadNum(learnBook.getDownLoadNum()+1);
            }
           learnBook =  update(learnBook) ;
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null) ;
        }
        return new ApiResult(ResultMsg.SUCCESS,learnBook);
    }

    @Override
    public ApiResult findBookWordsNum(Long bookId) {
        return bookWordService.getBookWordsNum(null,bookId,null);
    }


    /**
     * 存储书本的封面；
     */
    @Value("${web.upload-cibaoPath}")
    private String rePath ;

    @Value("${web.upload-cibao}")
    private String abPath ;
    @Override
    public ApiResult uploadBookCover(MultipartFile file) {
              ApiResult fileResult = NormalUtil.saveMultiFile(file, rePath, abPath);
             return  fileResult ;
    }
    /**
     * 课本json的存储位置；
     */
    @Value("${web.upload-jsonPath}")
    private String jsonRePath ;

    @Value("${web.upload-json}")
    private String jsonAbPath ;


    /**
     * 课本zip的存储位置；
     */
    @Value("${web.upload-zipPath}")
    private String zipRepath ;

    @Value("${web.upload-zip}")
    private String zipAbPath ;




    @Override
    public ApiResult createBookMird(Long bookId){
        return  packageBook(bookId,false) ;
    }



    @Override
    public ApiResult zipBookForApp(Long bookId) {
        return  packageBook(bookId,true ) ;
    }

    private ApiResult  packageBook(Long bookId ,boolean zip){
        try {
            LearnBook learnBook =    findById(bookId);
            List<BookWord> bookWordList  =bookWordService.findAll(null,bookId,null);
            List<JsonWordBean> jsonWordBeans =new ArrayList<>() ;
            for (BookWord bookWord : bookWordList) {
                jsonWordBeans.add(bookWord.toJsonBean(bookId));
            }
            //jsonFile的书写；
            String  jsonSrc =jsonAbPath+learnBook.getId()+".json";
            jsonMachine.wirteToFile(JSON.toJSONString(jsonWordBeans),jsonSrc);
            String zipPath =zipAbPath+learnBook.getId();
            //audio;
            Map<String,String> datasPath = findWordAudiosPathes(bookId);
            datasPath.put(jsonSrc,"words.json");
           List<FilesZipBean> filesZipBeans =new ArrayList<>() ;
                if (zip) {
                    try {
                        zipMachine.insertIntoZip(datasPath, zipPath, "node.txt");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    datasPath.forEach((path, repath) ->{
                        FilesZipBean filesZipBean =new FilesZipBean() ;
                        filesZipBean.setFilePath(path);
                        filesZipBean.setFileOutPutPath("/"+learnBook.getId()+"/"+repath);
                        filesZipBeans.add(filesZipBean);
                    });
                }

            if (zip){
                return  new ApiResult(ResultMsg.SUCCESS,zipPath+".zip") ;
            }else{
                return  new ApiResult(ResultMsg.SUCCESS,filesZipBeans) ;
            }



        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null) ;
        }
    }



    @Value("${web.upload-audioPath}")
    private String audioRePaht ;
    @Value("${web.upload-audio}")
    private String audioAbPath ;
    @Value("${web.upload-path}")
    private String genPath ;


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
    public Map<String,String > findWordAudiosPathes(Long bookId) {

        Map<String,String> audiosPath =new HashMap<>();

        List<BookWord> bookWords = bookWordService.findAll(null,bookId,null);

        if (!ListUtils.isEmpty(bookWords)){
            for (BookWord bookWord : bookWords) {
                 if (bookWord.getWord()!=null){
                     String wordEnPath =abPathEng+bookWord.getWordName()+".mp3" ;
                     if (new File(wordEnPath).exists()){
                         audiosPath.put(wordEnPath,"/en/"+bookWord.getWord().getWord()+".mp3") ;
                     }

                     String wordUsaPath =abPathUsa+bookWord.getWordName()+".mp3";
                     if (new File(wordUsaPath).exists()){
                         audiosPath.put(wordUsaPath,"/usa/"+bookWord.getWord().getWord()+".mp3");
                     }
                 }
            }
        }
        return audiosPath;
    }

    @Override
    public ApiResult downLoadWordsZip(Long bookId ) {
       ApiResult apiResult =   zipBookForApp(bookId);
       addDownLoadNum(bookId,null);
       if (apiResult.isSuccess()){
           try {
               ResponseEntity<FileSystemResource> data = DownloadUtil.export((String) apiResult.getData());
               return  new ApiResult(ResultMsg.SUCCESS,data) ;
           } catch (Exception e) {
               e.printStackTrace();
               return  new ApiResult(ResultMsg.OS_ERROR,e.getMessage());
           }
       }else{
           return  apiResult ;
       }
    }

    @Override
    public ApiResult downlowdWordsZip(List<String> bookIds) {
        if (ListUtils.isEmpty(bookIds)){
            return  new ApiResult(ResultMsg.SUCCESS,new ArrayList<>()) ;
        }
        List<String> pathes =new ArrayList<>();
        for (String bookId : bookIds) {
             ApiResult apiResult =   zipBookForApp(Long.valueOf(bookId));
            if (apiResult.isSuccess()){
                String path = (String) apiResult.getData();
                pathes.add(path);
            }
        }
        //进行压缩；
        return  new ApiResult(ResultMsg.SUCCESS,pathes) ;
    }

    @Override
    public ApiResult downlowdWordsZipWithinNoZip(List<String> bookIds) {
        if (ListUtils.isEmpty(bookIds)){
            return  new ApiResult(ResultMsg.SUCCESS,null) ;
        }
        List<FilesZipBean> filesZipBeans =new ArrayList<>() ;
        for (String bookId : bookIds) {
            ApiResult apiResult = createBookMird(Long.valueOf(bookId));
                List<FilesZipBean> path = (List<FilesZipBean>) apiResult.getData();
                filesZipBeans.addAll(path);
        }
        //进行压缩；
        return  new ApiResult(ResultMsg.SUCCESS,filesZipBeans) ;
    }


    @Override
    public ApiResult findAllStage() {
        return new ApiResult(ResultMsg.SUCCESS,learnBookDao.findAllStage());
    }

    @Override
    public ApiResult findGrages(String stage) {
        return new ApiResult(ResultMsg.SUCCESS,learnBookDao.findGrade(stage));
    }



    @Override
    public ApiResult findByBook(String bookName, String version, String stage, String grade) {
        return new ApiResult(ResultMsg.SUCCESS,learnBookDao.findByBook(bookName,version,stage,grade));
    }
}
