package com.zp.universityForum.controller;

import com.zp.universityForum.bean.Report;
import com.zp.universityForum.bean.ReportType;
import com.zp.universityForum.bean.common.Result;
import com.zp.universityForum.bean.vo.ReportReqVO;
import com.zp.universityForum.common.annotation.CurrentUser;
import com.zp.universityForum.common.constant.ResultConstant;
import com.zp.universityForum.service.ReportService;
import com.zp.universityForum.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zp
 * @date 2021-05-06 0:52
 */
//@CrossOrigin(origins = "http://1.15.243.160", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping(value = "report")
public class ReportController {

    private Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    ReportService reportService;

    @GetMapping("get_report_types")
    public Result getReportTypes(@RequestParam int kind) {
        List<ReportType> reportTypes = reportService.getReportTypes(kind);
        if(null == reportTypes || reportTypes.isEmpty()) {
            return ResultUtil.setErrorMsg("获取信息出错");
        }
        return ResultUtil.setData(reportTypes);
    }

    @PostMapping("create_report")
    public Result createReport(@RequestBody ReportReqVO vo, @CurrentUser Long userId) {
        Report report = reportService.checkReportIsExist(vo, userId);
        if(null != report) {
            Byte handle = report.getHandle();
            if(handle == 0) {
                return ResultUtil.setErrorMsg(ResultConstant.WAIT_REPORT_HANDLE_CODE, ResultConstant.WAIT_REPORT_HANDLE_MSG);
            } else if(handle == 1) {
                return ResultUtil.setErrorMsg(ResultConstant.REPORT_HANDLE_DONE_CODE, ResultConstant.REPORT_HANDLE_DONE_MSG);
            } else {
                return ResultUtil.setErrorMsg("举报失败！");
            }
        }
        int i = reportService.createReport(vo, userId);
        if(i != 1) {
            return ResultUtil.setErrorMsg("举报失败！");
        }
        return ResultUtil.setData();
    }
}
