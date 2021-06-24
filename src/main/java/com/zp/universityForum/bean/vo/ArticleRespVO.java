package com.zp.universityForum.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author zp
 * @date 2021-04-23 10:13
 */
@Data
public class ArticleRespVO {

    private Long artId;

    private Long artUserId;     // 发表帖子的用户id
    private String artUserName; // 发表帖子的用户名
    private String artTitle;    // 标题
    private String artTypeName; // 所在版块名
    private String artContent;  // 正文

    private Integer artViewNum; // 浏览量
    private Integer artComNum;  // 评论数
    private Integer artLikeNum; // 点赞数
    private boolean liked;    // 是否已被点赞 默认为false
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date artCreated;    // 创建时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date artUpdated;    // 更新时间
}
