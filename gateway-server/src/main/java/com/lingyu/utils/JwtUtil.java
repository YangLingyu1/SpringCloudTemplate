package com.lingyu.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    // 密钥（长度必须 >=32 位）
    private static final String SECRET = "lingyu_springcloud_template_key_20050702";
    private static final long EXPIRE = 1000 * 60 * 60 * 24; // 1天

    private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());

    // 生成 token
    // claims: 存放用户信息
    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()//创建一个jwt构建器
                .claims(claims)//放数据
                .issuedAt(new Date())//签发时间
                .expiration(new Date(System.currentTimeMillis() + EXPIRE))//过期时间
                .signWith(secretKey) //自定义的密钥进行加密
                .compact(); //拼接为token(内部有build)
    }

    // 解析 token
    public Claims parseToken(String token) {
        return Jwts.parser()//创建一个jwt解析器
                .verifyWith(secretKey)//验证密钥
                .build()//创建解析器
                .parseSignedClaims(token)//解析token
                .getPayload();//获取数据
    }

    // 获取 userId
    public Long getUserId(String token) {
        return parseToken(token).get("userId", Long.class);
    }

    // 是否过期
    public boolean isExpired(String token) {
        return parseToken(token).getExpiration().before(new Date());
    }
}