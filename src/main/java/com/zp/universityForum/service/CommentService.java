package com.zp.universityForum.service;

import com.zp.universityForum.bean.common.ListEntity;
import com.zp.universityForum.bean.vo.ComMultiReqVO;
import com.zp.universityForum.bean.vo.ComMultiRespVO;
import com.zp.universityForum.bean.vo.CommentReqVO;
import com.zp.universityForum.bean.vo.CommentRespVO;


public interface CommentService {

    int createArticleComment(CommentReqVO vo, Long userId);
    ComMultiRespVO createComMulti(ComMultiReqVO vo, Long userId);
    ListEntity<CommentRespVO> getArticleComments(Long artId, int pageNum, int pageSize);
}
