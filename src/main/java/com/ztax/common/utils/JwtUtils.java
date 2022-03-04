package com.ztax.common.utils;

import com.ztax.common.entity.Payload;
import io.jsonwebtoken.*;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

/**
 * 生成token以及校验token工具方法
 */
public class JwtUtils {

    public static final String JWT_PAYLOAD_USER_KEY = "user";


    /**
     * 私钥加密token
     *
     * @param userInfo   载荷中的数据
     * @param privateKey 私钥
     * @param expire     过期时间，单位分钟
     * @return JWT
     */
    public static String generateTokenExpireInMinutes(Object userInfo, PrivateKey privateKey, int expire) {
        return Jwts.builder()
                .claim(JWT_PAYLOAD_USER_KEY, JsonUtils.toString(userInfo))
                .setId(createJTI())
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(expire).atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    /**
     * 私钥加密token
     *
     * @param userInfo   载荷中的数据
     * @param privateKey 私钥
     * @param expire     过期时间，单位秒
     * @return JWT
     */
    public static String generateTokenExpireInSeconds(Object userInfo, PrivateKey privateKey, int expire) {
        return Jwts.builder()
                .claim(JWT_PAYLOAD_USER_KEY, JsonUtils.toString(userInfo))
                .setId(createJTI())
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(expire).atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥
     * @return Jws<Claims>
     */
    public static Jws<Claims> parserToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }

    public static String createJTI() {
        return new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
    }

    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     */
    public static <T> Payload<T> getInfoFromToken(String token, PublicKey publicKey, Class<T> userType) {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        Payload<T> claims = new Payload<>();
        claims.setId(body.getId());
        claims.setUserInfo(JsonUtils.toBean(body.get(JWT_PAYLOAD_USER_KEY).toString(), userType));
        claims.setExpiration(body.getExpiration());
        return claims;
    }

    /**
     * 获取token中的载荷信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     */
    public static <T> Payload<T> getInfoFromToken(String token, PublicKey publicKey) {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        Payload<T> claims = new Payload<>();
        claims.setId(body.getId());
        claims.setExpiration(body.getExpiration());
        return claims;
    }


    /**
     * 从token中拾取用户名
     */
    public static String getUsernameFromToken(String token, PublicKey publicKey) {
        return getClaimFromToken(token, publicKey, Claims::getSubject);
    }

    /**
     * 拾取token中三个字段对应的信息
     */
    public static <T> T getClaimFromToken(String token, PublicKey publicKey, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token, publicKey);
        return claimsResolver.apply(claims);
    }

    /**
     * 判断token是否有效 [依据当前时间 与token中创建时间比较看是否超过了设置的过期时间]
     */
    public static Boolean isTokenExpired(String token, PublicKey publicKey) {
        final Date expiration = getExpirationDateFromToken(token, publicKey);
        return expiration.before(new Date());
    }

    /**
     * 从token中获取创建日期
     */
    public static Date getExpirationDateFromToken(String token, PublicKey publicKey) {
        return getClaimFromToken(token, publicKey, Claims::getExpiration);
    }

    /**
     * 拾取token中所有信息
     */
    public static Claims getAllClaimsFromToken(String token, PublicKey publicKey) {
        return Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(token)
                .getBody();
    }

}