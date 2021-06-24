package com.zp.universityForum.bean;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attention {

    private Long attId;

    private Long attAuthorId;//关注人id
    private Long attUserId;
}
