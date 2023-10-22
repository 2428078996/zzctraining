package com.milk.common;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;


/**
 * @Description TODO
 * @Author @Milk
 * @Date 2022/11/6 11:26
 */
public class TokenUtils {


    private static  Long tokenExpiration= 24  * 60 * 60 *1000L ;

    private static final String SING_KEY="auth-user";

    public static String createToken(String username,String userId){

        String token= Jwts.builder()
                .claim("username",username)
                .claim("userId",userId)
                .setExpiration(new Date(System.currentTimeMillis()+tokenExpiration))
                .setSubject("AUTH-USER")
                .signWith(SignatureAlgorithm.HS256,SING_KEY)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    public static String getUserId(String token){

        try {
            if (StringUtils.isEmpty(token)){
                return null;
            }

            Jws<Claims> claims = Jwts.parser().setSigningKey(SING_KEY).parseClaimsJws(token);

            return (String)claims.getBody().get("userId");

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String getUsername(String token){

        try {
            if (StringUtils.isEmpty(token)){
                return null;
            }

            Jws<Claims> claims = Jwts.parser().setSigningKey(SING_KEY).parseClaimsJws(token);

            return (String) claims.getBody().get("username");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
