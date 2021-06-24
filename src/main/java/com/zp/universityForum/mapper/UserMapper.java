package com.zp.universityForum.mapper;

import com.zp.universityForum.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    long registerNewUser(User user);
    int updateUserInfo(User user);
    User findUserById(Long id);
    User findUserByEmail(String email);
    User findUserByPhone(String phone);
    User findUserByEmailAndPsw(String email, String psw);
}
