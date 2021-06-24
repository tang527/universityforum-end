package com.zp.universityForum.controller;

import com.zp.universityForum.common.annotation.CurrentUser;
import com.zp.universityForum.common.constant.Constant;
import com.zp.universityForum.common.constant.ResultConstant;
import com.zp.universityForum.bean.User;
import com.zp.universityForum.bean.common.Result;
import com.zp.universityForum.bean.vo.LoginReqVO;
import com.zp.universityForum.bean.vo.LoginRespVO;
import com.zp.universityForum.bean.vo.RegisterReqVO;
import com.zp.universityForum.common.constant.TokenConstant;
import com.zp.universityForum.common.exception.BusinessException;
import com.zp.universityForum.service.UserService;
import com.zp.universityForum.utils.JwtTokenUtil;
import com.zp.universityForum.utils.RedisUtil;
import com.zp.universityForum.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

//@CrossOrigin(origins = "http://1.15.243.160", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping(value = "user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserService userService;

    @PostMapping(value = "login")
    public Result userLogin(@RequestBody LoginReqVO vo){
        LoginRespVO loginRespVO = null;
        try {
            loginRespVO = userService.userLogin(vo);
        } catch (BusinessException e) {
            return ResultUtil.setErrorMsg(e.getMsg());
        }
        if (null != loginRespVO.getAccessToken()){
            return ResultUtil.setData(loginRespVO, ResultConstant.LOGIN_SUCCESS_MSG);
        } else {
            return ResultUtil.setErrorMsg(ResultConstant.ACCOUNT_PSW_ERROR_CODE, ResultConstant.ACCOUNT_PSW_ERROR_MSG);
        }
    }

    /**
     * 注意此处是修改用户信息，不包括email、phone与密码等重要信息 只需简单验证即可
     */
    @PostMapping(value = "update_info")
    public Result userUpdateInfo(@RequestBody User user, @CurrentUser Long userId) {
        // 通过token获取的userId
        user.setUserId(userId);
        // TODO 通过验证用户信息 成功后再进行修改
        if(userService.userUpdate(user) == 0) {
            return ResultUtil.setErrorMsg(ResultConstant.USER_INFO_UPDATE_FAILED_CODE, ResultConstant.USER_INFO_UPDATE_FAILED_MSG);
        }
        user.setUserImg(Constant.HTTP_PROTOCOL + user.getUserImg());
        return ResultUtil.setData(user);
    }

    @PostMapping("register")
    public Result userRegister(@RequestBody RegisterReqVO vo) {
        LoginRespVO resp = userService.userRegister(vo);
        if(null == resp.getAccessToken())
            return ResultUtil.setErrorMsg(ResultConstant.ACCOUNT_CREATED_FAILED_MSG);
        return ResultUtil.setData(resp, ResultConstant.ACCOUNT_CREATED_SUCCESS_MSG);
    }

    /**
     * 将该JWT-TOKEN放入redis中 标记为不可用
     * @param userId
     * @return
     */
    @PostMapping("logout")
    public Result logout(@CurrentUser Long userId, HttpServletRequest request) {
        String token = request.getHeader(TokenConstant.TOKEN_REQUEST_HEADER);
        if(redisUtil.hasKey(Constant.REDIS_KEY_DISABLED_TOKEN + "-" + token)) {
            return ResultUtil.setErrorMsg(ResultConstant.ACCOUNT_NOT_LOGIN_CODE, ResultConstant.ACCOUNT_NOT_LOGIN_MSG );
        }
        logger.info("获取userId为" + userId + "的用户的JWT token: " + token);
        long remainingTime = JwtTokenUtil.getRemainingTime(token);
        logger.info("token的剩余有效时间：" + remainingTime);
        logger.info("将token存入redis并放入黑名单");
        redisUtil.set(Constant.REDIS_KEY_DISABLED_TOKEN + "-" + token, token, remainingTime);
        return ResultUtil.setData();
    }
}
