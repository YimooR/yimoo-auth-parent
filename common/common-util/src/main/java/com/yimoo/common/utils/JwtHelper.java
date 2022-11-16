package com.yimoo.common.utils;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;


import java.util.Date;

/**
 * @ClassName JwtHelper
 * @Description TODO
 * @date 2022/11/16 19:12
 * @Version 1.0
 * @Author hao yang
 */

/**
 * 生成JSON Web令牌的工具类
 */
public class JwtHelper {

    //token过期时间
    private static long tokenExpiration = 365 * 24 * 60 * 60 * 1000;
    //加密密钥
    private static String tokenSignKey = "123456";

    //根据用户Id和用户名称生成token字符串
    public static String createToken(String userId, String username) {
        String token = Jwts.builder()
                //分组
                .setSubject("AUTH-USER")
                //过期时间
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                //放入重要信息
                .claim("userId", userId)
                .claim("username", username)
                //根据密钥加密
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                //将字符串压缩为一行
                .compressWith(CompressionCodecs.GZIP)
                //缩紧
                .compact();
        return token;
    }

   //从生成的token中获取userId
    public static String getUserId(String token) {
        try {
            if (StringUtils.isEmpty(token))
                return null;
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            String userId = (String) claims.get("userId");
            return userId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getUsername(String token) {
        try {
            if (StringUtils.isEmpty(token)) return "";

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return (String) claims.get("username");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void removeToken(String token) {
        //jwttoken无需删除，客户端扔掉即可。
    }


    public static void main(String[] args) {
        String token = JwtHelper.createToken("1", "admin");//"eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJSCjAK0A0Ndg1S0lFKrShQsjI0MzY2sDQ3MTbQUSotTi3yTFGyMjKEsP0Sc1OBWp6unfB0f7NSLQDxzD8_QwAAAA.2eCJdsJXOYaWFmPTJc8gl1YHTRl9DAeEJprKZn4IgJP9Fzo5fLddOQn1Iv2C25qMpwHQkPIGukTQtskWsNrnhQ";//JwtHelper.createToken(7L, "admin");
        System.out.println(token);
        System.out.println(JwtHelper.getUserId(token));
        System.out.println(JwtHelper.getUsername(token));
    }
}

