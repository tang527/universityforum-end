package com.zp.universityForum.bean;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article {

    private Long artId;

    private Long artUserId;     // 发表帖子的用户
    private String artTitle;    // 标题
    private Long artTypeId;     // 类型id
    private String artContent;  // 正文

    private Integer artViewNum;    // 浏览量
    private Integer artComNum;  // 评论数
    private Integer artLikeNum; // 点赞数
    private Date artCreated;    // 创建时间
    private Date artUpdated;    // 更新时间


}
