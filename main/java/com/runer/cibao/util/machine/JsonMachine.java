package com.runer.cibao.util.machine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/25
 * 生成json数据
 **/
@Component
public class JsonMachine  {


    public void wirteToFile(String json ,String src){

        File file = new File(src);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            file.delete();
            file.createNewFile();
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
           BufferedWriter writer=new BufferedWriter(write);
           writer.write(json);
           writer.close();
          } catch (Exception e) {
           System.out.println("写文件内容操作出错");
           e.printStackTrace();
          }
      
    }



   /**
   使用JackJson将object转换成json；
   @Param object 
   **/
    public String  getJsonFromObject(Object object){
        try {
            String result =new ObjectMapper().writeValueAsString(object);
            return  result ;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return  null ;
        }


    }














}
