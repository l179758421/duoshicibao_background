package com.runer.cibao.service.impl;


import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.BookWordsDao;
import com.runer.cibao.dao.WordDao;
import com.runer.cibao.domain.*;
import com.runer.cibao.domain.repository.WordRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.WordAudioRedisService;
import com.runer.cibao.service.WordService;
import com.runer.cibao.util.ExcelUtil;
import com.runer.cibao.util.NormalUtil;
import com.runer.cibao.util.page.PageableUtil;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.scene.chart.ScatterChart;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.ListUtils;
import org.zeroturnaround.zip.NameMapper;
import org.zeroturnaround.zip.ZipUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/14
 **/


@Service
public class WordServiceImpl extends BaseServiceImp<Word, WordRepository> implements WordService {


    @Autowired
    WordDao wordDao;
    @Autowired
    BookWordsDao bookWordsDao ;
    @Autowired
    WordAudioRedisService wordAudioRedisService ;


    @Value("${web.upload-path}")
    private String upload_path;

    @Value("${web.upload-basePath}")
    private String upload_base_path;

    @Override
    public Page<Word> findWords(String wordName, String rootAffixes, String wordGetName, Integer page, Integer limit) {
        Page<Word> wordPage = wordDao.findWords(wordName, rootAffixes, wordGetName, PageableUtil.basicPage(page, limit));
        for (Word word:wordPage) {

            String engFilePath = abPathEng+word.getWord()+".mp3";
            File testFileEng = new File(engFilePath);
            if(testFileEng.exists()){
                word.setWordAudioUrl(rePathEng+word.getWord()+".mp3");
            }
            String usaFilePath = abPathUsa+word.getWord()+".mp3";
            File testFileUsa = new File(usaFilePath);
            if(testFileUsa.exists()){
                word.setUsaAudioUrl(rePathUsa+word.getWord()+".mp3");
            }
        }
        return wordPage;
    }
    /*todo   word.setSyllabification(excel.getSyllabification());*/
    @Override
    public ApiResult addOrUpdateWord(Long id, String word, String phonetic_symbol,
                                     String americanPronunciation, String englishPronunciation,
                                     String interpretation, String englishExample1, String exampleTranslation1,
                                     String englishExample2, String exampleTranslation2, String assistantNotation,
                                     String rootAffixes, String aboutWords, String spare1, String spare2) {
        Word inputWord = new Word();
        if (id != null) {
            try {
                inputWord = findById(id);
            } catch (SmartCommunityException e) {
                e.printStackTrace();
                return new ApiResult(e.getResultMsg(), null);
            }
        }else{
            Word testExistWord = findByWordName(word);

            if (testExistWord != null) {
                return new ApiResult(ResultMsg.WORD_NAME_EXISTED, null);
            }
        }
        if (StringUtils.isEmpty(word)) {
            return new ApiResult(ResultMsg.WORD_IS_NULL, null);
        }
        if (StringUtils.isEmpty(interpretation)) {
            return new ApiResult(ResultMsg.INTERPRETATION_IS_NULL, null);
        }
        if (StringUtils.isEmpty(englishExample1)) {
            return new ApiResult("英文示例1不能为空");
        }
        if (StringUtils.isEmpty(exampleTranslation1)) {
            return new ApiResult("示例翻译1不能为空");
        }

        inputWord.setId(id);
        inputWord.setWord(word);
        inputWord.setPhonetic_symbol(phonetic_symbol);
        inputWord.setAmericanPronunciation(americanPronunciation);
        inputWord.setEnglishPronunciation(englishPronunciation);
        inputWord.setInterpretation(interpretation);
        inputWord.setEnglishExample1(englishExample1);
        inputWord.setExampleTranslation1(exampleTranslation1);
        inputWord.setEnglishExample2(englishExample2);
        inputWord.setExampleTranslation2(exampleTranslation2);
        inputWord.setAssistantNotation(assistantNotation);
        inputWord.setRootAffixes(rootAffixes);
        inputWord.setAboutWords(aboutWords);
        inputWord.setSpare1(spare1);
        inputWord.setSpare2(spare2);


        if (new File(abPathEng+word+".mp3").exists()){
            inputWord.setWordAudioUrl(rePathEng+word+".mp3");
        }

        if (new File(abPathUsa+word+".mp3").exists()){
            inputWord.setUsaAudioUrl(rePathUsa+word+".mp3");
        }


//
//if(searchFiles( new File(pathEng),word+".mp3").size() != 0){
//      inputWord.setWordAudioUrl(pathEng+word+".mp3");
//}
//
// String pathUsa = abPathUsa;
//      if(searchFiles( new File(pathUsa),word+"mp3").size() != 0){
//         inputWord.setUsaAudioUrl(pathUsa+word+".mp3");
//       }
        inputWord = r.save(inputWord);

        return new ApiResult(ResultMsg.SUCCESS, inputWord);
    }





//    public static List<File> searchFiles(File folder, final String keyword) {
//
//
//        List<File> result = new ArrayList<File>();
//        if (folder.isFile())
//            result.add(folder);
//
//        File[] subFolders = folder.listFiles(new FileFilter() {
//            public boolean accept(File file) {
//                if (file.isDirectory()) {
//                    return true;
//                }
//                if (file.getName().toLowerCase().contains(keyword)) {
//                    return true;
//                }
//                return false;
//            }
//        });
//
//        if (subFolders != null) {
//            for (File file : subFolders) {
//                if (file.isFile()) {
//                    // 如果是文件则将文件添加到结果列表中
//                    result.add(file);
//                } else {
//                    // 如果是文件夹，则递归调用本方法，然后把所有的文件加到结果列表中
//                    result.addAll(searchFiles(file, keyword));
//                }
//            }
//        }
//
//        return result;
//    }

    @Override
    public Word wordExcel2Word(WordExcel excel) {

        //不符合标准的情况下；word不能够为空！！！！(序号/单词划分/单词/释义/英文例句1/例句翻译1)不能为null

        if (excel != null && !StringUtils.isEmpty(excel.getWord()) && !StringUtils.isEmpty(excel.getOrderNum()) && !StringUtils.isEmpty(excel.getSyllabification()) && !StringUtils.isEmpty(excel.getInterpretation())
                && !StringUtils.isEmpty(excel.getEnglishExample1()) && !StringUtils.isEmpty(excel.getExampleTranslation1())) {

            Word word = new Word();
            word.setWord(excel.getWord());
            word.setPhonetic_symbol(excel.getPhonetic_symbol());
            word.setAmericanPronunciation(excel.getAmericanPronunciation());
            word.setEnglishPronunciation(excel.getEnglishPronunciation());
            word.setInterpretation(excel.getInterpretation());
            word.setEnglishExample1(excel.getEnglishExample1());
            word.setExampleTranslation1(excel.getExampleTranslation1());
            word.setEnglishExample2(excel.getEnglishExample2());
            word.setExampleTranslation2(excel.getExampleTranslation2());
            word.setAssistantNotation(excel.getAssistantNotation());
            word.setRootAffixes(excel.getRootAffixes());
            word.setAboutWords(excel.getAboutWords());
            word.setSpare1(excel.getSpare1());
            word.setSpare2(excel.getSpare2());
            word.setSyllabification(excel.getSyllabification());

            String engFilePath = abPathEng+word.getWord()+".mp3";
            File testFileEng = new File(engFilePath);
            if(testFileEng.exists()){
                word.setWordAudioUrl(rePathEng+word.getWord()+".mp3");
            }

            String usaFilePath = abPathUsa+word.getWord()+".mp3";
            File testFileUsa = new File(usaFilePath);
            if(testFileUsa.exists()){
                word.setUsaAudioUrl(rePathUsa+word.getWord()+".mp3");
            }


            return word;
        }
        return null;
    }

    @Override
    public WordExcel word2wordExcel(Word word) {
        if (word != null && !StringUtils.isEmpty(word.getWord())) {
            WordExcel excel = new WordExcel();
            if(word.getId() == null){
                excel.setOrderNum("");
            }else{
                excel.setOrderNum(String.valueOf(word.getId()));
            }
            excel.setWord(word.getWord());
            excel.setPhonetic_symbol(word.getPhonetic_symbol());
            excel.setAmericanPronunciation(word.getAmericanPronunciation());
            excel.setEnglishPronunciation(word.getEnglishPronunciation());
            excel.setInterpretation(word.getInterpretation());
            excel.setEnglishExample1(word.getEnglishExample1());
            excel.setExampleTranslation1(word.getExampleTranslation1());
            excel.setEnglishExample2(word.getEnglishExample2());
            excel.setExampleTranslation2(word.getExampleTranslation2());
            excel.setAssistantNotation(word.getAssistantNotation());
            excel.setRootAffixes(word.getRootAffixes());
            excel.setAboutWords(word.getAboutWords());
            excel.setSpare1(word.getSpare1());
            excel.setSpare2(word.getSpare2());
            excel.setSyllabification(word.getSyllabification());

            switch (word.getAudioState()){
                case 1:
                    excel.setAudioUrl("全部缺失");
                    break;
                case 2:
                    excel.setAudioUrl("美式音频缺失");
                    break;
                case 3:
                    excel.setAudioUrl("英式音频缺失");
                    break;
                default:
                    excel.setAudioUrl("√");
                    break;
            }

            return excel;
        }
        return null;
    }

    @Override
    public List<Word> excelsToWords(List<WordExcel> wordExcels) {

        List<Word> words = new ArrayList<>();
        if (!ListUtils.isEmpty(wordExcels)) {
            for (WordExcel wordExcel : wordExcels) {
                Word word = wordExcel2Word(wordExcel);
                if (word != null) {
                    words.add(word);
                }
            }
        }
        return words;
    }

    @Override
    public List<WordExcel> words2Excels(List<Word> words) {
        List<WordExcel> excels = new ArrayList<>();
        if (!ListUtils.isEmpty(words)) {
            for (Word word : words) {
                WordExcel excel = word2wordExcel(word);
                if (excel != null) {
                    excels.add(excel);
                }
            }
        }
        return excels;
    }


    @Override
    public ApiResult exportWords(String ids, String title, String sheetname) {
        try {
            List<Word> words = findByIds(ids);
            if (words.size() != 0) {
                for (Word word : words) {
                    if (!new File(abPathEng + word.getWord() + ".mp3").exists() && !new File(abPathUsa + word.getWord() + ".mp3").exists()) {
                        word.setAudioState(1);
                    } else {
                        if (!new File(abPathEng + word.getWord() + ".mp3").exists()) {
                            word.setAudioState(2);
                        }
                        if (!new File(abPathUsa + word.getWord() + ".mp3").exists()) {
                            word.setAudioState(3);
                        }
                    }
                }
            }
            return exportWords(words, title, sheetname);
        }catch (Exception e){
            return  new ApiResult(ResultMsg.OS_ERROR,null);
        }
    }

    /**
     *音频缺失导出
     */
    @Override
    public ApiResult exportWordsAudioUrl(String title, String sheetname,
                                         String fileName, HttpServletResponse response) {

        List<Word> listWords = findAll();
        List<Word> words = new ArrayList<>();
        for (Word word:listWords) {
            if(!new File(abPathEng+word.getWord()+".mp3").exists() && !new File(abPathUsa+word.getWord()+".mp3").exists()){
                word.setAudioState(1);
                words.add(word);
            }else{
                if(!new File(abPathEng+word.getWord()+".mp3").exists()){
                    word.setAudioState(2);
                    words.add(word);
                }
                if(!new File(abPathUsa+word.getWord()+".mp3").exists()){
                    word.setAudioState(3);
                    words.add(word);
                }
            }
        }
       return exportWords(words, title, sheetname);
    }

    @Override
    public ApiResult exportWords2(Long id, String title, String sheetname, String filename, HttpServletResponse response) {
        List<Word> words = new ArrayList<>();
        List<BookWord> bookWords = bookWordsDao.findAll(id,null,null);
        for (BookWord bookWord:bookWords) {
            if(bookWord.getWord() == null){
                Word ww = new Word();
                ww.setWord(bookWord.getWordName());
                if(!new File(abPathEng+bookWord.getWordName()+".mp3").exists() && !new File(abPathUsa+bookWord.getWordName()+".mp3").exists()){
                    ww.setAudioState(1);
                }else{
                    if(!new File(abPathEng+bookWord.getWordName()+".mp3").exists()){
                        ww.setAudioState(2);
                    }
                    if(!new File(abPathUsa+bookWord.getWordName()+".mp3").exists()){
                        ww.setAudioState(3);
                    }
                }
                words.add(ww);
            }
        }
        return  exportWords(words,title,sheetname);
    }


    @Override
    public ApiResult exportWords(List<Word> words, String title, String sheetname) {
        String  path=upload_path+title+".xls";
        String  basePath=upload_base_path+title+".xls";
        try  {
            OutputStream out = new FileOutputStream(path);
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            Sheet sheet1 = new Sheet(1, 0, ExcelPropertyIndexModel.class);
            sheet1.setSheetName(sheetname);
            List<ExcelPropertyIndexModel> data = new ArrayList<>();
            for(Word word : words){
                ExcelPropertyIndexModel item = new ExcelPropertyIndexModel();
                if (word != null) {
                    if(word.getId() == null){
                        item.setOrderNum("");
                    }else{
                        item.setOrderNum(String.valueOf(word.getId()));
                    }
                    item.setWord(word.getWord());
                    item.setPhonetic_symbol(word.getPhonetic_symbol());
                    item.setAmericanPronunciation(word.getAmericanPronunciation());
                    item.setEnglishPronunciation(word.getEnglishPronunciation());
                    item.setInterpretation(word.getInterpretation());
                    item.setEnglishExample1(word.getEnglishExample1());
                    item.setExampleTranslation1(word.getExampleTranslation1());
                    item.setEnglishExample2(word.getEnglishExample2());
                    item.setExampleTranslation2(word.getExampleTranslation2());
                    item.setAssistantNotation(word.getAssistantNotation());
                    item.setRootAffixes(word.getRootAffixes());
                    item.setAboutWords(word.getAboutWords());
                    item.setSpare1(word.getSpare1());
                    item.setSpare2(word.getSpare2());
                    item.setSyllabification(word.getSyllabification());
                    switch (word.getAudioState()){
                        case 1:
                            item.setAudioUrl("全部缺失");
                            break;
                        case 2:
                            item.setAudioUrl("美式音频缺失");
                            break;
                        case 3:
                            item.setAudioUrl("英式音频缺失");
                            break;
                        default:
                            item.setAudioUrl("√");
                            break;
                    }
                }
                data.add(item);
            }
            writer.write(data, sheet1);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(ResultMsg.OS_ERROR, null);
        }
        return new ApiResult(ResultMsg.SUCCESS, basePath);
    }

    @Override
    public ApiResult importWords(MultipartFile wordsFile) {
        List<WordExcel> wordExcels = ExcelUtil.importExcel(wordsFile, 0, 1, WordExcel.class);

        if (ListUtils.isEmpty(wordExcels)){
            return  new ApiResult("上传的内容必填选项未填写或者数据为空");
        }

        List<Word> words = excelsToWords(wordExcels);

        for (Word word:words) {
            String wordName = word.getWord();
            wordName = wordName.trim();
            while(wordName.startsWith("　")){
                wordName = wordName.substring(1,wordName.length()).trim();
            }
            while (wordName.endsWith("　")){
                wordName = wordName.substring(0,wordName.length()-1).trim();
            }
            word.setWord(wordName);
        }

        if(words.size() == 0){
            return new ApiResult("请检查必填项是否为空");
        }

        try {
            return saveForWords(words);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
        return new ApiResult(ResultMsg.SUCCESS, words);
    }

    @Override
    public ApiResult saveForWords(List<Word> words) throws SmartCommunityException {
        if (!ListUtils.isEmpty(words)) {
            Map<String, Word> wordMap = new HashMap<>();
            for (Word word : words) {
                Word sqlWord = r.findWordByWord(word.getWord());
                if (sqlWord != null) {
                    word.setId(sqlWord.getId());
                }
                wordMap.put(word.getWord(), word);
            }
            words = new ArrayList<>();
            List<Word> finalWords = words;
            wordMap.forEach((str, word) -> {
                finalWords.add(word);
            });
            words = r.saveAll(finalWords);
        } else {
            throw new SmartCommunityException(ResultMsg.PLEASE_CHECLK_EXCEL);
        }
        return new ApiResult(ResultMsg.SUCCESS, words);
    }

    @Override
    public Word findByWordName(String wordname) {
        return r.findWordByWord(wordname);
    }


    /**
     * web.upload-audioPath=audio/
     * web.upload-audio=${web.upload-path}${web.upload-audioPath}
     *
     * @param wordAudio
     * @return
     */
    @Value("${web.upload-audioPath}")
    private String rePaht;// audio/

    /**
     * 相对路径
     */
    @Value("${web.upload-engPath}")
    private String rePathEng;  // audio/eng/

    @Value("${web.upload-usaPath}")
    private String rePathUsa; // audio/usa/

    /**
     * 绝对路径
     */
    @Value("${web.upload-audioEng}")
    private String abPathEng;  // C:CiBao/audio/eng/
    @Value("${web.upload-audioUsa}")
    private String abPathUsa; // // C:CiBao/audio/usa/


    @Value("${web.upload-audio}")
    private String abPath;// C:CiBao/audio/

    @Value("${web.upload-path}") // C:/CiBao/
    private String upLoadPath ;


    @Value("${web.upload-audioZipPath}") //英式相对
    private String audioZipPaht ;
    @Value("${web.upload-audioZip}")//英式绝对
    private String audioZipAbPath;

    @Value("${web.upload-audioZipPathUsa}")//美式zip相对路径
    private String audioZipPathUsa;

    @Value("${web.upload-audioZipUsa}")//美式zip绝对路径
    private String audioZipABPathUsa;


    @Override
    public ApiResult uploadWordAudio(MultipartFile wordAudio,String type) {
        ApiResult result = NormalUtil.saveMultiFileWithOriginalName(wordAudio, rePathEng,rePathUsa, abPathEng,abPathUsa,type);
        if (result.getMsgCode() == ResultMsg.SUCCESS.getMsgCode()) {
            AudioFileEntity audioFileEntity = (AudioFileEntity) result.getData();
            Word word = findByWordName(audioFileEntity.getFileName());
            if (word != null) {
               String path=audioFileEntity.getFilePath();
               String audioType= path.split("/")[1];
               if(audioType.equals("eng")){
                    word.setWordAudioUrl(audioFileEntity.getFilePath());
                }else{
                    word.setUsaAudioUrl(audioFileEntity.getFilePath());
                }
                r.save(word);
            }
        }
        return result;
    }

    /**
     * 上传语音文件的压缩文件；
     * @param wordAudioZip
     * @return
     */
    @Override
    public ApiResult uploadWordAudioZip(MultipartFile wordAudioZip,String type) {
        ApiResult result = NormalUtil.saveMultiFileZip(wordAudioZip, audioZipPaht, audioZipAbPath,audioZipPathUsa,audioZipABPathUsa,type);
        if (result.isSuccess()){
            String filePath = (String) result.getData();
            File file =new File(upLoadPath+filePath);
            if(type.equals("1")){
                File f=new File(abPathEng);
                f.mkdirs();
            }else{
                File f=new File(abPathUsa);
                f.mkdirs();
            }

            List<WordsAudio> newFiles =new ArrayList<>() ; // C:CiBao/audio/eng/
            if(type.equals("1")){
                FileUtils.listFiles(new File(abPathEng), new String[]{"mp3"}, true).forEach(file1 -> {
                    WordsAudio wordsAudio =new WordsAudio() ;
                    wordsAudio.setName(file1.getName());
                    if (!wordAudioRedisService.checkWordExist(wordsAudio)){
                        newFiles.add(wordsAudio);
                    }
                });
            }else{                     // C:CiBao/audio/usa/
                FileUtils.listFiles(new File(abPathUsa), new String[]{"mp3"}, true).forEach(file1 -> {
                    WordsAudio wordsAudio =new WordsAudio() ;
                    wordsAudio.setName(file.getName());
                    if (!wordAudioRedisService.checkWordExist(wordsAudio)){
                        newFiles.add(wordsAudio);
                    }
                });
            }

            wordAudioRedisService.saveAllWord(newFiles);
            List<Word> savesWords =new ArrayList<>() ;

            if(type.equals("1")){
                //下面进行解压操作；
                ZipUtil.unpack(file, new File(abPathEng), new NameMapper() { //  C:CiBao/audio/eng/

                    @Override
                    public String map(String s) {
                        //取最后的值；
                        String[] st = s.split("/");
                        String  filename=st[st.length-1];
                        //不是文件的情况下，过滤；
                        if (filename.contains("_")||!filename.contains(".")){
                            return  null ;
                        }
                        WordsAudio wordsAudio =new WordsAudio() ;
                        wordsAudio.setName(filename);
                        //检查缓存；
                        if (!wordAudioRedisService.checkWordExist(wordsAudio)){
                            //检查单词的语音；
                            Word word =  findByWordName(filename.substring(0,filename.lastIndexOf(".")));
                            if (word!=null){
                                word.setWordAudioUrl(rePathEng+filename);  // audio/eng/
                                savesWords.add(word) ;
                            }
                            return  filename ;
                        }
                        return null;
                    }
                });
            }else{
                //下面进行解压操作；
                ZipUtil.unpack(file, new File(abPathUsa), new NameMapper() { //  C:CiBao/audio/usa
                    @Override
                    public String map(String s) {
                        //取最后的值；
                        String[] st = s.split("/");
                        String  filename=st[st.length-1];
                        //不是文件的情况下，过滤；
                        if (filename.contains("_")||!filename.contains(".")){
                            return  null ;
                        }
                        WordsAudio wordsAudio =new WordsAudio() ;
                        wordsAudio.setName(filename);
                        //检查缓存；
                        if (!wordAudioRedisService.checkWordExist(wordsAudio)){
                            //检查单词的语音；
                            Word word =  findByWordName(filename.substring(0,filename.lastIndexOf(".")));
                            if (word!=null){
                                word.setUsaAudioUrl(rePathUsa+filename);  // audio/usa
                                savesWords.add(word) ;
                            }
                            return  filename ;
                        }
                        return null;
                    }
                });
            }
            r.saveAll(savesWords) ;
            return  new ApiResult(ResultMsg.SUCCESS,null) ;
        }
        return new ApiResult("上传失败");
    }

    @Override
    public ApiResult getWordAudioList() {
        File f = new File(abPath);
        if (!f.exists()) {
            return new ApiResult("找不到文件");
        }
        List<WordsAudio> fileList = new ArrayList<>();
        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            WordsAudio wordsAudio =new WordsAudio() ;
            wordsAudio.setName(fs.getName());
            fileList.add(wordsAudio);
        }
        return new ApiResult(ResultMsg.SUCCESS,fileList);
    }

    @Override
    public ApiResult getById(Long id){
       if(id==null){
           return new ApiResult(ResultMsg.NOT_FOUND,null);
       }
        Word byId = null;
       try{
           byId = findById(id);
       }catch (SmartCommunityException e){
           return new ApiResult(ResultMsg.NOT_FOUND,null);
       }
       if(byId==null){
           return new ApiResult(ResultMsg.NOT_FOUND,null);
       }
        return new ApiResult(ResultMsg.SUCCESS,byId);
    }

}
