package com.zp.universityForum.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleType {

    private Long typeId;

    // 标签类型
    private String typeName;
    // 创建时间
    private Date typeCreated;
    // 描述
    private String typeDesc;
    // 板块级别
    private Integer typeLevel;
    // 版块封面图片
    private String typeImg;
    // 版主用户id
    private Long userId;

    // 帖子数量
    private Integer articleNum;
}
