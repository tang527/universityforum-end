package com.zp.universityForum.mapper;

import com.zp.universityForum.bean.common.SystemConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zp
 * @date 2021-04-14 14:37
 */
@Mapper
public interface SystemConfigMapper {
    SystemConfig findConfigByKey(String configKey);
}
