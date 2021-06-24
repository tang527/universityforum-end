package com.zp.universityForum.service.impl;

import com.zp.universityForum.bean.Admin;
import com.zp.universityForum.mapper.AdminMapper;
import com.zp.universityForum.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @Override
    public boolean adminLogin(String userName, String password) {
        Admin admin = adminMapper.findAdminByAdminLoginNameAndAdminLoginPassword(userName, password);
        return admin != null;
    }
}
