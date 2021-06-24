package com.zp.universityForum.bean;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    private Long adminId;

    private String adminLoginName;
    private String adminLoginPassword;
}
