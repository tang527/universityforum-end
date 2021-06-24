package com.zp.universityForum.bean;

import lombok.Data;

/**
 * @author zp
 * @date 2021-05-06 0:56
 */
@Data
public class ReportType {
    private int typeId;
    private String typeName;
    private String typeDesc;
    // 0 帖子和评论 1 举报用户
    private Byte typeKind;
}
