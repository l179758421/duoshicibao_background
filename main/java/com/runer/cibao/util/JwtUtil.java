package com.runer.cibao.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @Author sww
 * @Date 2019/9/26
 * JWT工具类
 **/

@Component
public class JwtUtil {

    //@Value("${jwt.config-key}")
      private  String key="cibao";

   // @Value("${jwt.config-ttl}")
      private  long ttl=360000;


      public String getKey() {
            return key;
    }
      public void setKey(String key) {
            this.key = key;
    }
      public long getTtl() {
            return ttl;
    }
      public void setTtl(long ttl) {
            this.ttl = ttl;
    }


      /**
        * 签发 token
        */
      public  String createJWT(String id, String loginName){
            long now=System.currentTimeMillis();
            long exp=now+ttl;
            JwtBuilder jwtBuilder = Jwts.builder().setId(id)
                      .setSubject(loginName).setIssuedAt(new Date())
                      .signWith(SignatureAlgorithm.HS256, key);
            if(ttl>0){
                  jwtBuilder.setExpiration( new Date(exp));
          }
            String token = jwtBuilder.compact();
            return token;
    }
    /*
     * 解析JWT
     * @param token
     * @return
             */
      public  Claims parseJWT(String token) throws Exception{
          Claims  claims = Jwts.parser()
                   .setSigningKey(key)
                   .parseClaimsJws(token).getBody();

        return claims;
   }


    public static void main(String[] args) throws Exception {
        JwtUtil jwt= new JwtUtil();
        String admin = jwt.createJWT("1228", "admin");
        System.out.println(admin);
        Claims claims = jwt.parseJWT(admin);
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
    }
}