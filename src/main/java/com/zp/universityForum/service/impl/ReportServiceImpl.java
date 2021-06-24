package com.zp.universityForum.service.impl;

import com.zp.universityForum.bean.Msg;
import com.zp.universityForum.bean.Report;
import com.zp.universityForum.bean.ReportType;
import com.zp.universityForum.bean.vo.ReportReqVO;
import com.zp.universityForum.mapper.ReportMapper;
import com.zp.universityForum.service.MsgService;
import com.zp.universityForum.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zp
 * @date 2021-05-06 0:53
 */
@Service
public class ReportServiceImpl implements ReportService {
    private Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
    @Autowired
    ReportMapper reportMapper;
    @Autowired
    MsgService msgService;

    @Override
    public List<ReportType> getReportTypes(int kind) {
        return reportMapper.listReportTypes(kind);
    }

    @Override
    public int createReport(ReportReqVO vo, Long userId) {
        vo.setReportUserId(userId);
        int i = reportMapper.insertReport(vo);
        try {
            Msg msg = new Msg();
            msg.setMsgType((byte) 3);
            msg.setMsgSendUserId(userId);
            msg.setMsgReceiveUserId(vo.getReportedUserId());
            // 举报类型为mul
            if(vo.getReportKind() == (byte) 1) {
                msg.setKeyTwo(vo.getKeyTwo());
                msg.setKeyThree(vo.getKeyThree());
            }
            msg.setMsgContent(vo.getReportReason());
            msg.setKeyOne(vo.getKeyOne());
            msg.setRelatedId(vo.getReportId());
            msgService.createSystemMsg(msg);
        } catch (Exception e) {
            return 0;
        }
        logger.info("reportVO层: " + vo);
        return i;
    }

    public Report checkReportIsExist(ReportReqVO vo, Long userId) {
        Byte kind = vo.getReportKind();
        Report report;
        if(kind == 1) {
            report = reportMapper.getMulReportByUserAndId(userId, vo.getReportedUserId(), vo.getKeyOne(), vo.getKeyTwo(), vo.getKeyThree());
        } else if(kind ==2) {
            report = reportMapper.getComReportByUserAndId(userId, vo.getReportedUserId(), vo.getKeyOne());
        } else {
            report = null;
        }
        return report;
    }
}
