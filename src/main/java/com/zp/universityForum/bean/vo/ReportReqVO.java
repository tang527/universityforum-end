package com.zp.universityForum.bean.vo;

import lombok.Data;

/**
 * @author zp
 * @date 2021-05-06 9:46
 */
@Data
public class ReportReqVO {

    private Long reportId;
    private Long reportUserId;
    private Long reportedUserId;
    private String reportReason;

    private Byte reportKind;
    private Long keyOne;
    private Long keyTwo;
    private Long keyThree;

    private Integer reportTypeId;
}
