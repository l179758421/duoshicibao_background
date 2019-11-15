package com.runer.cibao.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author szhua
 * @Description:smartcommunity==
 * @Date 2018/5/18
 **/
public class Arith {

    public static double add(double v1, double v2) {// 加法
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    public static double sub(double v1, double v2) {// 减法
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    public static double mul(double v1, double v2) {// 乘法
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    public static double div(double v1, double v2) {// 除法
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, 3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double round(double v) {// 截取3位
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, 3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String decimalFormat(String pattern, double value) {
        return new DecimalFormat(pattern).format(value);
    }

    public static String decimalFormat(double value) {
        return new DecimalFormat("0.00").format(value);
    }

    public static String decimalFormat(double value, String pattern) {
        return new DecimalFormat(pattern).format(value);
    }

    public static String decimalBlankFormat(double value) {
        return new DecimalFormat("0").format(value);
    }

    public static boolean isNumber(String value) { // 检查是否是数字
        String patternStr = "^\\d+$";
        Pattern p = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE); // 忽略大小写;
        Matcher m = p.matcher(value);
        return m.find();
    }

    public static String dayHousMinS(Long time){
        if(time <60 ){
            return (time.toString()+"秒");
        }else if(time <3600){
            Long min = time/60;
            Long mis = time%60;
            return  (min.toString()+"分");
        }else if(time < 86400){
            Long hous = time/3600;
            Long min = (time%3600)/60;
            Long mis = (time%3600)%60;
            return (hous.toString()+"时"+min.toString()+"分");
        }else{
            Long day = time/86400;
            Long hous = (time%86400)/3600;
            Long min = ((time%86400)%3600)/60;
            Long mis = ((time%86400)%3600)%60;
            return (day.toString()+"天"+hous.toString()+"时"+min.toString()+"分");
        }
    }


}
