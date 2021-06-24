package com.zp.universityForum.controller;

import com.zp.universityForum.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping(value = "/loginbackstage")
    public boolean adminLogin(String username, String password){
        System.out.println(username+" "+password);
        return adminService.adminLogin(username,password);
    }
}
