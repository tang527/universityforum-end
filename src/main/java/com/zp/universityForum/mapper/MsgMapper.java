package com.zp.universityForum.mapper;

import com.zp.universityForum.bean.Msg;
import com.zp.universityForum.bean.vo.MsgRespVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zp
 * @date 2021-05-14 1:03
 */
@Mapper
public interface MsgMapper {
    int insertMsg(Msg msg);
    List<MsgRespVO> listSystemMsg(Long userId);
    List<MsgRespVO> listReceiveMsg(Long userId);
    List<MsgRespVO> listArticleMsg(Long userId);
}
