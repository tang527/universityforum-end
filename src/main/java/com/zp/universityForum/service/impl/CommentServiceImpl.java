package com.zp.universityForum.service.impl;

import com.zp.universityForum.bean.Comment;
import com.zp.universityForum.bean.CommentMulti;
import com.zp.universityForum.bean.Msg;
import com.zp.universityForum.bean.common.ListEntity;
import com.zp.universityForum.bean.vo.ComMultiReqVO;
import com.zp.universityForum.bean.vo.ComMultiRespVO;
import com.zp.universityForum.bean.vo.CommentReqVO;
import com.zp.universityForum.bean.vo.CommentRespVO;
import com.zp.universityForum.mapper.CommentMapper;
import com.zp.universityForum.service.CommentService;
import com.zp.universityForum.service.MsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    MsgService msgService;
    @Autowired
    CommentMapper commentMapper;

    @Override
    public int createArticleComment(CommentReqVO vo, Long userId) {
        Comment comment = new Comment();
        comment.setComContent(vo.getContent());
        comment.setComArtId(vo.getArtId());
        comment.setComUserId(userId);

        logger.info("comment对象: " + comment);
        int i = commentMapper.insertComment(comment);
        if(i != 0) {
            Msg msg = new Msg();
            msg.setMsgType((byte) 1);
            msg.setMsgContent(vo.getContent());
            msg.setMsgSendUserId(userId);
            msg.setMsgReceiveUserId(vo.getComUserId());
            msg.setKeyOne(comment.getComId());
            msgService.createArticleMsg(msg);
            logger.info("对帖子评论的msg: " + msg);
        }
        return i;
    }

    @Override
    public ComMultiRespVO createComMulti(ComMultiReqVO vo, Long userId) {
        vo.setMultiUserId(userId);
        CommentMulti multi = new CommentMulti();
        multi.setComArtId(vo.getArtId());
        multi.setComId(vo.getComId());
        multi.setComMultiContent(vo.getMultiContent());
        multi.setComMultiType(vo.getMultiType());
        multi.setMultiUserId(vo.getMultiUserId());
        multi.setMultiRepliedUserId(vo.getMultiRepliedUserId());

        commentMapper.insertComMulti(multi);
        Long mulId = multi.getComMultiId();
        logger.info("新插入的mulId为: " + mulId);
        try {
            Msg msg = new Msg();
            msg.setMsgContent(vo.getMultiContent());
            msg.setMsgType((byte) 2);
            msg.setMsgSendUserId(vo.getMultiUserId());
            msg.setMsgReceiveUserId(vo.getMultiRepliedUserId());
            msg.setKeyOne(vo.getArtId());
            msg.setKeyTwo(vo.getComId());
            msg.setKeyThree(mulId);
            msgService.createReceiveMsg(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commentMapper.getComMulti(vo.getArtId(), vo.getComId(), mulId);
    }

    @Override
    public ListEntity<CommentRespVO> getArticleComments(Long artId, int pageNum, int pageSize) {
        // 此处直接对pageNum和pageSize 转换成start和end
        int total = commentMapper.getCommentsTotal(artId);
        List<CommentRespVO> comments = commentMapper.listComments(artId, (pageNum - 1) * pageSize, pageSize);
        if(null != comments && !comments.isEmpty()) {
            for(CommentRespVO comment:comments) {
                dealWithComment(comment);
            }
        }
        ListEntity<CommentRespVO> listEntity = new ListEntity<>(total, comments);
        listEntity.setPageNum(pageNum);
        listEntity.setPageSize(pageSize);
        // 譬如
        // 总数23 每页数量10  (23 + 10 - 1) / 10 = 3
        // 总数20             (20 + 10 - 1) / 10 = 2 诸如此类
        listEntity.setPages((total + pageSize - 1) / pageSize);
        return listEntity;
    }

    /**
     * 用于处理comment的一些参数
     */
    private void dealWithComment(CommentRespVO comment) {
        // 根据回复数量决定是否折叠
        List<ComMultiRespVO> comMultiList = comment.getComMultiRespVOList();
        if(null == comMultiList) return;
        if(comMultiList.isEmpty()) {
            comment.setFold(true);

        }
        comment.setMultiSize(comMultiList.size() > 5 ? 5 : comMultiList.size());
    }
}
