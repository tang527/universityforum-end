package com.zp.universityForum.service;

import com.zp.universityForum.bean.common.ListEntity;
import com.zp.universityForum.bean.common.Result;
import com.zp.universityForum.bean.vo.CommentRespVO;

/**
 * @author zp
 * @date 2021-04-02 18:04
 */
public interface CommonService {

    /**
     *构建返回结果集 只适用于包含一个key的情况
     */
    Result initReturnResult(int code, boolean success, String msg, Object data) throws Exception;
    Result initReturnResult(int code, boolean success, String msg);

    Integer getHotTypeNum();
    Integer getHotArtNum();
    Integer getNewArtNum();

}
