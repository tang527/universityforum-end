package com.zp.universityForum.mapper;

import com.zp.universityForum.bean.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {
    Admin findAdminByAdminLoginNameAndAdminLoginPassword(
            @Param("username") String username,
            @Param("password") String password);
}
