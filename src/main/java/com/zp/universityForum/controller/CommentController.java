package com.zp.universityForum.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zp.universityForum.bean.Comment;
import com.zp.universityForum.bean.common.ListEntity;
import com.zp.universityForum.bean.common.Result;
import com.zp.universityForum.bean.vo.ComMultiReqVO;
import com.zp.universityForum.bean.vo.ComMultiRespVO;
import com.zp.universityForum.bean.vo.CommentReqVO;
import com.zp.universityForum.bean.vo.CommentRespVO;
import com.zp.universityForum.common.annotation.CurrentUser;
import com.zp.universityForum.service.CommentService;
import com.zp.universityForum.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

//@CrossOrigin(origins = "http://1.15.243.160", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping(value = "comment")
public class CommentController {

    private Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    CommentService commentService;

    /**
     * 对某一article发表评论
     */
    @PostMapping("create_article_comment")
    public Result createArticleComment(@RequestBody CommentReqVO vo, @CurrentUser Long userId) {
        int i = commentService.createArticleComment(vo, userId);
        if(i != 1) {
            return ResultUtil.setErrorMsg("评论失败！");
        }
        return ResultUtil.setData();
    }

    @PostMapping("create_multi_comment")
    public Result createMultiComment(@RequestBody ComMultiReqVO vo, @CurrentUser Long userId) {
        ComMultiRespVO multi = commentService.createComMulti(vo, userId);
        if(null == multi) {
            return ResultUtil.setErrorMsg("评论失败！");
        }
        return ResultUtil.setData(multi);
    }

    @GetMapping("get_article_comments")
    public Result getArticleComments(
            @RequestParam(name = "artId") Long artId,
            @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        logger.info("pageNum: " + pageNum + " pageSize: " + pageSize);
        ListEntity<CommentRespVO> listEntity = commentService.getArticleComments(artId, pageNum, pageSize);
        if(null != listEntity) {

            return ResultUtil.setData(listEntity);
        }
        if (listEntity.getList().size() == 0) {
            // 此处为评论为空的情况
            return ResultUtil.setData();
        }
        return ResultUtil.setErrorMsg("评论信息获取失败!");
    }

}
