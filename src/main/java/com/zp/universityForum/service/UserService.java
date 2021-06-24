package com.zp.universityForum.service;

import com.github.pagehelper.Page;
import com.zp.universityForum.bean.User;
import com.zp.universityForum.bean.vo.LoginReqVO;
import com.zp.universityForum.bean.vo.LoginRespVO;
import com.zp.universityForum.bean.vo.RegisterReqVO;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    LoginRespVO userLogin(LoginReqVO vo);
    User userLoginByPhone(String phone, String code);
    LoginRespVO userRegister(RegisterReqVO vo);
    int userUpdate(User user);
    Object getLoginUserId(HttpServletRequest request) throws Exception;
    User findUserById(Long userId);
    User findUserByEmail(String email);
    User findUserByPhone(String phone);
    Page<User> findhotuser(Integer page, Integer size);


}
