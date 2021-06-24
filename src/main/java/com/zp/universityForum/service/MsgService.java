package com.zp.universityForum.service;

import com.zp.universityForum.bean.Msg;
import com.zp.universityForum.bean.vo.MsgRespVO;

import java.util.List;

/**
 * @author zp
 * @date 2021-05-14 0:32
 */
public interface MsgService {

    List<MsgRespVO> listArticleMsgByUserId(Long userId);
    List<MsgRespVO> listReceiveMsgByUserId(Long userId);
    List<MsgRespVO> listSystemMsgByUserId(Long userId);
    int createArticleMsg(Msg msg);
    int createReceiveMsg(Msg msg);
    int createSystemMsg(Msg msg);
}
