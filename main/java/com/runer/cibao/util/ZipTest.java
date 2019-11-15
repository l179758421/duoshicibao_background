package com.runer.cibao.util;

import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/21
 **/
public class ZipTest {



public static  void main(String [] args ) throws IOException{

//    for (int i = 0; i <100 ; i++) {
//        System.err.println(RandomUtils.nextInt(0,11));
//    }


    Date date =new Date() ;


    System.err.println(date.getDate());

    System.err.println(date.getDate());

    Calendar c1 = new GregorianCalendar();
    c1.setTime(DateUtils.addDays(new Date(),90));
    c1.set(Calendar.HOUR_OF_DAY, 0);
    c1.set(Calendar.MINUTE, 0);
    c1.set(Calendar.SECOND, 0);
    System.out.println(c1.getTime().toLocaleString());
    Calendar c2 = new GregorianCalendar();
    c2.setTime(DateUtils.addDays(new Date(),90));
    c2.set(Calendar.HOUR_OF_DAY, 23);
    c2.set(Calendar.MINUTE, 59);
    c2.set(Calendar.SECOND, 59);
    System.out.println(c2.getTime().toLocaleString());




//zip filePath
//        String fileForZipPath ="/Users/ruier/Desktop/Smart" ;
//        String zipPath ="/Users/ruier/Desktop/SmartForZip" ;
//
//        String zipTxt ="readme.txt";
//        File file =new File(zipPath+"/"+zipTxt) ;
//
//        if (!file.exists()){
//            FileUtils.forceMkdir(file);
//        }
//        if (!file.exists()){
//            System.err.println("创建失败！！！");
//            return;
//        }
//
//        ZipUtil.pack(new File(zipPath), new File(zipPath+".zip"));
//
//        FileUtils.deleteDirectory(new File(zipPath));
//
//        ZipEntrySource[] addedEntries = new ZipEntrySource[] {
//                new FileSource("1.png", new File("/Users/ruier/Desktop/Smart/seller/1.png")),
//                new FileSource("2.png", new File("/Users/ruier/Desktop/Smart/seller/2.png")),
//                new FileSource("3.png", new File("//Users/ruier/Desktop/Smart/seller/2.png")),
//        };
//
//        ZipUtil.addOrReplaceEntries(new File(zipPath+".zip"),addedEntries);
//
//
//
//        //ZipUtil.explode(new File(zipPath+".zip"));


//        ZipEntrySource[] addedEntries = new ZipEntrySource[] {
//                new FileSource("/path/in/zip/1.png", new File("/Users/ruier/Desktop/Smart/seller/1.png")),
//                new FileSource("/path/in/zip/2.png", new File("/Users/ruier/Desktop/Smart/seller/2.png")),
//                new FileSource("/path/in/zip/3.png", new File("//Users/ruier/Desktop/Smart/seller/2.png")),
//        };
//        ZipUtil.addOrReplaceEntries(new File(zipPath), addedEntries);


    }



}
