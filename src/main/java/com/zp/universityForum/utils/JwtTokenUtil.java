package com.zp.universityForum.utils;

import com.zp.universityForum.common.constant.TokenConstant;
import com.zp.universityForum.config.TokenConfig;
import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

public class JwtTokenUtil {

    private static String secretKey;
    private static Duration accessTokenExpireTime;
    private static Duration refreshTokenExpireTime;
    private static String issuer;
    private static Integer power;

    public static void setTokenConfig(TokenConfig tokenConfig) {
        secretKey = tokenConfig.getSecretKey();
        accessTokenExpireTime = tokenConfig.getAccessTokenExpireTime();
        refreshTokenExpireTime = tokenConfig.getRefreshTokenExpireTime();
        issuer = tokenConfig.getIssuer();
        power = tokenConfig.getPower();
    }

    /**
     * 签发/生成token
     * @param issuer 签发人
     * @param subject JWT的主体 一般是用户ID
     * @param claims 存储在jwt里的信息(键值对) 一般是存放用户的权限/角色信息
     * @param ttlMills 有效时间(毫秒)
     * @param secret 密钥
     */
    public static String generateToken(String issuer, String subject, Map<String, Object> claims, long ttlMills, String secret) {
        // 加密方式
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 当前时间戳 并转换为日期
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        // 将Base64编码后的String还原成字节数组
        byte[] signingKey = DatatypeConverter.parseBase64Binary(secret);
        // new JwtBuilder 设置jwt的body
        JwtBuilder builder = Jwts.builder();
        // 若claims不为空 则加入JWT的载荷中
        if (null != claims) {
            builder.setClaims(claims);
        }
        if (!StringUtils.isEmpty(subject)) {
            builder.setSubject(subject);
        }
        if (!StringUtils.isEmpty(issuer)) {
            builder.setIssuer(issuer);
        }
        // 签发时间
        builder.setIssuedAt(now);
        if (ttlMills >= 0) {
            long expMillis = nowMillis + ttlMills;
            // 过期时间
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        builder.signWith(signatureAlgorithm, signingKey);
        return builder.compact();
    }

    /**
     * 生成 access_token 正常请求时携带的凭证
     */
    public static String getAccessToken(String subject, Map<String, Object> claims) {
        return generateToken(issuer, subject, claims, accessTokenExpireTime.toMillis(), secretKey);
    }

    /**
     * 生成 refresh_token
     */
    public static String getRefreshToken(String subject, Map<String, Object> claims) {
        // 此处可以看出 刷新token 和 业务token 只是过期时间不同
        return generateToken(issuer, subject, claims, refreshTokenExpireTime.toMillis(), secretKey);
    }

    /**
     * 解析令牌 获取数据声明
     * 拿到用户及用户的角色、权限信息
     */
    public static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            // 用密钥(字节数组)解析jwt 获取body(有效载荷)
            claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey)).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            // 如果解析出现异常 则token无效
            claims = null;
        }
        return claims;
    }


    /**
     * 获取用户id
     * @return
     */
    public static String getUserId(String token) {
        String userId = null;
        try {
            Claims claims = getClaimsFromToken(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            // 出错日志
            // TODO
        }
        return userId;
    }

    /**
     * 获取用户名
     * @return
     */
    public static String getUsername(String token) {
        String userName = null;

        try {
            Claims claims = getClaimsFromToken(token);
            // key为CONSTANT中的常量
            userName = (String) claims.get(TokenConstant.JWT_USER_NAME);
        } catch (Exception e) {
            // 出错日志
            // TODO
        }
        return userName;
    }

    /**
     * 验证token是否过期
     */
    public static Boolean isTokenExpired(String token) {
        // 首先进行解析
        Claims claims = getClaimsFromToken(token);
        if (null == claims) {
            return true;
        }
        Date expiration = claims.getExpiration();
        // 过期时间与当前时间进行比较 如果过期时间在当前时间之前 返回true 表示已经过期
        // 否则返回 false 还未过期
        return expiration.before(new Date());
    }

    /**
     * 校验令牌
     */
    public static Boolean validateToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            // 须同时满足 不为空(即解析正确) 和 未过期 这两个条件 证明令牌有效
            return (null != claims && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 刷新token
     * 如果是过期刷新 claims/载荷不变
     * 如果是主动刷新 claims/载荷改变 (一般是权限/角色改变的时候主动刷新)
     */
    public static String refreshToken(String refreshToken, Map<String, Object> claims) {
        String refreshedToken;
        try {
            Claims parserClaims = getClaimsFromToken(refreshToken);
            // 如果传入的claims为空，说明过期刷新，原用户信息不变，claims引用上个token中的内容
            if (null == claims) {
                claims = parserClaims;
            }
            // 不为空 根据传入的claims中的用户信息生成新的token
            refreshedToken = generateToken(parserClaims.getIssuer(), parserClaims.getSubject(), claims, accessTokenExpireTime.toMillis(), secretKey);
        } catch (Exception e) {
            refreshedToken = null;
            // 日志报错 TODO
        }
        return refreshedToken;
    }

    /**
     * 获取token的剩余过期时间
     */
    public static long getRemainingTime(String token) {
        long result = 0;
        try {
            long nowMillis = System.currentTimeMillis();
            result = getClaimsFromToken(token).getExpiration().getTime() - nowMillis;
        } catch (Exception e) {
            // 日志报错 TODO
        }
        return result;
    }

    /**
     * 计算是否refreshToken需要刷新
     * @return true 需要 false 不需要
     */
    public static boolean isRefreshTokenDying(String refreshToken) {
        long remainingTime = getRemainingTime(refreshToken);

        if(remainingTime < power * accessTokenExpireTime.toMillis()) {
            return true;
        }
        return false;
    }

    /**
     * 如果refreshToken需要刷新 则使用此方法进行刷新
     * @param refreshToken 将要过期的refreshToken
     */
    public static String getNewRefreshToken(String refreshToken) {
        String refreshedToken;
        try {
            // 获取快过期的refresh中的claim
            Claims parserClaims = getClaimsFromToken(refreshToken);
            refreshedToken = generateToken(parserClaims.getIssuer(), parserClaims.getSubject(), parserClaims, refreshTokenExpireTime.toMillis(), secretKey);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }
}
