package com.zp.universityForum.utils;

import com.zp.universityForum.config.TokenConfig;
import org.springframework.stereotype.Component;

/**
 * 用于初始化配置代理
 * @author zp
 * @date 2021-04-02 14:32
 */
@Component
public class InitializerUtil {
    private TokenConfig tokenConfig;

    public InitializerUtil(TokenConfig tokenConfig) {
        JwtTokenUtil.setTokenConfig(tokenConfig);
    }
}
