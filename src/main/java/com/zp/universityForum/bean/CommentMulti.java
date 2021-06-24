package com.zp.universityForum.bean;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentMulti {

    private Long comArtId;
    private Long comId;
    private Long comMultiId;

    private Long multiUserId;
    private Long multiRepliedUserId;

    private String comMultiContent;
    private Byte comMultiType;
}
