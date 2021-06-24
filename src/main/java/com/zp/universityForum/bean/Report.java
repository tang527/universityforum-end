package com.zp.universityForum.bean;

import com.zp.universityForum.bean.vo.ReportReqVO;
import lombok.Data;

import java.util.Date;

/**
 * @author zp
 * @date 2021-05-06 9:54
 */
@Data
public class Report {

    private Long reportId;
    private Long reportUserId;
    private Long reportedUserId;

    private String reportReason;
    private Byte reportKind;
    private Long keyOne;
    private Long keyTwo;
    private Long keyThree;

    private Integer reportTypeId;
    private Date reportTime;
    // 0 未处理 1已处理
    private Byte handle;

    public Report() {
    }
    public Report(ReportReqVO vo) {
        this.reportUserId = vo.getReportUserId();
        this.reportedUserId = vo.getReportedUserId();
        this.reportKind = vo.getReportKind();
        this.reportReason = vo.getReportReason();
        this.keyOne = vo.getKeyOne();
        this.keyTwo = vo.getKeyTwo();
        this.keyThree = vo.getKeyThree();
        this.reportTypeId = vo.getReportTypeId();
    }
}
