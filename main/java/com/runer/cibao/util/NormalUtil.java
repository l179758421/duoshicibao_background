package com.runer.cibao.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.base.PageApiResult;
import com.runer.cibao.domain.AudioFileEntity;
import com.runer.cibao.domain.PersonalLearnUnit;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.BaseService;
import com.runer.cibao.util.machine.DateMachine;
import com.runer.cibao.util.page.PageableUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

import static com.runer.cibao.exception.ResultMsg.LIST_DATA_UNKNOW_ERROR;
import static com.runer.cibao.exception.ResultMsg.SUCCESS;

/**
 * @Author szhua
 * 2018-05-16
 * 10:53
 * @Descriptionssmartcommunity== NormalUtil
 **/
public class NormalUtil {


    public static Date[] getSevenDate(){
        Date [] dates =new Date[7] ;
        //周一
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date monday = cal.getTime() ;
        for (int i = 0; i <7 ; i++){
            dates[i]=DateUtils.addDays(monday,i) ;
        }
        return  dates ;
    }

    /**
     * 获得范围日期；
     * @param startDate
     * @param endDate
     * @return
     */
    public static Date[] rangeDate(Date startDate ,Date endDate){
        Date[] ranges =new Date[2] ;
        Date resultStart =null ;
        Date resultEnd =null ;
        if (startDate!=null&&endDate==null){
            resultStart =startDate ;
            resultEnd =new Date() ;
        }
        if (startDate==null&&endDate!=null){
            startDate=DateUtils.addDays(endDate,Integer.MAX_VALUE+1);
            resultEnd =endDate ;
        }
        if (startDate!=null&&endDate!=null){
            resultStart =startDate ;
            resultEnd =endDate ;
        }

        if (startDate==null&&endDate==null){
        return  null ;
        }

        ranges[0] =resultStart ;
        ranges[1]= resultEnd ;

        return  ranges ;



    }




    /**
     * ll
     */
    public interface  MapDataCall {
        void putValue(Map<String, Object> data) ;
    }

    public static Map<String,Object> generateMapData(MapDataCall mapDataCall){
        Map<String,Object> data =new HashMap<>();
        mapDataCall.putValue(data);
        return  data ;
    }


    public static Pageable createLargestPage(){
      return   PageableUtil.basicPage(1,Integer.MAX_VALUE) ;
    }



    public static  String JacksonJson(Object object){
        try {
            return  new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return  null ;
        }
    }


    /*

     */
    public static LayPageResult createLayPageReuslt(Page page){
        LayPageResult layPageResult = new LayPageResult<>();
        layPageResult.setCode(0);
        layPageResult.setCount(page.getTotalElements());
        layPageResult.setMsg("");
        layPageResult.setData(page.getContent());
        return  layPageResult ;
    }

    /**
     * 创建pageResult ;
     * @param page
     * @return
     */
    public static PageApiResult createPageResult(Page page){

        PageApiResult pageApiResult =new PageApiResult<>() ;

        if(page==null){
            pageApiResult.setMsgCode(LIST_DATA_UNKNOW_ERROR.getMsgCode());
            pageApiResult.setMsg(LIST_DATA_UNKNOW_ERROR.getMessage());
            pageApiResult.setDatas(new ArrayList<>());
            pageApiResult.setCurrentPage(1);
            pageApiResult.setAllCount(0);
            return  pageApiResult ;
        }

        pageApiResult.setAllCount(page.getTotalElements());
        pageApiResult.setDatas(page.getContent());
        pageApiResult.setCurrentPage(page.getPageable().getPageNumber()+1);
        pageApiResult.setMsg(ResultMsg.SUCCESS.getMessage());
        pageApiResult.setMsgCode(ResultMsg.SUCCESS.getMsgCode());

        return  pageApiResult ;
    }




    public static  ApiResult saveMultiFileWithOriginalName(MultipartFile file,String rePathEng,String rePathUsa,String engPath, String usaPath,String type){
        if (file==null){
            return new ApiResult(ResultMsg.FILE_IS_NULL,null);
        }

        if (!file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            File saveFile=null;
            if(type.equals("1")){
                saveFile= new File(engPath+ originalFilename);
            }else{
                 saveFile = new File(usaPath+ originalFilename);
            }

            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(saveFile));
                out.write(file.getBytes());
                out.flush();
                out.close();

                AudioFileEntity audioFileEntity =new AudioFileEntity() ;
                audioFileEntity.setFileName(originalFilename.substring(0,originalFilename.lastIndexOf(".")));
                if(type.equals("1")){
                    audioFileEntity.setFilePath(rePathEng+originalFilename);
                }else{
                    audioFileEntity.setFilePath(rePathUsa+originalFilename);
                }


                return  new ApiResult(ResultMsg.UPLOAD_FILE_SUCCESS,audioFileEntity);
            }catch (Exception e) {
                e.printStackTrace();
                return new ApiResult(ResultMsg.UPLOAD_FILE_ERROR,null);
            }
        } else {
            return new ApiResult(ResultMsg.FILE_IS_NULL,null);
        }
    }


    /**
     * Save 单个的file；
     * @param file
     * @param filePath
     * @param fileAbsolutePath
     * @return
     */
    public static ApiResult saveMultiFile(MultipartFile file , String filePath , String fileAbsolutePath){

        if (file==null){
            return new ApiResult(ResultMsg.FILE_IS_NULL,null);
        }

        if (!file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            String saveFileName =UUID.randomUUID()+"."+originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            File saveFile= new File(fileAbsolutePath+ saveFileName);
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(saveFile));
                out.write(file.getBytes());
                out.flush();
                out.close();
                return  new ApiResult(ResultMsg.UPLOAD_FILE_SUCCESS,filePath+saveFileName);
            }catch (Exception e) {
                e.printStackTrace();
                return new ApiResult(ResultMsg.UPLOAD_FILE_ERROR,null);
            }
        } else {
            return new ApiResult(ResultMsg.FILE_IS_NULL,null);
        }
    }

    /**
     * Save 单个的zip；
     * @param file
     * @param filePath
     * @param fileAbsolutePath
     * @return
     */
    public static ApiResult saveMultiFileZip(MultipartFile file , String filePath , String fileAbsolutePath,String filePathUsa,String fileAbsolutePathUsa,String type){

        if (file==null){
            return new ApiResult(ResultMsg.FILE_IS_NULL,null);
        }

        if (!file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            String saveFileName =UUID.randomUUID()+"."+originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            File saveFile=null;
            if(type.equals("1")){
                saveFile = new File(fileAbsolutePath+ saveFileName);
            }else{
                saveFile = new File(fileAbsolutePathUsa+ saveFileName);
            }

            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(saveFile));
                out.write(file.getBytes());
                out.flush();
                out.close();
                String path="";
                if(type.equals("1")){
                    path=filePath+saveFileName;
                }else{
                    path=filePathUsa+saveFileName;
                }
                return  new ApiResult(ResultMsg.UPLOAD_FILE_SUCCESS,path);
            }catch (Exception e) {
                e.printStackTrace();
                return new ApiResult(ResultMsg.UPLOAD_FILE_ERROR,null);
            }
        } else {
            return new ApiResult(ResultMsg.FILE_IS_NULL,null);
        }
    }


    /**
     * DeleteById  以id为单位删除数据
     * @param baseService
     * @param id
     * @return
     */
    public  static ApiResult deleteById(BaseService baseService , Long id ){
        try {
            baseService.deleteById(id);
            return  new ApiResult(ResultMsg.SUCCESS,null);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null);
        }
    }


    /**
     * deleteByIds  以ids为单位批量的进行删除
     * @param baseService
     * @param ids
     * @return
     */
    public static  ApiResult deleteByIds(BaseService baseService ,String ids){
        try {
            baseService.deleteByIds(ids);
            return  new ApiResult(ResultMsg.SUCCESS,null) ;
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null) ;
        }
    }

    public static  ApiResult generateSuccessResult(Object data){
       return  new ApiResult(SUCCESS,data) ;
    }


    /**
     * 导出下载文件；
     * @param file
     * @return
     */
    public ResponseEntity<FileSystemResource> export(File file) {
        if (file == null) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + file.getName());
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new FileSystemResource(file));
    }


    public static  int  reviewTestTimes(PersonalLearnUnit personalLearnUnit ){
        int state =personalLearnUnit.getReviewTestState();
        if (state==0){
            return  1 ;
        }else if (state==1){
            return  2 ;
        }else if (state==2){
            return  4 ;
        }else  if (state==4){
            return  7 ;
        }else  if (state==7){
            return  15 ;
        }
        return  1 ;
    }



    /**
     * 单元是否需要进行复习；
     * @param personalLearnUnit
     * @return
     * 复习测试时间为效果检测后的第1、2、4、7、15天，共五次复习，两者同时需要复习优先进行复习测试
     */
    public static   boolean unitShouldReviewTest(PersonalLearnUnit personalLearnUnit , DateMachine dateMachine){
        //1/2/4/7/15 ;
        int state =personalLearnUnit.getReviewTestState();
        Date finishedDate =personalLearnUnit.getFinishedDate();
        Date lastReviewDate =personalLearnUnit.getLastedReviewTestDate();
        if (state==0){
            //第一天
            if (finishedDate!=null){
                int days =    dateMachine.differentDaysByMillisecond(finishedDate,new Date());
                personalLearnUnit.setState(1);
                if (days>=1){
                    return true ;
                }
            }
        }
        if (state==1){
            if (lastReviewDate!=null){
                int days=dateMachine.differentDaysByMillisecond(lastReviewDate,new Date());
                personalLearnUnit.setState(2);
                if (days>=2){
                    return  true ;
                }
            }
        }
        if (state==2){
            if (lastReviewDate!=null){
                int days =    dateMachine.differentDaysByMillisecond(lastReviewDate,new Date());
                personalLearnUnit.setState(4);
                if (days>=4){
                    return  true ;
                }
            }
        }
        if (state==4){
            if (lastReviewDate!=null){
                int days =    dateMachine.differentDaysByMillisecond(lastReviewDate,new Date());
                personalLearnUnit.setState(7);
                if (days>=7){
                    return  true ;
                }
            }
        }
        if (state==7){
            if (lastReviewDate!=null){
                int days =    dateMachine.differentDaysByMillisecond(lastReviewDate,new Date());
                personalLearnUnit.setState(15);
                if (days>=15){
                    return  true ;
                }
            }
        }
        //if state == 15 复习已经完成了；
        return  false ;
    }
}
