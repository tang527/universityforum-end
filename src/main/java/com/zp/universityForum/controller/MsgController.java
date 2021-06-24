package com.zp.universityForum.controller;

import com.zp.universityForum.bean.common.Result;
import com.zp.universityForum.bean.vo.ArticleReqVO;
import com.zp.universityForum.bean.vo.MsgRespVO;
import com.zp.universityForum.common.annotation.CurrentUser;
import com.zp.universityForum.service.MsgService;
import com.zp.universityForum.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zp
 * @date 2021-05-14 0:30
 */
//@CrossOrigin(origins = "http://1.15.243.160", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping(value = "msg")
public class MsgController {

    private Logger logger = LoggerFactory.getLogger(MsgController.class);
    @Autowired
    private MsgService msgService;

    @GetMapping("get_article")
    Result getArticle(@CurrentUser Long userId) {
        logger.error("获取与article相关的msg");
        List<MsgRespVO> msgList = msgService.listArticleMsgByUserId(userId);
        if (null == msgList) {
            return ResultUtil.setErrorMsg("消息获取失败!");
        }
        return ResultUtil.setData(msgList);
    }

    @GetMapping("get_comment")
    Result getComment(@CurrentUser Long userId) {
        logger.error("获取与comment相关的msg");
        List<MsgRespVO> msgList = msgService.listReceiveMsgByUserId(userId);
        if (null == msgList) {
            return ResultUtil.setErrorMsg("消息获取失败!");
        }
        return ResultUtil.setData(msgList);
    }

    @GetMapping("get_system")
    Result getSystem(@CurrentUser Long userId) {
        logger.error("获取与comment相关的msg");
        List<MsgRespVO> msgList = msgService.listSystemMsgByUserId(userId);
        if (null == msgList) {
            return ResultUtil.setErrorMsg("消息获取失败!");
        }
        return ResultUtil.setData(msgList);
    }

}
