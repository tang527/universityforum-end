package com.zp.universityForum.bean.vo;

import lombok.Data;

/**
 * @author zp
 * @date 2021-04-02 13:43
 * accessToken : 过期时间短
 * refreshToken : 过期时间长
 * 1. 令牌的重复使用: 当access token过期而refresh token有效的时候，客户端使用后者重新获取新的access token
 *                   而老的access_token 用redis 标记起来并设置过期时间，过期时间为该令牌剩余的过期时间
 *                   老的access_token在它剩余的过期时间里也要保持可使用，每次使用refresh token刷新access token的时候，不光更新access token，refresh token 同样也更新
 * 2. 令牌的作废 :    在用户修改了密码，或登出时：清空 shiro/SpringSecurity等安全框架的一些缓存信息
 *                   然后把 access_token 加入黑名单、refresh_token 加入redis里维护的黑名单
 *                   并且设置过期时间过期时间为该令牌剩余的过期时间
 *                   即 发放的access_token/refresh_token 过期了，与之对应的黑名单也不用再维护了
 * 3. 令牌的验证 :    判断客户端是否携带accessToken
 *                   判断用户是否被锁定
 *                   判断用户是否被删除
 *                   判断用户是否是否主动退出（查黑名单）
 *                   判断access_token 是否通过校验(校验是否过期)
 * 4. JWT的缺点 :    无法作废已颁布的令牌 —— 因为JWT是无状态的 所有的认证信息都在 JWT 中，由于在服务端没有状态，即使你知道了某个 JWT 被盗取了，你也没有办法将其作废
 *                   不易应对数据过期
 *
 */
@Data
public class LoginRespVO {
    private String accessToken;
    private String refreshToken;
    private Long userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String userImg;
    private Byte userSex;
    private Byte userStatus;
    private String userShow;
}
