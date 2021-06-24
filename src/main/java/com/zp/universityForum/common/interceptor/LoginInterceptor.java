package com.zp.universityForum.common.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zp.universityForum.common.constant.Constant;
import com.zp.universityForum.common.constant.ResultConstant;
import com.zp.universityForum.common.constant.TokenConstant;
import com.zp.universityForum.bean.common.Result;
import com.zp.universityForum.service.CommonService;
import com.zp.universityForum.utils.JwtTokenUtil;
import com.zp.universityForum.utils.RedisUtil;
import com.zp.universityForum.utils.ResultUtil;
import com.zp.universityForum.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 登陆拦截器
 * @author zp
 * @date 2021-04-02 17:17
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    @Autowired
    private RedisUtil redisUtil;

    @Resource
    private CommonService commonService;

    /**
     * 预处理回调方法，实现处理器的预处理（如登录检查）
     * 第三个参数为响应的处理器，即controller
     * 返回true，表示继续流程，调用下一个拦截器或者处理器
     * 返回false，表示流程中断，通过response产生响应
     *
     * 注意： 此处的拦截器用处为 拦截所有request请求（除注册及其它无需登录便可进行的操作），并将token解析为当前用户ID，并将此ID放入request中
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取Headers中的参数
        String token = request.getHeader(TokenConstant.TOKEN_REQUEST_HEADER);
        String refreshToken = request.getHeader(TokenConstant.TOKEN_REFRESH_HEADER);
        Result result;
        ObjectMapper om = new ObjectMapper();
        // 设置允许origin跨域
        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,token,content-type, refreshToken, accessToken");
        // token为空
        if(StringUtils.isEmpty(token)) {
            result = commonService.initReturnResult(ResultConstant.ACCOUNT_NOT_LOGIN_CODE,false,ResultConstant.ACCOUNT_NOT_LOGIN_MSG);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(om.writeValueAsString(result));
            return false;
        }
        // 验证用户是否已经退出登录 如果退出登录 则直接返回需要登录
        if(redisUtil.hasKey(Constant.REDIS_KEY_DISABLED_TOKEN + "-" + token)) {
            logger.info("redis黑名单中已存在此token 不可用");
            // 存在
            result = commonService.initReturnResult(ResultConstant.ACCOUNT_NEED_RE_LOGIN_CODE,false,ResultConstant.ACCOUNT_NEED_RE_LOGIN_MSG);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(om.writeValueAsString(result));
            return false;
        };
        // 校验token是否有效 过期或不合格都是无效
        if(!JwtTokenUtil.validateToken(token)) {

            // 刷新token 在此之前验证refreshToken是否有效
            if(JwtTokenUtil.validateToken(refreshToken)) {
                logger.info("refreshToken仍有效，使用其进行刷新");
                // 使用refreshToken刷新 并赋值
                token = JwtTokenUtil.refreshToken(refreshToken,null);
                logger.info("refreshToken的剩余有效时间：" + JwtTokenUtil.getRemainingTime(refreshToken));
                // 判断如果refreshToken的有效剩余时间较少 则连refreshToken也应该刷新
                // TODO
                if(JwtTokenUtil.isRefreshTokenDying(refreshToken)) {
                    logger.info("refreshToken的剩余有效时间：" + JwtTokenUtil.getRemainingTime(refreshToken));
                    logger.info("refreshToken需要刷新");
                    // 将剩余时间较短的refreshToken加入 redis 黑名单
                    refreshToken = JwtTokenUtil.getNewRefreshToken(refreshToken);
                    logger.info("refreshToekn已刷新！新的refreshToken的剩余时间：" + JwtTokenUtil.getRemainingTime(refreshToken));
                    if(StringUtil.isEmpty(refreshToken) || StringUtil.isEmpty(token)) {
                        result = commonService.initReturnResult(ResultConstant.ACCOUNT_NEED_RE_LOGIN_CODE,false,ResultConstant.ACCOUNT_NEED_RE_LOGIN_MSG);
                        response.setContentType("application/json;charset=utf-8");
                        response.getWriter().print(om.writeValueAsString(result));
                        return false;
                    }
                }

                // 传送刷新后的token
                response.setHeader(TokenConstant.TOKEN_REQUEST_HEADER, token);
                response.setHeader(TokenConstant.TOKEN_REFRESH_HEADER, refreshToken);
            } else {
                // 否则需要重新登陆
                result = commonService.initReturnResult(ResultConstant.ACCOUNT_NEED_RE_LOGIN_CODE,false,ResultConstant.ACCOUNT_NEED_RE_LOGIN_MSG);
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().print(om.writeValueAsString(result));
                return false;
            }
        }
        logger.info("token的剩余有效时间：" + JwtTokenUtil.getRemainingTime(token));
        logger.info("refreshToken的剩余有效时间：" + JwtTokenUtil.getRemainingTime(refreshToken));
        // token有效并进行Controller流程
        // 通过token获取userId 并存入 request
        Long userId = Long.parseLong(JwtTokenUtil.getUserId(token));
        String userName = JwtTokenUtil.getUsername(token);
        logger.info("token如下：" + token);
        request.setAttribute(Constant.LOGIN_USER_ID, userId);
        request.setAttribute(Constant.LOGIN_USER_NAME, userName);
        return true;
    }
}
