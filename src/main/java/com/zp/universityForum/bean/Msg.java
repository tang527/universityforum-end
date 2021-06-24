package com.zp.universityForum.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zp
 * @date 2021-05-14 0:36
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Msg {

    private Long msgId;

    // 1 : 帖子消息 2 : 回复消息 3 : 系统通知   目前系统通知一般是被举报
    private Byte msgType;
    private String msgContent;
    private Long relatedId;

    // 当且仅当需要用到 多重回复时才使用keyTwo和keyThree 此为数据库遗留问题...
    private Long keyOne;
    private Long keyTwo;
    private Long keyThree;

    private Long msgSendUserId;
    private Long msgReceiveUserId;

    private Date msgCreated;
    // 0 : 未读 1 : 已读
    private Byte msgHasRead;

}
