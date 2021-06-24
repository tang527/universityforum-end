package com.zp.universityForum.bean.vo;

import lombok.Data;

/**
 * @author zp
 * @date 2021-04-02 15:06
 */
@Data
public class RegisterReqVO {

    private String userName;
    private String userPsw;
    private String userShow;
    private String userEmail;
    private String userPhone;
    private Byte userSex;

}
