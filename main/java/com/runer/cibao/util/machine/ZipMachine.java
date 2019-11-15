package com.runer.cibao.util.machine;

import com.runer.cibao.domain.FilesZipBean;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.zeroturnaround.zip.FileSource;
import org.zeroturnaround.zip.ZipEntrySource;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/21
 此类工具类，一个压缩生产工具
 **/
@Component
public class ZipMachine {

    /**
     * 独自生产一个文件夹,并放入一个占位文件
     * <p>
     * 建议放入一个txt
     */
    public void produceAFolder(String folderPath, String seatFile) throws IOException {
        if (StringUtils.isEmpty(seatFile)) {
            produceAFolderWithDeafaultReadmeTxt(folderPath);
            return;
        }
        File file = new File(folderPath + "/" + seatFile);

        if (!file.exists()) {
            FileUtils.forceMkdir(file);
        }
    }


    /**
     * 创建一个folder 里面会有一个readme.txt
     *
     * @param folderPath
     * @throws IOException
     */
    public void produceAFolderWithDeafaultReadmeTxt(String folderPath) throws IOException {
        File file = new File(folderPath + "/readme.txt");
        if (!file.exists()) {
            FileUtils.forceMkdir(file);
        }
    }


    /**
     * 创建一个空的zip！！！
     *
     * @param zipPath
     * @param seatFile
     * @throws IOException
     */
    public void createEmptyZip(String zipPath, String seatFile) throws IOException {
        produceAFolder(zipPath, seatFile);
        ZipUtil.pack(new File(zipPath), new File(zipPath + ".zip"));
    }

    /**
     * 创建空的zip并将其删除；
     *
     * @param zipPath
     * @param seatFile
     * @throws IOException
     */
    public void createEmptyZipAndDelete(String zipPath, String seatFile) throws IOException {
        createEmptyZip(zipPath, seatFile);
        FileUtils.forceDelete(new File(zipPath));

    }


    /**
     * 用于存储可能key存在的情况
     * @param filePath
     * @param zipPath
     * @param seatFile
     * @throws Exception
     */
    public void insertIntoZip(List<FilesZipBean> filePath, String zipPath, String seatFile) throws Exception{

        if (filePath==null||filePath.isEmpty()){
            throw  new Exception("塞入的文件为空") ;
        }
        //创建zip
        createEmptyZipAndDelete(zipPath,seatFile);

        //遍历需要copy的文件
        List<ZipEntrySource> zipEntrySources =new ArrayList<>();
        filePath.forEach(filesZipBean -> {
            File file =new File(filesZipBean.getFilePath()) ;
            if (file.exists()){
                FileSource fileSource =new FileSource(filesZipBean.getFileOutPutPath(),file);
                zipEntrySources.add(fileSource) ;
            }
        });
        if (zipEntrySources==null||zipEntrySources.isEmpty()){
            throw  new Exception("文件路径不正确");
        }
        // list to array ;
        ZipEntrySource[] addedEntries =new ZipEntrySource[zipEntrySources.size()];
        zipEntrySources.toArray(addedEntries);

        // 塞进zip；
        ZipUtil.addOrReplaceEntries(new File(zipPath+".zip"),addedEntries);

    }




    /**
     * 凭空创建zip ，然后将文件copy到里面去
     * @param filePath
     * @param zipPath
     * @param seatFile
     */
     public void insertIntoZip(Map<String,String> filePath , String zipPath , String seatFile ) throws Exception {
         if (filePath==null||filePath.isEmpty()){
             throw  new Exception("塞入的文件为空") ;
         }
         //创建zip
         createEmptyZipAndDelete(zipPath,seatFile);

         //遍历需要copy的文件
        List<ZipEntrySource> zipEntrySources =new ArrayList<>();
        filePath.forEach((path, name) -> {
            File file =new File(path) ;
            if (file.exists()){
                FileSource fileSource =new FileSource(name,file);
                zipEntrySources.add(fileSource) ;
            }
        });

        if (zipEntrySources==null||zipEntrySources.isEmpty()){
            throw  new Exception("文件路径不正确");
        }
         // list to array ;
         ZipEntrySource[] addedEntries =new ZipEntrySource[zipEntrySources.size()];
         zipEntrySources.toArray(addedEntries);

        // 塞进zip；
         ZipUtil.addOrReplaceEntries(new File(zipPath+".zip"),addedEntries);

     }




























}
