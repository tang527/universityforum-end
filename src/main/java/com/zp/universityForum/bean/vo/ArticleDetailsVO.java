package com.zp.universityForum.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author zp
 * @date 2021-04-30 9:43
 */
@Data
public class ArticleDetailsVO {
    private Long artId;

    private Long artUserId;     // 发表帖子的用户id
    private String artUserName; // 发表帖子的用户名
    private String artUserAvatar;   // 发表帖子的用户头像

    private String artTitle;    // 标题
    private String artContent;  // 正文

    private Integer artViewNum; // 浏览量
    private Integer artComNum;  // 评论数
    private Integer artLikeNum; // 点赞数
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date artCreated;    // 创建时间
}
