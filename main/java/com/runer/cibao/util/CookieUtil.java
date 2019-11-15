package com.runer.cibao.util;



import com.runer.cibao.base.ApiResult;
import com.runer.cibao.exception.ResultMsg;
import org.springframework.util.Base64Utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/4
 *
 *
 * 暂时用于储存用户的信息；
 **/
public class CookieUtil {

    //保存cookie时的cookieName
    private final static String cookieDomainName = "cibao_runer";
    //加密cookie时的网站自定码
    private final static String webKey = "cibao_runer";
    //设置cookie有效期是两个星期，根据需要自定义
    private final static long cookieMaxAge = 60 * 60 * 24 * 7 * 2;

    //保存Cookie到客户端-------------------------------------------------------------------------
    //在CheckLogonServlet.java中被调用
    //传递进来的user对象中封装了在登陆时填写的用户名与密码

    public static void saveCookie(String username, String password, HttpServletResponse response)  {
        //cookie的有效期
        long validTime = System.currentTimeMillis() + (cookieMaxAge * 5000);
        //MD5加密用户详细信息
        byte[] cookieValueWithMd5 = Base64Utils.encode((password +","+ webKey).getBytes());
        //将要被保存的完整的Cookie值
        String cookieValue = username + ":" + validTime + ":" + new String(cookieValueWithMd5);
        //再一次对Cookie的值进行BASE64编码
        String cookieValueBase64 = new String(Base64Utils.encode(cookieValue.getBytes()));
        //开始保存Cookie
        Cookie cookie = new Cookie(cookieDomainName, cookieValueBase64);
        //存两年(这个值应该大于或等于validTime)
        cookie.setMaxAge(60 * 60 * 24 * 365 * 2);
        //cookie有效路径是网站根目录
        cookie.setPath("/");
        //向客户端写入

        response.addCookie(cookie);
    }

    //读取Cookie,自动完成登陆操作----------------------------------------------------------------
    //在Filter程序中调用该方法,见AutoLogonFilter.java
    public  static  ApiResult readCooikeForLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //根据cookieName取cookieValue
        Cookie cookies[] = request.getCookies();
        String cookieValue = null;
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
               // System.err.println(cookies[i].getName());
                if (cookieDomainName.equals(cookies[i].getName())) {
                    cookieValue = cookies[i].getValue();
                    break;
                }
            }
        }
        //如果cookieValue为空,返回,
        if (cookieValue == null) {
            return new ApiResult(ResultMsg.COOKIE_IS_NULL,null) ;
        }
        //如果cookieValue不为空,才执行下面的代码
        //先得到的CookieValue进行Base64解码
        String cookieValueAfterDecode = new String(Base64Utils.decode(cookieValue.getBytes()), "utf-8");
        //对解码后的值进行分拆,得到一个数组,如果数组长度不为3,就是非法登陆
        String cookieValues[] = cookieValueAfterDecode.split(":");
        if (cookieValues.length != 3) {
           return  new ApiResult(ResultMsg.ILLEGEAL_LOGIN,null);
        }
        //判断是否在有效期内,过期就删除Cookie
        long validTimeInCookie = new Long(cookieValues[1]);
        if (validTimeInCookie < System.currentTimeMillis()) {
            //删除Cookie
            clearCookie(response);
            return new ApiResult(ResultMsg.COOKIE_IS_TIME_OUT,null);
        }
        return  new ApiResult(ResultMsg.SUCCESS,cookieValues);
    }


    /**
     * 反向的对cookie 进行解析；
     * @param cookieValues
     * @return
     */
    public static String [] parseCookies(String [] cookieValues){
        if (cookieValues!=null&&cookieValues.length==3){
            String passInfo =cookieValues[2];
            try {
                String passAndWebkey =new String(Base64Utils.decode(passInfo.getBytes()),"utf-8") ;
                cookieValues[2]=passAndWebkey.split(",")[0];
                return cookieValues ;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return  cookieValues ;
            }
        }else{
            return  cookieValues ;
        }
    }



    //用户注销时,清除Cookie,在需要时可随时调用-----------------------------------------------------
    public static void clearCookie( HttpServletResponse response){
        Cookie cookie = new Cookie(cookieDomainName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    //获取Cookie组合字符串的MD5码的字符串----------------------------------------------------------------
    public static String getMD5(String value) {
        String result = null;
        try{
            byte[] valueByte = value.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(valueByte);
            result = toHex(md.digest());
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return result;
    }
    //将传递进来的字节数组转换成十六进制的字符串形式并返回
    private static String toHex(byte[] buffer){
        StringBuffer sb = new StringBuffer(buffer.length * 2);
        for (int i = 0; i < buffer.length; i++){
            sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
            sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
        }
        return sb.toString();
    }


}