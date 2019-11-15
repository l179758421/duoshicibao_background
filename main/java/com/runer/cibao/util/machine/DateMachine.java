package com.runer.cibao.util.machine;

import com.runer.cibao.Config;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/25
 **/
@Component
public class DateMachine {


    public Date getDaysBefore(int days ){
        Date date =new Date() ;
        Date beforeDate =DateUtils.addDays(date,0-days) ;
        return  beforeDate ;
    }



    public Date parseDateDefault(String date ){
     return    parseDate(date, Config.DATE_FORMAT_ALL) ;
    }


    public  Date parseDate(String date  ,String format){
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat(format);
        try {
           return  simpleDateFormat.parse(date) ;
        } catch (ParseException e) {
            e.printStackTrace();
            return  null ;
        }
    }



    public Date[] getOneDayTimes(Date date) {

        if (date==null){
            date =new Date() ;
        }

        Calendar c1 = new GregorianCalendar();

        c1.setTime(date);
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND,0);


        Calendar c2 = new GregorianCalendar();
        c2.setTime(date);

        c2.set(Calendar.HOUR_OF_DAY, 23);
        c2.set(Calendar.MINUTE, 59);
        c2.set(Calendar.SECOND, 59);
        c1.set(Calendar.MILLISECOND,999);
        return  new Date [] { c1.getTime(),c2.getTime() } ;

    }

    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public  int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            return day2-day1;
        }
    }


    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public  int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }



}
