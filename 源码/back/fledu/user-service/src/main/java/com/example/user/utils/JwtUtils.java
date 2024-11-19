package com.example.user.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.*;

public class JwtUtils {

    private static final long DEFAULT_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000;
    private static final String SIGN = "kda9y7hbg&&*nwe";
    private static final String ISSUER = "admin";

    /**
     * 创建Token
     */
    public static String createToken(String id, String type){
        HashMap<String, Object> map = new HashMap<>();

        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();

        String token = builder
                .withHeader(map)
                .withIssuer(ISSUER) // 设置发布者
                .withClaim("sub",id)
                .withClaim("type", type)
                .withExpiresAt(new Date(System.currentTimeMillis() + DEFAULT_EXPIRATION_TIME))  //指定令牌过期时间
                .sign(Algorithm.HMAC256(SIGN));  // sign
        return token;
    }

    /**
     * 获取id值 以及 type 值
     */
    public static List<String> getIdAndType(String token) {
        try {
            DecodedJWT claims = JWT.require(Algorithm.HMAC256(SIGN))
                    .withIssuer(ISSUER) // 设置发布者
                    .build()
                    .verify(token);
            String id = claims.getSubject();
            String type = claims.getClaim("type").asString();
            List<String> ids = new ArrayList<>();
            ids.add(id);
            ids.add(type);
            return ids;
        }catch (Exception e){ // 如果 token 过期会报错 TokenExpiredException
            e.printStackTrace();
            return Collections.singletonList("error_token");
        }
    }

    /**
     * 验证token  合法性
     */
    public static boolean judge(String token) {
        try {
            DecodedJWT claims = JWT.require(Algorithm.HMAC256(SIGN))
                    .withIssuer(ISSUER) // 设置发布者
                    .build()
                    .verify(token);
            return true;
        }catch (Exception e){ // 如果 token 过期会报错 TokenExpiredException
            e.printStackTrace();
            return false;
        }
    }
}
