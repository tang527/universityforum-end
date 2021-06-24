package com.zp.universityForum.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

/**
 * @author zp
 * @date 2021-04-30 8:44
 */
@Data
public class ComMultiRespVO {
    // 帖子Id
    private Long artId;
    // 评论Id
    private Long comId;
    // 回复Id
    private Long multiId;
    // 回复者
    private Long multiUserId;
    private String multiUserName;
    private String multiUserAvatar;

    // 被回复者
    private Long multiRepliedUserId;
    private String multiRepliedUserName;

    private String multiContent;
    private Byte multiType;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date multiComTime;    // 创建时间
}
