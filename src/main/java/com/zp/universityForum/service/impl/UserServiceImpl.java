package com.zp.universityForum.service.impl;

import com.github.pagehelper.Page;
import com.zp.universityForum.common.constant.Constant;
import com.zp.universityForum.common.constant.ResultConstant;
import com.zp.universityForum.common.constant.TokenConstant;
import com.zp.universityForum.common.exception.BusinessException;
import com.zp.universityForum.bean.User;
import com.zp.universityForum.mapper.UserMapper;
import com.zp.universityForum.bean.vo.LoginReqVO;
import com.zp.universityForum.bean.vo.LoginRespVO;
import com.zp.universityForum.bean.vo.RegisterReqVO;
import com.zp.universityForum.service.UserService;
import com.zp.universityForum.utils.JwtTokenUtil;
import com.zp.universityForum.utils.RedisUtil;
import com.zp.universityForum.utils.StringUtil;
import com.zp.universityForum.utils.password.PasswordUtil;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Resource
    RedisUtil redisUtil;

    @Override
    public LoginRespVO userLogin(LoginReqVO vo) {
        User user = userMapper.findUserByEmail(vo.getEmail());
        if (null == user) {
            throw new BusinessException(ResultConstant.ACCOUNT_NOT_EXIST_MSG);
        }
        // 明文 与 密文不匹配
        if (!PasswordUtil.matches(vo.getPsw(), user.getUserPsw())) {
            throw new BusinessException(ResultConstant.ACCOUNT_PSW_ERROR_MSG);
        }
        // 通过用户获取resp返回
        LoginRespVO respVO = getLoginRespVO(user);
        return respVO;
    }

    @Override
    // TODO
    public User userLoginByPhone(String phone, String code) {
        return null;
    }

    /**
     * 用于登陆成功或注册后的结果返回
     */
    private LoginRespVO getLoginRespVO(User user) {
        LoginRespVO respVO = new LoginRespVO();
        if(null == user) {
            // 如果新用户创建失败，则设置token为空
            respVO.setAccessToken(null);
            return respVO;
        }
        long userId = user.getUserId();
        String userName = user.getUserName();
        String userEmail = user.getUserEmail();
        String userPhone = user.getUserPhone();
        String userImg = user.getUserImg();
        String userShow = user.getUserShow();
        Byte userSex = user.getUserSex();
        Byte userStatus = user.getUserStatus();

        // 验证成功 生成token
        Map<String, Object> claims = new HashMap<>();
        // claims存放用户ID以及用户权限
        claims.put(TokenConstant.JWT_USER_ID, userId);
        claims.put(TokenConstant.JWT_USER_NAME, userName);
        String accessToken = JwtTokenUtil.getAccessToken(Long.toString(userId), claims);
        String refreshToken;
        refreshToken = JwtTokenUtil.getRefreshToken(Long.toString(userId), claims);

        respVO.setAccessToken(accessToken);
        respVO.setRefreshToken(refreshToken);

        // 用于将唯一数据传给前端进行显示
        respVO.setUserId(userId);
        respVO.setUserName(userName);
        respVO.setUserEmail(userEmail);
        respVO.setUserPhone(userPhone);
        // 添加http://
//        respVO.setUserImg(Constant.HTTP_PROTOCOL + userImg);
        respVO.setUserImg(userImg);
        respVO.setUserSex(userSex);
        respVO.setUserShow(userShow);
        respVO.setUserStatus(userStatus);
        return respVO;
    }

    /**
     * 新用户注册
     * @param vo 用户注册表单
     * @return 数据库建立的数据对象
     */
    @Override
    public LoginRespVO userRegister(RegisterReqVO vo) {
        User user=new User();
        user.setUserEmail(vo.getUserEmail());
        user.setUserName(vo.getUserName());

        user.setUserPhone(vo.getUserPhone());
        user.setUserSex(vo.getUserSex());
        user.setUserShow(vo.getUserShow());
//        user.setUserBlog("myself");
        Date date = new Date();//获得系统时间
        user.setUserCreated(date);
        //用户密码加密处理
        String cipherText = PasswordUtil.encode(vo.getUserPsw());
        user.setUserPsw(cipherText);

        try {
            userMapper.registerNewUser(user);
            //获取新注册的userId
            long id = user.getUserId();
            // 通过id更新用户img数据并返回用户信息
            StringBuilder sb = new StringBuilder();
//            user.setUserImg(Constant.HTTP_PROTOCOL + Constant.IMG_CDN_PREFIX + Constant.IMG_USER_PREFIX + id + Constant.IMG_USER_SUFFIX);
            user.setUserImg(StringUtil.join(Constant.HTTP_PROTOCOL, Constant.IMG_CDN_PREFIX, Constant.IMG_USER_PREFIX, Long.toString(id), Constant.IMG_USER_SUFFIX));
            userMapper.updateUserInfo(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResultConstant.ACCOUNT_CREATED_FAILED_MSG);
        }
        // 对新注册的用户进行登录处理
        return getLoginRespVO(user);
    }

    /**
     * 修改用户基本信息并返回 用于更新前端信息
     * @param user
     * @return
     */
    @Override
    public int userUpdate(User user) {
        String userImg = user.getUserImg();
//        if (!StringUtil.isEmpty(userImg)) {
            // 判断是否以 http:// 起始
//            if(userImg.startsWith(Constant.HTTP_PROTOCOL)) {
//                // 去除 http:// 如后续更改为 https://则为8
//                user.setUserImg(userImg.substring(7));
//            }
//        }
        return userMapper.updateUserInfo(user);
    }

    /**
     * 获取从拦截器中添加的userId
     * 此方法已弃用
     * @param request
     */
    public Object getLoginUserId(HttpServletRequest request) throws Exception {
        return request.getAttribute(Constant.LOGIN_USER_ID);
    }

    @Override
    public User findUserById(Long id) {
        return userMapper.findUserById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }

    @Override
    public User findUserByPhone(String phone) {
        return null;
    }

    @Override
    public Page<User> findhotuser(Integer page, Integer size) {
        return null;
    }


}
