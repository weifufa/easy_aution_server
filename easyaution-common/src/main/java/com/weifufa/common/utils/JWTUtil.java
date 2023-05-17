package com.weifufa.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.weifufa.common.constant.MemberConstant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {
    /**
     * 密钥要⾃⼰保管好
     */
    private static String SECRET = "privatekey#^&^%!save";
    ///private static int expire = 3600*3;//3天过期

    /**
     * 传⼊payload信息获取token
     *
     * @param map payload
     * @return token
     */
    public static String getToken(Map<String, String> map) {
        JWTCreator.Builder builder = JWT.create();
        //payload
        map.forEach(builder::withClaim);
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE, MemberConstant.TOKEN_EXPIRE); //默认3天过期
        builder.withExpiresAt(instance.getTime());//指定令牌的过期时间
        return builder.sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 验证token
     */
    public static DecodedJWT verify(String token) {
        //如果有任何验证异常，此处都会抛出异常
        return JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }

    /**
     * 获取token中的payload
     */
    public static Map<String, Claim> getPayloadFromToken(String token) {
        return
                JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token).getClaims();
    }

    /**
     * 获取token中的payload
     */
    public static String getUserPhone(String token) {
        JWTVerifier jwtVerifier =
                JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token); // 验证并获取解码后的
        String phone = decodedJWT.getClaim("phone").asString();
        //System.out.println("⽤⼾手机号：" + phone);
        return phone;
    }
    public static String createJwt(String phone) {
        //生成JWT令牌
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        String token = getToken(map);
        return token;
    }
}
