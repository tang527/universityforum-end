package com.zp.universityForum.common.constant;

/**
 * @author zp
 * @date 2021-04-02 12:19
 */
public interface TokenConstant {

    /**
     * Header请求头中的token key
     */
    String TOKEN_REQUEST_HEADER = "accessToken";

    String TOKEN_REFRESH_HEADER = "refreshToken";

    /**
     * JWT主体内容中的用户名
     */
    String JWT_USER_NAME = "userName";

    /**
     * JWT主体内容中的用户ID
     */
    String JWT_USER_ID = "userId";

}
