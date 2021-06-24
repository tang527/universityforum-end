package com.zp.universityForum.service.impl;

import com.zp.universityForum.bean.common.ListEntity;
import com.zp.universityForum.bean.common.Result;
import com.zp.universityForum.bean.common.SystemConfig;
import com.zp.universityForum.bean.vo.CommentRespVO;
import com.zp.universityForum.common.constant.Constant;
import com.zp.universityForum.mapper.SystemConfigMapper;

import com.zp.universityForum.service.CommonService;
import com.zp.universityForum.utils.QiniuCloudUtil;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zp
 * @date 2021-04-02 18:07
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Override
    public Result initReturnResult(int code, boolean success, String msg, Object data) throws Exception {
        Result result = new Result();
        result.setCode(code);
        result.setSuccess(success);
        result.setMessage(msg);
        result.setData(data);
        return result;
    }

    @Override
    public Result initReturnResult(int code, boolean success, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setSuccess(success);
        result.setMessage(msg);
        return result;
    }

    /**
     * 获取热门版块数量的配置
     */
    public Integer getHotTypeNum() {
        return getConfigByKey(Constant.CONFIG_HOT_TYPE_NUM_KEY).getConfigValue();
    }

    /**
     * 获取热门帖子数量的配置
     */
    public Integer getHotArtNum() {
        return getConfigByKey(Constant.CONFIG_HOT_ART_NUM_KEY).getConfigValue();
    }

    /**
     * 获取最新帖子数量的配置
     */
    public Integer getNewArtNum() {
        return getConfigByKey(Constant.CONFIG_NEW_ART_NUM_KEY).getConfigValue();
    }

    private SystemConfig getConfigByKey(String key) {
        SystemConfig config = systemConfigMapper.findConfigByKey(key);
        return config;
    }


}
