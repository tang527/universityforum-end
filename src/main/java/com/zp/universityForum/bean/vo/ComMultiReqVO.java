package com.zp.universityForum.bean.vo;

import lombok.Data;

/**
 * @author zp
 * @date 2021-05-04 9:40
 */
@Data
public class ComMultiReqVO {
    // 帖子Id
    private Long artId;
    // 评论Id
    private Long comId;

    // 回复者
    private Long multiUserId;
    // 被回复者
    private Long multiRepliedUserId;
    private String multiContent;
    // 0 表示回复该层层主 此种情况只显示回复者名称 一般为第一次在盖楼发表评论
    // 1 表示回复该层其他用户 此种情况需要显示回复者与被回复者
    private Byte multiType;
}
