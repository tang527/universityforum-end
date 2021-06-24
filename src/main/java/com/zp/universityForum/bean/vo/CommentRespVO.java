package com.zp.universityForum.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zp
 * @date 2021-04-29 10:49
 * 用于Article页面的展示 主要为 Comment 包括用户信息
 */
@Data
public class CommentRespVO {
    private Long comId;
    private Long comUserId;

    private String comUserName;
    private String comUserAvatar;
    private String comContent;  // 评论内容

    private List<ComMultiRespVO> comMultiRespVOList;    // 回复内容
    private boolean fold;       // 便于前端折叠与显示 若回复为0 则不折叠 false 显示回复 否则折叠 true
    private boolean showInput;  // 便于前端控制显示回复框与否 默认为false 不显示
    private int multiSize;      // 用于前端显示与隐藏 回复的数量

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date comCreated;    // 创建时间
}
