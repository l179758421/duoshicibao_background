package com.runer.cibao.util;


import com.runer.cibao.util.machine.DateMachine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/10
 **/

@Component
public class TestForExcel {



    @Value("classpath:testResourses/111.xlsx")
    Resource testForExcel;


    public static  void main(String [] args){

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2012-12-22");
            Date[] dates = new DateMachine().getOneDayTimes(date);;
            for (Date date1 : dates) {
                System.err.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }



    public void noModelMultipleSheet() {

        System.err.println("开始开始");
        InputStream inputStream = null;



        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("testResourses/111.xlsx");

            if (inputStream!=null){
                System.err.println("inputStreamIsNotNull!!!");
            }




        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
