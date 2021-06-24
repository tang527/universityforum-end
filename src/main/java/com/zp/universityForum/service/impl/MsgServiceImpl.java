package com.zp.universityForum.service.impl;

import com.zp.universityForum.bean.Msg;
import com.zp.universityForum.bean.vo.MsgRespVO;
import com.zp.universityForum.mapper.MsgMapper;
import com.zp.universityForum.service.MsgService;
import com.zp.universityForum.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zp
 * @date 2021-05-14 0:32
 */
@Service
public class MsgServiceImpl implements MsgService {

    @Autowired
    private MsgMapper msgMapper;

    @Override
    public List<MsgRespVO> listArticleMsgByUserId(Long userId) {
        List<MsgRespVO> voList = msgMapper.listArticleMsg(userId);
        if(null != voList && !voList.isEmpty()) {
            for (MsgRespVO vo :
                    voList) {
                vo.setMsgContent(StringUtil.delHTMLTag(vo.getMsgContent()));
            }
        }
        return voList;
    }

    @Override
    public List<MsgRespVO> listReceiveMsgByUserId(Long userId) {
        List<MsgRespVO> voList = msgMapper.listReceiveMsg(userId);
        return voList;
    }

    @Override
    public List<MsgRespVO> listSystemMsgByUserId(Long userId) {
        List<MsgRespVO> voList = msgMapper.listSystemMsg(userId);
        return voList;
    }

    @Override
    public int createArticleMsg(Msg msg) {
        return msgMapper.insertMsg(msg);
    }

    @Override
    public int createReceiveMsg(Msg msg) {
        return msgMapper.insertMsg(msg);
    }

    @Override
    public int createSystemMsg(Msg msg) {
        return msgMapper.insertMsg(msg);
    }
}
