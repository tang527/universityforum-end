package com.zp.universityForum.service;

import com.zp.universityForum.bean.Report;
import com.zp.universityForum.bean.ReportType;
import com.zp.universityForum.bean.vo.ReportReqVO;

import java.util.List;

/**
 * @author zp
 * @date 2021-05-06 0:53
 */
public interface ReportService {

    List<ReportType> getReportTypes(int kind);
    int createReport(ReportReqVO vo, Long userId);
    Report checkReportIsExist(ReportReqVO vo, Long userId);
}
