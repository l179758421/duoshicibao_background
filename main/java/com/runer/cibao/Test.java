package com.runer.cibao;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/29
 **/
public class Test {

   public static void  main(String [] args){
       Long a =new Long(1);
       Long b =1L ;
       System.err.println(a==b);

       Map<String,String> stringStringMap =new TreeMap<>() ;
       stringStringMap.put("1","1");
       stringStringMap.put("1","2");
       stringStringMap.forEach((s, s2) -> {
           System.err.println(s2);
       });
   }
}
