package com.zp.universityForum.bean.vo;

import lombok.Data;

/**
 * @author zp
 * @date 2021-04-30 9:11
 */
@Data
public class CommentReqVO {
    // 被评论者用户id
    private Long comUserId;
    private String content;
    private Long artId;
}
