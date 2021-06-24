package com.zp.universityForum.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * JWT的配置读取类
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class TokenConfig {
    private String secretKey;
    private Duration accessTokenExpireTime;
    private Duration refreshTokenExpireTime;
    private String issuer;
    private Integer power;
}
