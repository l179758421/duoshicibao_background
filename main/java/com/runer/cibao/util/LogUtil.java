package com.runer.cibao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/6
 **/
public class LogUtil {


    public static  void normalLog(Object o , Object thisClass){

        Logger logger = LoggerFactory.getLogger(thisClass.getClass().getName());
        if (o==null){
         logger.error(null);
         return;
        }
        logger.error(String.valueOf(o));
    }



}
