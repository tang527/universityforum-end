package com.zp.universityForum.bean;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {


    private Long comId;

    private String comContent;//评论正文
    private Long comArtId;//文章id
    private Long comUserId;//评论用户得id
    @JsonFormat(timezone = "GMT+8",pattern = "YYYY-MM-DD hh:mm:ss")
    private Date comTime;//评论时间
}
