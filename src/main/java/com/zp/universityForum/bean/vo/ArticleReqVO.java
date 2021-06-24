package com.zp.universityForum.bean.vo;

import lombok.Data;

/**
 * @author zp
 * @date 2021-04-21 8:54
 */
@Data
public class ArticleReqVO {

    private String artTitle;
    private String artContent;
    private long artTypeId;
}
