package com.zp.universityForum.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zp
 * @date 2021-05-14 0:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MsgRespVO {

    private Long msgId;

    // 1 : 帖子消息 2 : 回复消息 3 : 系统通知   目前系统通知一般是被举报
//    private Byte msgType;
    private String msgContent;

    /**
     * type = 1 帖子名称
     * type = 2 回复的该楼层的内容 如有图片则显示【图片】 并进行省略显示 尝试去除html中的标签
     * type = 3 直接显示系统通知即可 当type = 3时显示举报信息 其中keyOne直接关联举报的id
     *      注：目前只有举报 comment和com_mul 一个是comId 一个是artId、comId、mulId
     *      其中keyOne 关联artId
     *      预留的keyTwo和keyThree 分别关联 comId 和 mulId
     */
    private String relatedTitle;
    private String relatedContent;
    private Long relatedId;
    private Long keyOne;
    private Long keyTwo;
    private Long keyThree;
    // kind reportKind 1: mul 2: comment
    private Byte msgKind;
    private String additionalMsg;


    private Long msgSendUserId;
    private String msgSendUserName;

    @JsonFormat(pattern="MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date msgCreated;
    private Byte msgHasRead;
}
