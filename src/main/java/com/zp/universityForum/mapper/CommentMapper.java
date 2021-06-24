package com.zp.universityForum.mapper;

import com.github.pagehelper.Page;
import com.zp.universityForum.bean.Comment;
import com.zp.universityForum.bean.CommentMulti;
import com.zp.universityForum.bean.vo.ComMultiRespVO;
import com.zp.universityForum.bean.vo.CommentRespVO;
import com.zp.universityForum.resultmap.ViewComAndUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.awt.print.Pageable;
import java.util.List;

@Mapper
public interface CommentMapper{

    int insertComment(Comment comment);
    Long insertComMulti(CommentMulti commentMulti);
    CommentRespVO getComment(@Param("comId") Long comId);
    ComMultiRespVO getComMulti(@Param("artId") Long artId, @Param("comId") Long comId, @Param("mulId") Long mulId);
    List<CommentRespVO> listComments(@Param("artId") Long artId, @Param("start") int start, @Param("end") int end);
    int getCommentsTotal(@Param("artId") Long artId);
}
