package com.zp.universityForum.bean;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long userId;
    private String userPsw;
    private String userName;        //用户昵称 唯一
    private String userEmail;       //用户邮箱 唯一
    private Byte userSex;           //用户性别1：男 0：女
    private String userPhone;       //用户手机号码 唯一
    private Byte userStatus;        //用户状态1：激活 0：未激活


    private Date userCreated;       //用户注册时间
    private Date userUpdated;       //用户更新时间
    private String userShow;        //用户个性签名
    private String userBlog;        //用户主页
    private String userImg;         //用户头像
    private Integer userFans;       //用户粉丝数
    private Integer userConcern;    //用户关注数

}
